package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.util.ByteBufferUtils;
import org.telegram.customization.compression.util.SafeUtils;

final class LZ4JNIFastDecompressor extends LZ4FastDecompressor {
    static final /* synthetic */ boolean $assertionsDisabled = (!LZ4JNIFastDecompressor.class.desiredAssertionStatus());
    public static final LZ4JNIFastDecompressor INSTANCE = new LZ4JNIFastDecompressor();
    private static LZ4FastDecompressor SAFE_INSTANCE;

    LZ4JNIFastDecompressor() {
    }

    public final int decompress(byte[] src, int srcOff, byte[] dest, int destOff, int destLen) {
        SafeUtils.checkRange(src, srcOff);
        SafeUtils.checkRange(dest, destOff, destLen);
        int result = LZ4JNI.LZ4_decompress_fast(src, null, srcOff, dest, null, destOff, destLen);
        if (result >= 0) {
            return result;
        }
        throw new LZ4Exception("Error decoding offset " + (srcOff - result) + " of input buffer");
    }

    public int decompress(ByteBuffer src, int srcOff, ByteBuffer dest, int destOff, int destLen) {
        ByteBufferUtils.checkNotReadOnly(dest);
        ByteBufferUtils.checkRange(src, srcOff);
        ByteBufferUtils.checkRange(dest, destOff, destLen);
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
            int LZ4_decompress_fast = LZ4JNI.LZ4_decompress_fast(srcArr, srcBuf, srcOff, destArr, destBuf, destOff, destLen);
            if (LZ4_decompress_fast >= 0) {
                return LZ4_decompress_fast;
            }
            throw new LZ4Exception("Error decoding offset " + (srcOff - LZ4_decompress_fast) + " of input buffer");
        }
        LZ4FastDecompressor safeInstance = SAFE_INSTANCE;
        if (safeInstance == null) {
            safeInstance = LZ4Factory.safeInstance().fastDecompressor();
            SAFE_INSTANCE = safeInstance;
        }
        return safeInstance.decompress(src, srcOff, dest, destOff, destLen);
    }
}
