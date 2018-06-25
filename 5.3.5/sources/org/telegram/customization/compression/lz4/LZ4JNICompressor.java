package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.util.ByteBufferUtils;
import org.telegram.customization.compression.util.SafeUtils;

final class LZ4JNICompressor extends LZ4Compressor {
    static final /* synthetic */ boolean $assertionsDisabled = (!LZ4JNICompressor.class.desiredAssertionStatus());
    public static final LZ4Compressor INSTANCE = new LZ4JNICompressor();
    private static LZ4Compressor SAFE_INSTANCE;

    LZ4JNICompressor() {
    }

    public int compress(byte[] src, int srcOff, int srcLen, byte[] dest, int destOff, int maxDestLen) {
        SafeUtils.checkRange(src, srcOff, srcLen);
        SafeUtils.checkRange(dest, destOff, maxDestLen);
        int result = LZ4JNI.LZ4_compress_limitedOutput(src, null, srcOff, srcLen, dest, null, destOff, maxDestLen);
        if (result > 0) {
            return result;
        }
        throw new LZ4Exception("maxDestLen is too small");
    }

    public int compress(ByteBuffer src, int srcOff, int srcLen, ByteBuffer dest, int destOff, int maxDestLen) {
        ByteBufferUtils.checkNotReadOnly(dest);
        ByteBufferUtils.checkRange(src, srcOff, srcLen);
        ByteBufferUtils.checkRange(dest, destOff, maxDestLen);
        if ((src.hasArray() || src.isDirect()) && (dest.hasArray() || dest.isDirect())) {
            byte[] srcArr = null;
            byte[] destArr = null;
            ByteBuffer srcBuf = null;
            ByteBuffer destBuf = null;
            if (src.hasArray()) {
                srcArr = src.array();
                srcOff += src.arrayOffset();
            } else if ($assertionsDisabled || src.isDirect()) {
                srcBuf = src;
            } else {
                throw new AssertionError();
            }
            if (dest.hasArray()) {
                destArr = dest.array();
                destOff += dest.arrayOffset();
            } else if ($assertionsDisabled || dest.isDirect()) {
                destBuf = dest;
            } else {
                throw new AssertionError();
            }
            int LZ4_compress_limitedOutput = LZ4JNI.LZ4_compress_limitedOutput(srcArr, srcBuf, srcOff, srcLen, destArr, destBuf, destOff, maxDestLen);
            if (LZ4_compress_limitedOutput > 0) {
                return LZ4_compress_limitedOutput;
            }
            throw new LZ4Exception("maxDestLen is too small");
        }
        LZ4Compressor safeInstance = SAFE_INSTANCE;
        if (safeInstance == null) {
            safeInstance = LZ4Factory.safeInstance().fastCompressor();
            SAFE_INSTANCE = safeInstance;
        }
        return safeInstance.compress(src, srcOff, srcLen, dest, destOff, maxDestLen);
    }
}
