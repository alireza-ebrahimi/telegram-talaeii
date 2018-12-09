package org.telegram.messenger.exoplayer2.mediacodec;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.media.MediaCodecInfo.AudioCapabilities;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.media.MediaCodecInfo.VideoCapabilities;
import android.util.Log;
import android.util.Pair;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.Util;

@TargetApi(16)
public final class MediaCodecInfo {
    public static final String TAG = "MediaCodecInfo";
    public final boolean adaptive;
    private final CodecCapabilities capabilities;
    private final String mimeType;
    public final String name;
    public final boolean secure;
    public final boolean tunneling;

    private MediaCodecInfo(String str, String str2, CodecCapabilities codecCapabilities, boolean z, boolean z2) {
        boolean z3 = false;
        this.name = (String) Assertions.checkNotNull(str);
        this.mimeType = str2;
        this.capabilities = codecCapabilities;
        boolean z4 = (z || codecCapabilities == null || !isAdaptive(codecCapabilities)) ? false : true;
        this.adaptive = z4;
        z4 = codecCapabilities != null && isTunneling(codecCapabilities);
        this.tunneling = z4;
        if (z2 || (codecCapabilities != null && isSecure(codecCapabilities))) {
            z3 = true;
        }
        this.secure = z3;
    }

    private static int adjustMaxInputChannelCount(String str, String str2, int i) {
        if (i > 1) {
            return i;
        }
        if ((Util.SDK_INT >= 26 && i > 0) || MimeTypes.AUDIO_MPEG.equals(str2) || MimeTypes.AUDIO_AMR_NB.equals(str2) || MimeTypes.AUDIO_AMR_WB.equals(str2) || MimeTypes.AUDIO_AAC.equals(str2) || MimeTypes.AUDIO_VORBIS.equals(str2) || MimeTypes.AUDIO_OPUS.equals(str2) || MimeTypes.AUDIO_RAW.equals(str2) || MimeTypes.AUDIO_FLAC.equals(str2) || MimeTypes.AUDIO_ALAW.equals(str2) || MimeTypes.AUDIO_MLAW.equals(str2) || MimeTypes.AUDIO_MSGSM.equals(str2)) {
            return i;
        }
        int i2 = MimeTypes.AUDIO_AC3.equals(str2) ? 6 : MimeTypes.AUDIO_E_AC3.equals(str2) ? 16 : 30;
        Log.w(TAG, "AssumedMaxChannelAdjustment: " + str + ", [" + i + " to " + i2 + "]");
        return i2;
    }

    @TargetApi(21)
    private static boolean areSizeAndRateSupportedV21(VideoCapabilities videoCapabilities, int i, int i2, double d) {
        return (d == -1.0d || d <= 0.0d) ? videoCapabilities.isSizeSupported(i, i2) : videoCapabilities.areSizeAndRateSupported(i, i2, d);
    }

    private static boolean isAdaptive(CodecCapabilities codecCapabilities) {
        return Util.SDK_INT >= 19 && isAdaptiveV19(codecCapabilities);
    }

    @TargetApi(19)
    private static boolean isAdaptiveV19(CodecCapabilities codecCapabilities) {
        return codecCapabilities.isFeatureSupported("adaptive-playback");
    }

    private static boolean isSecure(CodecCapabilities codecCapabilities) {
        return Util.SDK_INT >= 21 && isSecureV21(codecCapabilities);
    }

    @TargetApi(21)
    private static boolean isSecureV21(CodecCapabilities codecCapabilities) {
        return codecCapabilities.isFeatureSupported("secure-playback");
    }

    private static boolean isTunneling(CodecCapabilities codecCapabilities) {
        return Util.SDK_INT >= 21 && isTunnelingV21(codecCapabilities);
    }

    @TargetApi(21)
    private static boolean isTunnelingV21(CodecCapabilities codecCapabilities) {
        return codecCapabilities.isFeatureSupported("tunneled-playback");
    }

    private void logAssumedSupport(String str) {
        Log.d(TAG, "AssumedSupport [" + str + "] [" + this.name + ", " + this.mimeType + "] [" + Util.DEVICE_DEBUG_INFO + "]");
    }

    private void logNoSupport(String str) {
        Log.d(TAG, "NoSupport [" + str + "] [" + this.name + ", " + this.mimeType + "] [" + Util.DEVICE_DEBUG_INFO + "]");
    }

    public static MediaCodecInfo newInstance(String str, String str2, CodecCapabilities codecCapabilities) {
        return new MediaCodecInfo(str, str2, codecCapabilities, false, false);
    }

    public static MediaCodecInfo newInstance(String str, String str2, CodecCapabilities codecCapabilities, boolean z, boolean z2) {
        return new MediaCodecInfo(str, str2, codecCapabilities, z, z2);
    }

    public static MediaCodecInfo newPassthroughInstance(String str) {
        return new MediaCodecInfo(str, null, null, false, false);
    }

    @TargetApi(21)
    public Point alignVideoSizeV21(int i, int i2) {
        if (this.capabilities == null) {
            logNoSupport("align.caps");
            return null;
        }
        VideoCapabilities videoCapabilities = this.capabilities.getVideoCapabilities();
        if (videoCapabilities == null) {
            logNoSupport("align.vCaps");
            return null;
        }
        int widthAlignment = videoCapabilities.getWidthAlignment();
        int heightAlignment = videoCapabilities.getHeightAlignment();
        return new Point(widthAlignment * Util.ceilDivide(i, widthAlignment), heightAlignment * Util.ceilDivide(i2, heightAlignment));
    }

    public CodecProfileLevel[] getProfileLevels() {
        return (this.capabilities == null || this.capabilities.profileLevels == null) ? new CodecProfileLevel[0] : this.capabilities.profileLevels;
    }

    @TargetApi(21)
    public boolean isAudioChannelCountSupportedV21(int i) {
        if (this.capabilities == null) {
            logNoSupport("channelCount.caps");
            return false;
        }
        AudioCapabilities audioCapabilities = this.capabilities.getAudioCapabilities();
        if (audioCapabilities == null) {
            logNoSupport("channelCount.aCaps");
            return false;
        } else if (adjustMaxInputChannelCount(this.name, this.mimeType, audioCapabilities.getMaxInputChannelCount()) >= i) {
            return true;
        } else {
            logNoSupport("channelCount.support, " + i);
            return false;
        }
    }

    @TargetApi(21)
    public boolean isAudioSampleRateSupportedV21(int i) {
        if (this.capabilities == null) {
            logNoSupport("sampleRate.caps");
            return false;
        }
        AudioCapabilities audioCapabilities = this.capabilities.getAudioCapabilities();
        if (audioCapabilities == null) {
            logNoSupport("sampleRate.aCaps");
            return false;
        } else if (audioCapabilities.isSampleRateSupported(i)) {
            return true;
        } else {
            logNoSupport("sampleRate.support, " + i);
            return false;
        }
    }

    public boolean isCodecSupported(String str) {
        if (str == null || this.mimeType == null) {
            return true;
        }
        String mediaMimeType = MimeTypes.getMediaMimeType(str);
        if (mediaMimeType == null) {
            return true;
        }
        if (this.mimeType.equals(mediaMimeType)) {
            Pair codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(str);
            if (codecProfileAndLevel == null) {
                return true;
            }
            for (CodecProfileLevel codecProfileLevel : getProfileLevels()) {
                if (codecProfileLevel.profile == ((Integer) codecProfileAndLevel.first).intValue() && codecProfileLevel.level >= ((Integer) codecProfileAndLevel.second).intValue()) {
                    return true;
                }
            }
            logNoSupport("codec.profileLevel, " + str + ", " + mediaMimeType);
            return false;
        }
        logNoSupport("codec.mime " + str + ", " + mediaMimeType);
        return false;
    }

    @TargetApi(21)
    public boolean isVideoSizeAndRateSupportedV21(int i, int i2, double d) {
        if (this.capabilities == null) {
            logNoSupport("sizeAndRate.caps");
            return false;
        }
        VideoCapabilities videoCapabilities = this.capabilities.getVideoCapabilities();
        if (videoCapabilities == null) {
            logNoSupport("sizeAndRate.vCaps");
            return false;
        }
        if (!areSizeAndRateSupportedV21(videoCapabilities, i, i2, d)) {
            if (i >= i2 || !areSizeAndRateSupportedV21(videoCapabilities, i2, i, d)) {
                logNoSupport("sizeAndRate.support, " + i + "x" + i2 + "x" + d);
                return false;
            }
            logAssumedSupport("sizeAndRate.rotated, " + i + "x" + i2 + "x" + d);
        }
        return true;
    }
}
