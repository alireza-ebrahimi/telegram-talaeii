package org.telegram.messenger.exoplayer2.mediacodec;

import android.annotation.TargetApi;
import android.media.MediaCodec.CodecException;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.util.Util;

public class MediaCodecRenderer$DecoderInitializationException extends Exception {
    private static final int CUSTOM_ERROR_CODE_BASE = -50000;
    private static final int DECODER_QUERY_ERROR = -49998;
    private static final int NO_SUITABLE_DECODER_ERROR = -49999;
    public final String decoderName;
    public final String diagnosticInfo;
    public final String mimeType;
    public final boolean secureDecoderRequired;

    public MediaCodecRenderer$DecoderInitializationException(Format format, Throwable cause, boolean secureDecoderRequired, int errorCode) {
        super("Decoder init failed: [" + errorCode + "], " + format, cause);
        this.mimeType = format.sampleMimeType;
        this.secureDecoderRequired = secureDecoderRequired;
        this.decoderName = null;
        this.diagnosticInfo = buildCustomDiagnosticInfo(errorCode);
    }

    public MediaCodecRenderer$DecoderInitializationException(Format format, Throwable cause, boolean secureDecoderRequired, String decoderName) {
        super("Decoder init failed: " + decoderName + ", " + format, cause);
        this.mimeType = format.sampleMimeType;
        this.secureDecoderRequired = secureDecoderRequired;
        this.decoderName = decoderName;
        this.diagnosticInfo = Util.SDK_INT >= 21 ? getDiagnosticInfoV21(cause) : null;
    }

    @TargetApi(21)
    private static String getDiagnosticInfoV21(Throwable cause) {
        if (cause instanceof CodecException) {
            return ((CodecException) cause).getDiagnosticInfo();
        }
        return null;
    }

    private static String buildCustomDiagnosticInfo(int errorCode) {
        return "com.google.android.exoplayer.MediaCodecTrackRenderer_" + (errorCode < 0 ? "neg_" : "") + Math.abs(errorCode);
    }
}
