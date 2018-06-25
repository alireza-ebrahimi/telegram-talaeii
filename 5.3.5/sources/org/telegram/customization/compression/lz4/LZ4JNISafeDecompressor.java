package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.util.ByteBufferUtils;
import org.telegram.customization.compression.util.SafeUtils;

final class LZ4JNISafeDecompressor extends LZ4SafeDecompressor {
    static final /* synthetic */ boolean $assertionsDisabled = (!LZ4JNISafeDecompressor.class.desiredAssertionStatus());
    public static final LZ4JNISafeDecompressor INSTANCE = new LZ4JNISafeDecompressor();
    private static LZ4SafeDecompressor SAFE_INSTANCE;

    LZ4JNISafeDecompressor() {
    }

    public final int decompress(byte[] src, int srcOff, int srcLen, byte[] dest, int destOff, int maxDestLen) {
        SafeUtils.checkRange(src, srcOff, srcLen);
        SafeUtils.checkRange(dest, destOff, maxDestLen);
        int result = LZ4JNI.LZ4_decompress_safe(src, null, srcOff, srcLen, dest, null, destOff, maxDestLen);
        if (result >= 0) {
            return result;
        }
        throw new LZ4Exception("Error decoding offset " + (srcOff - result) + " of input buffer");
    }

    public int decompress(ByteBuffer src, int srcOff, int srcLen, ByteBuffer dest, int destOff, int maxDestLen) {
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
            int LZ4_decompress_safe = LZ4JNI.LZ4_decompress_safe(srcArr, srcBuf, srcOff, srcLen, destArr, destBuf, destOff, maxDestLen);
            if (LZ4_decompress_safe >= 0) {
                return LZ4_decompress_safe;
            }
            throw new LZ4Exception("Error decoding offset " + (srcOff - LZ4_decompress_safe) + " of input buffer");
        }
        LZ4SafeDecompressor safeInstance = SAFE_INSTANCE;
        if (safeInstance == null) {
            safeInstance = LZ4Factory.safeInstance().safeDecompressor();
            SAFE_INSTANCE = safeInstance;
        }
        return safeInstance.decompress(src, srcOff, srcLen, dest, destOff, maxDestLen);
    }
}
