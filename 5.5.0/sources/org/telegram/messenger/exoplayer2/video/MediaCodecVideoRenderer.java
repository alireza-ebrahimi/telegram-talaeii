package org.telegram.messenger.exoplayer2.video;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.media.MediaCodec;
import android.media.MediaCodec.OnFrameRenderedListener;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import java.nio.ByteBuffer;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.decoder.DecoderCounters;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.drm.DrmInitData;
import org.telegram.messenger.exoplayer2.drm.DrmSessionManager;
import org.telegram.messenger.exoplayer2.drm.FrameworkMediaCrypto;
import org.telegram.messenger.exoplayer2.mediacodec.MediaCodecInfo;
import org.telegram.messenger.exoplayer2.mediacodec.MediaCodecRenderer;
import org.telegram.messenger.exoplayer2.mediacodec.MediaCodecSelector;
import org.telegram.messenger.exoplayer2.mediacodec.MediaCodecUtil;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.TraceUtil;
import org.telegram.messenger.exoplayer2.util.Util;
import org.telegram.messenger.exoplayer2.video.VideoRendererEventListener.EventDispatcher;

@TargetApi(16)
public class MediaCodecVideoRenderer extends MediaCodecRenderer {
    private static final String KEY_CROP_BOTTOM = "crop-bottom";
    private static final String KEY_CROP_LEFT = "crop-left";
    private static final String KEY_CROP_RIGHT = "crop-right";
    private static final String KEY_CROP_TOP = "crop-top";
    private static final int MAX_PENDING_OUTPUT_STREAM_OFFSET_COUNT = 10;
    private static final int[] STANDARD_LONG_EDGE_VIDEO_PX = new int[]{1920, 1600, 1440, 1280, 960, 854, 640, 540, 480};
    private static final String TAG = "MediaCodecVideoRenderer";
    private final long allowedJoiningTimeMs;
    private CodecMaxValues codecMaxValues;
    private boolean codecNeedsSetOutputSurfaceWorkaround;
    private int consecutiveDroppedFrameCount;
    private final Context context;
    private int currentHeight;
    private float currentPixelWidthHeightRatio;
    private int currentUnappliedRotationDegrees;
    private int currentWidth;
    private final boolean deviceNeedsAutoFrcWorkaround;
    private long droppedFrameAccumulationStartTimeMs;
    private int droppedFrames;
    private Surface dummySurface;
    private final EventDispatcher eventDispatcher;
    private final VideoFrameReleaseTimeHelper frameReleaseTimeHelper;
    private long joiningDeadlineMs;
    private final int maxDroppedFramesToNotify;
    private long outputStreamOffsetUs;
    private int pendingOutputStreamOffsetCount;
    private final long[] pendingOutputStreamOffsetsUs;
    private float pendingPixelWidthHeightRatio;
    private int pendingRotationDegrees;
    private boolean renderedFirstFrame;
    private int reportedHeight;
    private float reportedPixelWidthHeightRatio;
    private int reportedUnappliedRotationDegrees;
    private int reportedWidth;
    private int scalingMode;
    private Format[] streamFormats;
    private Surface surface;
    private boolean tunneling;
    private int tunnelingAudioSessionId;
    OnFrameRenderedListenerV23 tunnelingOnFrameRenderedListener;

    protected static final class CodecMaxValues {
        public final int height;
        public final int inputSize;
        public final int width;

        public CodecMaxValues(int i, int i2, int i3) {
            this.width = i;
            this.height = i2;
            this.inputSize = i3;
        }
    }

    @TargetApi(23)
    private final class OnFrameRenderedListenerV23 implements OnFrameRenderedListener {
        private OnFrameRenderedListenerV23(MediaCodec mediaCodec) {
            mediaCodec.setOnFrameRenderedListener(this, new Handler());
        }

        public void onFrameRendered(MediaCodec mediaCodec, long j, long j2) {
            if (this == MediaCodecVideoRenderer.this.tunnelingOnFrameRenderedListener) {
                MediaCodecVideoRenderer.this.maybeNotifyRenderedFirstFrame();
            }
        }
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, 0);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j) {
        this(context, mediaCodecSelector, j, null, null, -1);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j, Handler handler, VideoRendererEventListener videoRendererEventListener, int i) {
        this(context, mediaCodecSelector, j, null, false, handler, videoRendererEventListener, i);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z, Handler handler, VideoRendererEventListener videoRendererEventListener, int i) {
        super(2, mediaCodecSelector, drmSessionManager, z);
        this.allowedJoiningTimeMs = j;
        this.maxDroppedFramesToNotify = i;
        this.context = context.getApplicationContext();
        this.frameReleaseTimeHelper = new VideoFrameReleaseTimeHelper(context);
        this.eventDispatcher = new EventDispatcher(handler, videoRendererEventListener);
        this.deviceNeedsAutoFrcWorkaround = deviceNeedsAutoFrcWorkaround();
        this.pendingOutputStreamOffsetsUs = new long[10];
        this.outputStreamOffsetUs = C3446C.TIME_UNSET;
        this.joiningDeadlineMs = C3446C.TIME_UNSET;
        this.currentWidth = -1;
        this.currentHeight = -1;
        this.currentPixelWidthHeightRatio = -1.0f;
        this.pendingPixelWidthHeightRatio = -1.0f;
        this.scalingMode = 1;
        clearReportedVideoSize();
    }

    private static boolean areAdaptationCompatible(boolean z, Format format, Format format2) {
        return format.sampleMimeType.equals(format2.sampleMimeType) && getRotationDegrees(format) == getRotationDegrees(format2) && (z || (format.width == format2.width && format.height == format2.height));
    }

    private void clearRenderedFirstFrame() {
        this.renderedFirstFrame = false;
        if (Util.SDK_INT >= 23 && this.tunneling) {
            MediaCodec codec = getCodec();
            if (codec != null) {
                this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(codec);
            }
        }
    }

    private void clearReportedVideoSize() {
        this.reportedWidth = -1;
        this.reportedHeight = -1;
        this.reportedPixelWidthHeightRatio = -1.0f;
        this.reportedUnappliedRotationDegrees = -1;
    }

    private static boolean codecNeedsSetOutputSurfaceWorkaround(String str) {
        return (("deb".equals(Util.DEVICE) || "flo".equals(Util.DEVICE)) && "OMX.qcom.video.decoder.avc".equals(str)) || ("tcl_eu".equals(Util.DEVICE) && "OMX.MTK.VIDEO.DECODER.AVC".equals(str));
    }

    @TargetApi(21)
    private static void configureTunnelingV21(MediaFormat mediaFormat, int i) {
        mediaFormat.setFeatureEnabled("tunneled-playback", true);
        mediaFormat.setInteger("audio-session-id", i);
    }

    private static boolean deviceNeedsAutoFrcWorkaround() {
        return Util.SDK_INT <= 22 && "foster".equals(Util.DEVICE) && "NVIDIA".equals(Util.MANUFACTURER);
    }

    private static Point getCodecMaxSize(MediaCodecInfo mediaCodecInfo, Format format) {
        Object obj = format.height > format.width ? 1 : null;
        int i = obj != null ? format.height : format.width;
        int i2 = obj != null ? format.width : format.height;
        float f = ((float) i2) / ((float) i);
        for (int i3 : STANDARD_LONG_EDGE_VIDEO_PX) {
            int i32;
            int i4 = (int) (((float) i32) * f);
            if (i32 <= i || i4 <= i2) {
                return null;
            }
            if (Util.SDK_INT >= 21) {
                int i5 = obj != null ? i4 : i32;
                if (obj == null) {
                    i32 = i4;
                }
                Point alignVideoSizeV21 = mediaCodecInfo.alignVideoSizeV21(i5, i32);
                if (mediaCodecInfo.isVideoSizeAndRateSupportedV21(alignVideoSizeV21.x, alignVideoSizeV21.y, (double) format.frameRate)) {
                    return alignVideoSizeV21;
                }
            } else {
                i32 = Util.ceilDivide(i32, 16) * 16;
                i4 = Util.ceilDivide(i4, 16) * 16;
                if (i32 * i4 <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                    return new Point(obj != null ? i4 : i32, obj != null ? i32 : i4);
                }
            }
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getMaxInputSize(java.lang.String r6, int r7, int r8) {
        /*
        r4 = 16;
        r1 = 4;
        r0 = 2;
        r2 = -1;
        if (r7 == r2) goto L_0x0009;
    L_0x0007:
        if (r8 != r2) goto L_0x000b;
    L_0x0009:
        r0 = r2;
    L_0x000a:
        return r0;
    L_0x000b:
        r3 = r6.hashCode();
        switch(r3) {
            case -1664118616: goto L_0x0018;
            case -1662541442: goto L_0x0044;
            case 1187890754: goto L_0x0023;
            case 1331836730: goto L_0x002e;
            case 1599127256: goto L_0x0039;
            case 1599127257: goto L_0x004f;
            default: goto L_0x0012;
        };
    L_0x0012:
        r3 = r2;
    L_0x0013:
        switch(r3) {
            case 0: goto L_0x005a;
            case 1: goto L_0x005a;
            case 2: goto L_0x0063;
            case 3: goto L_0x007e;
            case 4: goto L_0x0081;
            case 5: goto L_0x0081;
            default: goto L_0x0016;
        };
    L_0x0016:
        r0 = r2;
        goto L_0x000a;
    L_0x0018:
        r3 = "video/3gpp";
        r3 = r6.equals(r3);
        if (r3 == 0) goto L_0x0012;
    L_0x0021:
        r3 = 0;
        goto L_0x0013;
    L_0x0023:
        r3 = "video/mp4v-es";
        r3 = r6.equals(r3);
        if (r3 == 0) goto L_0x0012;
    L_0x002c:
        r3 = 1;
        goto L_0x0013;
    L_0x002e:
        r3 = "video/avc";
        r3 = r6.equals(r3);
        if (r3 == 0) goto L_0x0012;
    L_0x0037:
        r3 = r0;
        goto L_0x0013;
    L_0x0039:
        r3 = "video/x-vnd.on2.vp8";
        r3 = r6.equals(r3);
        if (r3 == 0) goto L_0x0012;
    L_0x0042:
        r3 = 3;
        goto L_0x0013;
    L_0x0044:
        r3 = "video/hevc";
        r3 = r6.equals(r3);
        if (r3 == 0) goto L_0x0012;
    L_0x004d:
        r3 = r1;
        goto L_0x0013;
    L_0x004f:
        r3 = "video/x-vnd.on2.vp9";
        r3 = r6.equals(r3);
        if (r3 == 0) goto L_0x0012;
    L_0x0058:
        r3 = 5;
        goto L_0x0013;
    L_0x005a:
        r1 = r7 * r8;
    L_0x005c:
        r1 = r1 * 3;
        r0 = r0 * 2;
        r0 = r1 / r0;
        goto L_0x000a;
    L_0x0063:
        r1 = "BRAVIA 4K 2015";
        r3 = org.telegram.messenger.exoplayer2.util.Util.MODEL;
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x0070;
    L_0x006e:
        r0 = r2;
        goto L_0x000a;
    L_0x0070:
        r1 = org.telegram.messenger.exoplayer2.util.Util.ceilDivide(r7, r4);
        r2 = org.telegram.messenger.exoplayer2.util.Util.ceilDivide(r8, r4);
        r1 = r1 * r2;
        r1 = r1 * 16;
        r1 = r1 * 16;
        goto L_0x005c;
    L_0x007e:
        r1 = r7 * r8;
        goto L_0x005c;
    L_0x0081:
        r0 = r7 * r8;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x005c;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.video.MediaCodecVideoRenderer.getMaxInputSize(java.lang.String, int, int):int");
    }

    private static int getMaxInputSize(Format format) {
        if (format.maxInputSize == -1) {
            return getMaxInputSize(format.sampleMimeType, format.width, format.height);
        }
        int i = 0;
        for (int i2 = 0; i2 < format.initializationData.size(); i2++) {
            i += ((byte[]) format.initializationData.get(i2)).length;
        }
        return format.maxInputSize + i;
    }

    private static float getPixelWidthHeightRatio(Format format) {
        return format.pixelWidthHeightRatio == -1.0f ? 1.0f : format.pixelWidthHeightRatio;
    }

    private static int getRotationDegrees(Format format) {
        return format.rotationDegrees == -1 ? 0 : format.rotationDegrees;
    }

    private static boolean isBufferLate(long j) {
        return j < -30000;
    }

    private void maybeNotifyDroppedFrames() {
        if (this.droppedFrames > 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.eventDispatcher.droppedFrames(this.droppedFrames, elapsedRealtime - this.droppedFrameAccumulationStartTimeMs);
            this.droppedFrames = 0;
            this.droppedFrameAccumulationStartTimeMs = elapsedRealtime;
        }
    }

    private void maybeNotifyVideoSizeChanged() {
        if (this.currentWidth != -1 || this.currentHeight != -1) {
            if (this.reportedWidth != this.currentWidth || this.reportedHeight != this.currentHeight || this.reportedUnappliedRotationDegrees != this.currentUnappliedRotationDegrees || this.reportedPixelWidthHeightRatio != this.currentPixelWidthHeightRatio) {
                this.eventDispatcher.videoSizeChanged(this.currentWidth, this.currentHeight, this.currentUnappliedRotationDegrees, this.currentPixelWidthHeightRatio);
                this.reportedWidth = this.currentWidth;
                this.reportedHeight = this.currentHeight;
                this.reportedUnappliedRotationDegrees = this.currentUnappliedRotationDegrees;
                this.reportedPixelWidthHeightRatio = this.currentPixelWidthHeightRatio;
            }
        }
    }

    private void maybeRenotifyRenderedFirstFrame() {
        if (this.renderedFirstFrame) {
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void maybeRenotifyVideoSizeChanged() {
        if (this.reportedWidth != -1 || this.reportedHeight != -1) {
            this.eventDispatcher.videoSizeChanged(this.reportedWidth, this.reportedHeight, this.reportedUnappliedRotationDegrees, this.reportedPixelWidthHeightRatio);
        }
    }

    private void setJoiningDeadlineMs() {
        this.joiningDeadlineMs = this.allowedJoiningTimeMs > 0 ? SystemClock.elapsedRealtime() + this.allowedJoiningTimeMs : C3446C.TIME_UNSET;
    }

    @TargetApi(23)
    private static void setOutputSurfaceV23(MediaCodec mediaCodec, Surface surface) {
        mediaCodec.setOutputSurface(surface);
    }

    private void setSurface(Surface surface) {
        if (surface == null) {
            if (this.dummySurface != null) {
                surface = this.dummySurface;
            } else {
                MediaCodecInfo codecInfo = getCodecInfo();
                if (codecInfo != null && shouldUseDummySurface(codecInfo.secure)) {
                    this.dummySurface = DummySurface.newInstanceV17(this.context, codecInfo.secure);
                    surface = this.dummySurface;
                }
            }
        }
        if (this.surface != surface) {
            this.surface = surface;
            int state = getState();
            if (state == 1 || state == 2) {
                MediaCodec codec = getCodec();
                if (Util.SDK_INT < 23 || codec == null || surface == null || this.codecNeedsSetOutputSurfaceWorkaround) {
                    releaseCodec();
                    maybeInitCodec();
                } else {
                    setOutputSurfaceV23(codec, surface);
                }
            }
            if (surface == null || surface == this.dummySurface) {
                clearReportedVideoSize();
                clearRenderedFirstFrame();
                return;
            }
            maybeRenotifyVideoSizeChanged();
            clearRenderedFirstFrame();
            if (state == 2) {
                setJoiningDeadlineMs();
            }
        } else if (surface != null && surface != this.dummySurface) {
            maybeRenotifyVideoSizeChanged();
            maybeRenotifyRenderedFirstFrame();
        }
    }

    private static void setVideoScalingMode(MediaCodec mediaCodec, int i) {
        mediaCodec.setVideoScalingMode(i);
    }

    private boolean shouldUseDummySurface(boolean z) {
        return Util.SDK_INT >= 23 && !this.tunneling && (!z || DummySurface.isSecureSupported(this.context));
    }

    protected boolean canReconfigureCodec(MediaCodec mediaCodec, boolean z, Format format, Format format2) {
        return areAdaptationCompatible(z, format, format2) && format2.width <= this.codecMaxValues.width && format2.height <= this.codecMaxValues.height && getMaxInputSize(format2) <= this.codecMaxValues.inputSize;
    }

    protected void configureCodec(MediaCodecInfo mediaCodecInfo, MediaCodec mediaCodec, Format format, MediaCrypto mediaCrypto) {
        this.codecMaxValues = getCodecMaxValues(mediaCodecInfo, format, this.streamFormats);
        MediaFormat mediaFormat = getMediaFormat(format, this.codecMaxValues, this.deviceNeedsAutoFrcWorkaround, this.tunnelingAudioSessionId);
        if (this.surface == null) {
            Assertions.checkState(shouldUseDummySurface(mediaCodecInfo.secure));
            if (this.dummySurface == null) {
                this.dummySurface = DummySurface.newInstanceV17(this.context, mediaCodecInfo.secure);
            }
            this.surface = this.dummySurface;
        }
        mediaCodec.configure(mediaFormat, this.surface, mediaCrypto, 0);
        if (Util.SDK_INT >= 23 && this.tunneling) {
            this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(mediaCodec);
        }
    }

    protected void dropOutputBuffer(MediaCodec mediaCodec, int i, long j) {
        TraceUtil.beginSection("dropVideoBuffer");
        mediaCodec.releaseOutputBuffer(i, false);
        TraceUtil.endSection();
        DecoderCounters decoderCounters = this.decoderCounters;
        decoderCounters.droppedOutputBufferCount++;
        this.droppedFrames++;
        this.consecutiveDroppedFrameCount++;
        this.decoderCounters.maxConsecutiveDroppedOutputBufferCount = Math.max(this.consecutiveDroppedFrameCount, this.decoderCounters.maxConsecutiveDroppedOutputBufferCount);
        if (this.droppedFrames == this.maxDroppedFramesToNotify) {
            maybeNotifyDroppedFrames();
        }
    }

    protected CodecMaxValues getCodecMaxValues(MediaCodecInfo mediaCodecInfo, Format format, Format[] formatArr) {
        int i = format.width;
        int i2 = format.height;
        int maxInputSize = getMaxInputSize(format);
        if (formatArr.length == 1) {
            return new CodecMaxValues(i, i2, maxInputSize);
        }
        int length = formatArr.length;
        int i3 = 0;
        int i4 = 0;
        while (i3 < length) {
            int i5;
            Format format2 = formatArr[i3];
            if (areAdaptationCompatible(mediaCodecInfo.adaptive, format, format2)) {
                i5 = (format2.width == -1 || format2.height == -1) ? 1 : 0;
                i5 |= i4;
                i = Math.max(i, format2.width);
                i2 = Math.max(i2, format2.height);
                i4 = Math.max(maxInputSize, getMaxInputSize(format2));
                maxInputSize = i2;
                i2 = i;
            } else {
                i5 = i4;
                i4 = maxInputSize;
                maxInputSize = i2;
                i2 = i;
            }
            i3++;
            i = i2;
            i2 = maxInputSize;
            maxInputSize = i4;
            i4 = i5;
        }
        if (i4 != 0) {
            Log.w(TAG, "Resolutions unknown. Codec max resolution: " + i + "x" + i2);
            Point codecMaxSize = getCodecMaxSize(mediaCodecInfo, format);
            if (codecMaxSize != null) {
                i = Math.max(i, codecMaxSize.x);
                i2 = Math.max(i2, codecMaxSize.y);
                maxInputSize = Math.max(maxInputSize, getMaxInputSize(format.sampleMimeType, i, i2));
                Log.w(TAG, "Codec max resolution adjusted to: " + i + "x" + i2);
            }
        }
        return new CodecMaxValues(i, i2, maxInputSize);
    }

    @SuppressLint({"InlinedApi"})
    protected MediaFormat getMediaFormat(Format format, CodecMaxValues codecMaxValues, boolean z, int i) {
        MediaFormat frameworkMediaFormatV16 = format.getFrameworkMediaFormatV16();
        frameworkMediaFormatV16.setInteger("max-width", codecMaxValues.width);
        frameworkMediaFormatV16.setInteger("max-height", codecMaxValues.height);
        if (codecMaxValues.inputSize != -1) {
            frameworkMediaFormatV16.setInteger("max-input-size", codecMaxValues.inputSize);
        }
        if (z) {
            frameworkMediaFormatV16.setInteger("auto-frc", 0);
        }
        if (i != 0) {
            configureTunnelingV21(frameworkMediaFormatV16, i);
        }
        return frameworkMediaFormatV16;
    }

    public void handleMessage(int i, Object obj) {
        if (i == 1) {
            setSurface((Surface) obj);
        } else if (i == 4) {
            this.scalingMode = ((Integer) obj).intValue();
            MediaCodec codec = getCodec();
            if (codec != null) {
                setVideoScalingMode(codec, this.scalingMode);
            }
        } else {
            super.handleMessage(i, obj);
        }
    }

    public boolean isReady() {
        if (super.isReady() && (this.renderedFirstFrame || ((this.dummySurface != null && this.surface == this.dummySurface) || getCodec() == null || this.tunneling))) {
            this.joiningDeadlineMs = C3446C.TIME_UNSET;
            return true;
        } else if (this.joiningDeadlineMs == C3446C.TIME_UNSET) {
            return false;
        } else {
            if (SystemClock.elapsedRealtime() < this.joiningDeadlineMs) {
                return true;
            }
            this.joiningDeadlineMs = C3446C.TIME_UNSET;
            return false;
        }
    }

    void maybeNotifyRenderedFirstFrame() {
        if (!this.renderedFirstFrame) {
            this.renderedFirstFrame = true;
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    protected void onCodecInitialized(String str, long j, long j2) {
        this.eventDispatcher.decoderInitialized(str, j, j2);
        this.codecNeedsSetOutputSurfaceWorkaround = codecNeedsSetOutputSurfaceWorkaround(str);
    }

    protected void onDisabled() {
        this.currentWidth = -1;
        this.currentHeight = -1;
        this.currentPixelWidthHeightRatio = -1.0f;
        this.pendingPixelWidthHeightRatio = -1.0f;
        this.outputStreamOffsetUs = C3446C.TIME_UNSET;
        this.pendingOutputStreamOffsetCount = 0;
        clearReportedVideoSize();
        clearRenderedFirstFrame();
        this.frameReleaseTimeHelper.disable();
        this.tunnelingOnFrameRenderedListener = null;
        this.tunneling = false;
        try {
            super.onDisabled();
        } finally {
            this.decoderCounters.ensureUpdated();
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    protected void onEnabled(boolean z) {
        super.onEnabled(z);
        this.tunnelingAudioSessionId = getConfiguration().tunnelingAudioSessionId;
        this.tunneling = this.tunnelingAudioSessionId != 0;
        this.eventDispatcher.enabled(this.decoderCounters);
        this.frameReleaseTimeHelper.enable();
    }

    protected void onInputFormatChanged(Format format) {
        super.onInputFormatChanged(format);
        this.eventDispatcher.inputFormatChanged(format);
        this.pendingPixelWidthHeightRatio = getPixelWidthHeightRatio(format);
        this.pendingRotationDegrees = getRotationDegrees(format);
    }

    protected void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        Object obj = (mediaFormat.containsKey(KEY_CROP_RIGHT) && mediaFormat.containsKey(KEY_CROP_LEFT) && mediaFormat.containsKey(KEY_CROP_BOTTOM) && mediaFormat.containsKey(KEY_CROP_TOP)) ? 1 : null;
        this.currentWidth = obj != null ? (mediaFormat.getInteger(KEY_CROP_RIGHT) - mediaFormat.getInteger(KEY_CROP_LEFT)) + 1 : mediaFormat.getInteger("width");
        this.currentHeight = obj != null ? (mediaFormat.getInteger(KEY_CROP_BOTTOM) - mediaFormat.getInteger(KEY_CROP_TOP)) + 1 : mediaFormat.getInteger("height");
        this.currentPixelWidthHeightRatio = this.pendingPixelWidthHeightRatio;
        if (Util.SDK_INT < 21) {
            this.currentUnappliedRotationDegrees = this.pendingRotationDegrees;
        } else if (this.pendingRotationDegrees == 90 || this.pendingRotationDegrees == 270) {
            int i = this.currentWidth;
            this.currentWidth = this.currentHeight;
            this.currentHeight = i;
            this.currentPixelWidthHeightRatio = 1.0f / this.currentPixelWidthHeightRatio;
        }
        setVideoScalingMode(mediaCodec, this.scalingMode);
    }

    protected void onPositionReset(long j, boolean z) {
        super.onPositionReset(j, z);
        clearRenderedFirstFrame();
        this.consecutiveDroppedFrameCount = 0;
        if (this.pendingOutputStreamOffsetCount != 0) {
            this.outputStreamOffsetUs = this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1];
            this.pendingOutputStreamOffsetCount = 0;
        }
        if (z) {
            setJoiningDeadlineMs();
        } else {
            this.joiningDeadlineMs = C3446C.TIME_UNSET;
        }
    }

    protected void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) {
        if (Util.SDK_INT < 23 && this.tunneling) {
            maybeNotifyRenderedFirstFrame();
        }
    }

    protected void onStarted() {
        super.onStarted();
        this.droppedFrames = 0;
        this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
    }

    protected void onStopped() {
        this.joiningDeadlineMs = C3446C.TIME_UNSET;
        maybeNotifyDroppedFrames();
        super.onStopped();
    }

    protected void onStreamChanged(Format[] formatArr, long j) {
        this.streamFormats = formatArr;
        if (this.outputStreamOffsetUs == C3446C.TIME_UNSET) {
            this.outputStreamOffsetUs = j;
        } else {
            if (this.pendingOutputStreamOffsetCount == this.pendingOutputStreamOffsetsUs.length) {
                Log.w(TAG, "Too many stream changes, so dropping offset: " + this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1]);
            } else {
                this.pendingOutputStreamOffsetCount++;
            }
            this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1] = j;
        }
        super.onStreamChanged(formatArr, j);
    }

    protected boolean processOutputBuffer(long j, long j2, MediaCodec mediaCodec, ByteBuffer byteBuffer, int i, int i2, long j3, boolean z) {
        while (this.pendingOutputStreamOffsetCount != 0 && j3 >= this.pendingOutputStreamOffsetsUs[0]) {
            this.outputStreamOffsetUs = this.pendingOutputStreamOffsetsUs[0];
            this.pendingOutputStreamOffsetCount--;
            System.arraycopy(this.pendingOutputStreamOffsetsUs, 1, this.pendingOutputStreamOffsetsUs, 0, this.pendingOutputStreamOffsetCount);
        }
        long j4 = j3 - this.outputStreamOffsetUs;
        if (z) {
            skipOutputBuffer(mediaCodec, i, j4);
            return true;
        }
        long j5 = j3 - j;
        if (this.surface == this.dummySurface) {
            if (!isBufferLate(j5)) {
                return false;
            }
            skipOutputBuffer(mediaCodec, i, j4);
            return true;
        } else if (!this.renderedFirstFrame) {
            if (Util.SDK_INT >= 21) {
                renderOutputBufferV21(mediaCodec, i, j4, System.nanoTime());
            } else {
                renderOutputBuffer(mediaCodec, i, j4);
            }
            return true;
        } else if (getState() != 2) {
            return false;
        } else {
            j5 -= (SystemClock.elapsedRealtime() * 1000) - j2;
            long nanoTime = System.nanoTime();
            long adjustReleaseTime = this.frameReleaseTimeHelper.adjustReleaseTime(j3, (j5 * 1000) + nanoTime);
            j5 = (adjustReleaseTime - nanoTime) / 1000;
            if (shouldDropOutputBuffer(j5, j2)) {
                dropOutputBuffer(mediaCodec, i, j4);
                return true;
            }
            if (Util.SDK_INT >= 21) {
                if (j5 < 50000) {
                    renderOutputBufferV21(mediaCodec, i, j4, adjustReleaseTime);
                    return true;
                }
            } else if (j5 < 30000) {
                if (j5 > 11000) {
                    try {
                        Thread.sleep((j5 - 10000) / 1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                renderOutputBuffer(mediaCodec, i, j4);
                return true;
            }
            return false;
        }
    }

    protected void releaseCodec() {
        try {
            super.releaseCodec();
        } finally {
            if (this.dummySurface != null) {
                if (this.surface == this.dummySurface) {
                    this.surface = null;
                }
                this.dummySurface.release();
                this.dummySurface = null;
            }
        }
    }

    protected void renderOutputBuffer(MediaCodec mediaCodec, int i, long j) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodec.releaseOutputBuffer(i, true);
        TraceUtil.endSection();
        DecoderCounters decoderCounters = this.decoderCounters;
        decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    @TargetApi(21)
    protected void renderOutputBufferV21(MediaCodec mediaCodec, int i, long j, long j2) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodec.releaseOutputBuffer(i, j2);
        TraceUtil.endSection();
        DecoderCounters decoderCounters = this.decoderCounters;
        decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    protected boolean shouldDropOutputBuffer(long j, long j2) {
        return isBufferLate(j);
    }

    protected boolean shouldInitCodec(MediaCodecInfo mediaCodecInfo) {
        return this.surface != null || shouldUseDummySurface(mediaCodecInfo.secure);
    }

    protected void skipOutputBuffer(MediaCodec mediaCodec, int i, long j) {
        TraceUtil.beginSection("skipVideoBuffer");
        mediaCodec.releaseOutputBuffer(i, false);
        TraceUtil.endSection();
        DecoderCounters decoderCounters = this.decoderCounters;
        decoderCounters.skippedOutputBufferCount++;
    }

    protected int supportsFormat(MediaCodecSelector mediaCodecSelector, Format format) {
        int i = 0;
        String str = format.sampleMimeType;
        if (!MimeTypes.isVideo(str)) {
            return 0;
        }
        boolean z;
        DrmInitData drmInitData = format.drmInitData;
        if (drmInitData != null) {
            z = false;
            for (int i2 = 0; i2 < drmInitData.schemeDataCount; i2++) {
                z |= drmInitData.get(i2).requiresSecureDecryption;
            }
        } else {
            z = false;
        }
        MediaCodecInfo decoderInfo = mediaCodecSelector.getDecoderInfo(str, z);
        if (decoderInfo == null) {
            return 1;
        }
        boolean isCodecSupported = decoderInfo.isCodecSupported(format.codecs);
        if (isCodecSupported && format.width > 0 && format.height > 0) {
            if (Util.SDK_INT >= 21) {
                isCodecSupported = decoderInfo.isVideoSizeAndRateSupportedV21(format.width, format.height, (double) format.frameRate);
            } else {
                isCodecSupported = format.width * format.height <= MediaCodecUtil.maxH264DecodableFrameSize();
                if (!isCodecSupported) {
                    Log.d(TAG, "FalseCheck [legacyFrameSize, " + format.width + "x" + format.height + "] [" + Util.DEVICE_DEBUG_INFO + "]");
                }
            }
        }
        int i3 = decoderInfo.adaptive ? 16 : 8;
        if (decoderInfo.tunneling) {
            i = 32;
        }
        return (i | i3) | (isCodecSupported ? 4 : 3);
    }
}
