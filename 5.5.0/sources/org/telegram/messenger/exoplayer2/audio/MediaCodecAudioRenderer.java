package org.telegram.messenger.exoplayer2.audio;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import java.nio.ByteBuffer;
import org.telegram.messenger.exoplayer2.ExoPlaybackException;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.PlaybackParameters;
import org.telegram.messenger.exoplayer2.audio.AudioRendererEventListener.EventDispatcher;
import org.telegram.messenger.exoplayer2.audio.AudioTrack.InitializationException;
import org.telegram.messenger.exoplayer2.audio.AudioTrack.Listener;
import org.telegram.messenger.exoplayer2.audio.AudioTrack.WriteException;
import org.telegram.messenger.exoplayer2.drm.DrmSessionManager;
import org.telegram.messenger.exoplayer2.drm.FrameworkMediaCrypto;
import org.telegram.messenger.exoplayer2.mediacodec.MediaCodecInfo;
import org.telegram.messenger.exoplayer2.mediacodec.MediaCodecRenderer;
import org.telegram.messenger.exoplayer2.mediacodec.MediaCodecSelector;
import org.telegram.messenger.exoplayer2.util.MediaClock;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.Util;

@TargetApi(16)
public class MediaCodecAudioRenderer extends MediaCodecRenderer implements MediaClock {
    private boolean allowPositionDiscontinuity;
    private final AudioTrack audioTrack;
    private int channelCount;
    private boolean codecNeedsDiscardChannelsWorkaround;
    private long currentPositionUs;
    private final EventDispatcher eventDispatcher;
    private boolean passthroughEnabled;
    private MediaFormat passthroughMediaFormat;
    private int pcmEncoding;

    private final class AudioTrackListener implements Listener {
        private AudioTrackListener() {
        }

        public void onAudioSessionId(int i) {
            MediaCodecAudioRenderer.this.eventDispatcher.audioSessionId(i);
            MediaCodecAudioRenderer.this.onAudioSessionId(i);
        }

        public void onPositionDiscontinuity() {
            MediaCodecAudioRenderer.this.onAudioTrackPositionDiscontinuity();
            MediaCodecAudioRenderer.this.allowPositionDiscontinuity = true;
        }

        public void onUnderrun(int i, long j, long j2) {
            MediaCodecAudioRenderer.this.eventDispatcher.audioTrackUnderrun(i, j, j2);
            MediaCodecAudioRenderer.this.onAudioTrackUnderrun(i, j, j2);
        }
    }

    public MediaCodecAudioRenderer(MediaCodecSelector mediaCodecSelector) {
        this(mediaCodecSelector, null, true);
    }

    public MediaCodecAudioRenderer(MediaCodecSelector mediaCodecSelector, Handler handler, AudioRendererEventListener audioRendererEventListener) {
        this(mediaCodecSelector, null, true, handler, audioRendererEventListener);
    }

    public MediaCodecAudioRenderer(MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z) {
        this(mediaCodecSelector, drmSessionManager, z, null, null);
    }

    public MediaCodecAudioRenderer(MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z, Handler handler, AudioRendererEventListener audioRendererEventListener) {
        this(mediaCodecSelector, drmSessionManager, z, handler, audioRendererEventListener, null, new AudioProcessor[0]);
    }

    public MediaCodecAudioRenderer(MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z, Handler handler, AudioRendererEventListener audioRendererEventListener, AudioCapabilities audioCapabilities, AudioProcessor... audioProcessorArr) {
        super(1, mediaCodecSelector, drmSessionManager, z);
        this.audioTrack = new AudioTrack(audioCapabilities, audioProcessorArr, new AudioTrackListener());
        this.eventDispatcher = new EventDispatcher(handler, audioRendererEventListener);
    }

    private static boolean codecNeedsDiscardChannelsWorkaround(String str) {
        return Util.SDK_INT < 24 && "OMX.SEC.aac.dec".equals(str) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("zeroflte") || Util.DEVICE.startsWith("herolte") || Util.DEVICE.startsWith("heroqlte"));
    }

    protected boolean allowPassthrough(String str) {
        return this.audioTrack.isPassthroughSupported(str);
    }

    protected void configureCodec(MediaCodecInfo mediaCodecInfo, MediaCodec mediaCodec, Format format, MediaCrypto mediaCrypto) {
        this.codecNeedsDiscardChannelsWorkaround = codecNeedsDiscardChannelsWorkaround(mediaCodecInfo.name);
        if (this.passthroughEnabled) {
            this.passthroughMediaFormat = format.getFrameworkMediaFormatV16();
            this.passthroughMediaFormat.setString("mime", MimeTypes.AUDIO_RAW);
            mediaCodec.configure(this.passthroughMediaFormat, null, mediaCrypto, 0);
            this.passthroughMediaFormat.setString("mime", format.sampleMimeType);
            return;
        }
        mediaCodec.configure(format.getFrameworkMediaFormatV16(), null, mediaCrypto, 0);
        this.passthroughMediaFormat = null;
    }

    protected MediaCodecInfo getDecoderInfo(MediaCodecSelector mediaCodecSelector, Format format, boolean z) {
        if (allowPassthrough(format.sampleMimeType)) {
            MediaCodecInfo passthroughDecoderInfo = mediaCodecSelector.getPassthroughDecoderInfo();
            if (passthroughDecoderInfo != null) {
                this.passthroughEnabled = true;
                return passthroughDecoderInfo;
            }
        }
        this.passthroughEnabled = false;
        return super.getDecoderInfo(mediaCodecSelector, format, z);
    }

    public MediaClock getMediaClock() {
        return this;
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.audioTrack.getPlaybackParameters();
    }

    public long getPositionUs() {
        long currentPositionUs = this.audioTrack.getCurrentPositionUs(isEnded());
        if (currentPositionUs != Long.MIN_VALUE) {
            if (!this.allowPositionDiscontinuity) {
                currentPositionUs = Math.max(this.currentPositionUs, currentPositionUs);
            }
            this.currentPositionUs = currentPositionUs;
            this.allowPositionDiscontinuity = false;
        }
        return this.currentPositionUs;
    }

    public void handleMessage(int i, Object obj) {
        switch (i) {
            case 2:
                this.audioTrack.setVolume(((Float) obj).floatValue());
                return;
            case 3:
                this.audioTrack.setAudioAttributes((AudioAttributes) obj);
                return;
            default:
                super.handleMessage(i, obj);
                return;
        }
    }

    public boolean isEnded() {
        return super.isEnded() && this.audioTrack.isEnded();
    }

    public boolean isReady() {
        return this.audioTrack.hasPendingData() || super.isReady();
    }

    protected void onAudioSessionId(int i) {
    }

    protected void onAudioTrackPositionDiscontinuity() {
    }

    protected void onAudioTrackUnderrun(int i, long j, long j2) {
    }

    protected void onCodecInitialized(String str, long j, long j2) {
        this.eventDispatcher.decoderInitialized(str, j, j2);
    }

    protected void onDisabled() {
        try {
            this.audioTrack.release();
            try {
                super.onDisabled();
            } finally {
                this.decoderCounters.ensureUpdated();
                this.eventDispatcher.disabled(this.decoderCounters);
            }
        } catch (Throwable th) {
            super.onDisabled();
        } finally {
            this.decoderCounters.ensureUpdated();
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    protected void onEnabled(boolean z) {
        super.onEnabled(z);
        this.eventDispatcher.enabled(this.decoderCounters);
        int i = getConfiguration().tunnelingAudioSessionId;
        if (i != 0) {
            this.audioTrack.enableTunnelingV21(i);
        } else {
            this.audioTrack.disableTunneling();
        }
    }

    protected void onInputFormatChanged(Format format) {
        super.onInputFormatChanged(format);
        this.eventDispatcher.inputFormatChanged(format);
        this.pcmEncoding = MimeTypes.AUDIO_RAW.equals(format.sampleMimeType) ? format.pcmEncoding : 2;
        this.channelCount = format.channelCount;
    }

    protected void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        int[] iArr;
        int i = 0;
        int i2 = this.passthroughMediaFormat != null ? 1 : 0;
        String string = i2 != 0 ? this.passthroughMediaFormat.getString("mime") : MimeTypes.AUDIO_RAW;
        if (i2 != 0) {
            mediaFormat = this.passthroughMediaFormat;
        }
        i2 = mediaFormat.getInteger("channel-count");
        int integer = mediaFormat.getInteger("sample-rate");
        if (this.codecNeedsDiscardChannelsWorkaround && i2 == 6 && this.channelCount < 6) {
            iArr = new int[this.channelCount];
            while (i < this.channelCount) {
                iArr[i] = i;
                i++;
            }
        } else {
            iArr = null;
        }
        try {
            this.audioTrack.configure(string, i2, integer, this.pcmEncoding, 0, iArr);
        } catch (Exception e) {
            throw ExoPlaybackException.createForRenderer(e, getIndex());
        }
    }

    protected void onPositionReset(long j, boolean z) {
        super.onPositionReset(j, z);
        this.audioTrack.reset();
        this.currentPositionUs = j;
        this.allowPositionDiscontinuity = true;
    }

    protected void onStarted() {
        super.onStarted();
        this.audioTrack.play();
    }

    protected void onStopped() {
        this.audioTrack.pause();
        super.onStopped();
    }

    protected boolean processOutputBuffer(long j, long j2, MediaCodec mediaCodec, ByteBuffer byteBuffer, int i, int i2, long j3, boolean z) {
        Exception e;
        if (this.passthroughEnabled && (i2 & 2) != 0) {
            mediaCodec.releaseOutputBuffer(i, false);
            return true;
        } else if (z) {
            mediaCodec.releaseOutputBuffer(i, false);
            r1 = this.decoderCounters;
            r1.skippedOutputBufferCount++;
            this.audioTrack.handleDiscontinuity();
            return true;
        } else {
            try {
                if (!this.audioTrack.handleBuffer(byteBuffer, j3)) {
                    return false;
                }
                mediaCodec.releaseOutputBuffer(i, false);
                r1 = this.decoderCounters;
                r1.renderedOutputBufferCount++;
                return true;
            } catch (InitializationException e2) {
                e = e2;
                throw ExoPlaybackException.createForRenderer(e, getIndex());
            } catch (WriteException e3) {
                e = e3;
                throw ExoPlaybackException.createForRenderer(e, getIndex());
            }
        }
    }

    protected void renderToEndOfStream() {
        try {
            this.audioTrack.playToEndOfStream();
        } catch (Exception e) {
            throw ExoPlaybackException.createForRenderer(e, getIndex());
        }
    }

    public PlaybackParameters setPlaybackParameters(PlaybackParameters playbackParameters) {
        return this.audioTrack.setPlaybackParameters(playbackParameters);
    }

    protected int supportsFormat(MediaCodecSelector mediaCodecSelector, Format format) {
        boolean z = false;
        String str = format.sampleMimeType;
        if (!MimeTypes.isAudio(str)) {
            return 0;
        }
        int i = Util.SDK_INT >= 21 ? 32 : 0;
        if (allowPassthrough(str) && mediaCodecSelector.getPassthroughDecoderInfo() != null) {
            return (i | 8) | 4;
        }
        MediaCodecInfo decoderInfo = mediaCodecSelector.getDecoderInfo(str, false);
        if (decoderInfo == null) {
            return 1;
        }
        if (Util.SDK_INT < 21 || ((format.sampleRate == -1 || decoderInfo.isAudioSampleRateSupportedV21(format.sampleRate)) && (format.channelCount == -1 || decoderInfo.isAudioChannelCountSupportedV21(format.channelCount)))) {
            z = true;
        }
        return (z ? 4 : 3) | (i | 8);
    }
}
