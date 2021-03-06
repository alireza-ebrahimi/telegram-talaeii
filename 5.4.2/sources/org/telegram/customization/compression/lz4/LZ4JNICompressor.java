package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.p160a.C2672a;
import org.telegram.customization.compression.p160a.C2675c;

final class LZ4JNICompressor extends LZ4Compressor {
    static final /* synthetic */ boolean $assertionsDisabled = (!LZ4JNICompressor.class.desiredAssertionStatus());
    public static final LZ4Compressor INSTANCE = new LZ4JNICompressor();
    private static LZ4Compressor SAFE_INSTANCE;

    LZ4JNICompressor() {
    }

    public int compress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        C2672a.m12565b(byteBuffer2);
        C2672a.m12562a(byteBuffer, i, i2);
        C2672a.m12562a(byteBuffer2, i3, i4);
        if ((byteBuffer.hasArray() || byteBuffer.isDirect()) && (byteBuffer2.hasArray() || byteBuffer2.isDirect())) {
            byte[] array;
            int arrayOffset;
            ByteBuffer byteBuffer3;
            byte[] array2;
            int arrayOffset2;
            ByteBuffer byteBuffer4;
            if (byteBuffer.hasArray()) {
                array = byteBuffer.array();
                arrayOffset = i + byteBuffer.arrayOffset();
                byteBuffer3 = null;
            } else if ($assertionsDisabled || byteBuffer.isDirect()) {
                byteBuffer3 = byteBuffer;
                array = null;
                arrayOffset = i;
            } else {
                throw new AssertionError();
            }
            if (byteBuffer2.hasArray()) {
                array2 = byteBuffer2.array();
                arrayOffset2 = i3 + byteBuffer2.arrayOffset();
                byteBuffer4 = null;
            } else if ($assertionsDisabled || byteBuffer2.isDirect()) {
                byteBuffer4 = byteBuffer2;
                array2 = null;
                arrayOffset2 = i3;
            } else {
                throw new AssertionError();
            }
            int LZ4_compress_limitedOutput = LZ4JNI.LZ4_compress_limitedOutput(array, byteBuffer3, arrayOffset, i2, array2, byteBuffer4, arrayOffset2, i4);
            if (LZ4_compress_limitedOutput > 0) {
                return LZ4_compress_limitedOutput;
            }
            throw new LZ4Exception("maxDestLen is too small");
        }
        LZ4Compressor lZ4Compressor = SAFE_INSTANCE;
        if (lZ4Compressor == null) {
            lZ4Compressor = LZ4Factory.safeInstance().fastCompressor();
            SAFE_INSTANCE = lZ4Compressor;
        }
        return lZ4Compressor.compress(byteBuffer, i, i2, byteBuffer2, i3, i4);
    }

    public int compress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        C2675c.m12581a(bArr, i, i2);
        C2675c.m12581a(bArr2, i3, i4);
        int LZ4_compress_limitedOutput = LZ4JNI.LZ4_compress_limitedOutput(bArr, null, i, i2, bArr2, null, i3, i4);
        if (LZ4_compress_limitedOutput > 0) {
            return LZ4_compress_limitedOutput;
        }
        throw new LZ4Exception("maxDestLen is too small");
    }
}
