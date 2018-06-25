package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.util.ByteBufferUtils;
import org.telegram.customization.compression.util.SafeUtils;

final class LZ4HCJNICompressor extends LZ4Compressor {
    static final /* synthetic */ boolean $assertionsDisabled = (!LZ4HCJNICompressor.class.desiredAssertionStatus());
    public static final LZ4HCJNICompressor INSTANCE = new LZ4HCJNICompressor();
    private static LZ4Compressor SAFE_INSTANCE;
    private final int compressionLevel;

    LZ4HCJNICompressor() {
        this(9);
    }

    LZ4HCJNICompressor(int compressionLevel) {
        this.compressionLevel = compressionLevel;
    }

    public int compress(byte[] src, int srcOff, int srcLen, byte[] dest, int destOff, int maxDestLen) {
        SafeUtils.checkRange(src, srcOff, srcLen);
        SafeUtils.checkRange(dest, destOff, maxDestLen);
        int result = LZ4JNI.LZ4_compressHC(src, null, srcOff, srcLen, dest, null, destOff, maxDestLen, this.compressionLevel);
        if (result > 0) {
            return result;
        }
        throw new LZ4Exception();
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
            int LZ4_compressHC = LZ4JNI.LZ4_compressHC(srcArr, srcBuf, srcOff, srcLen, destArr, destBuf, destOff, maxDestLen, this.compressionLevel);
            if (LZ4_compressHC > 0) {
                return LZ4_compressHC;
            }
            throw new LZ4Exception();
        }
        LZ4Compressor safeInstance = SAFE_INSTANCE;
        if (safeInstance == null) {
            safeInstance = LZ4Factory.safeInstance().highCompressor(this.compressionLevel);
            SAFE_INSTANCE = safeInstance;
        }
        return safeInstance.compress(src, srcOff, srcLen, dest, destOff, maxDestLen);
    }
}
