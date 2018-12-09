package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.p160a.C2672a;
import org.telegram.customization.compression.p160a.C2675c;

final class LZ4JNIFastDecompressor extends LZ4FastDecompressor {
    static final /* synthetic */ boolean $assertionsDisabled = (!LZ4JNIFastDecompressor.class.desiredAssertionStatus());
    public static final LZ4JNIFastDecompressor INSTANCE = new LZ4JNIFastDecompressor();
    private static LZ4FastDecompressor SAFE_INSTANCE;

    LZ4JNIFastDecompressor() {
    }

    public int decompress(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3) {
        byte[] bArr = null;
        C2672a.m12565b(byteBuffer2);
        C2672a.m12561a(byteBuffer, i);
        C2672a.m12562a(byteBuffer2, i2, i3);
        if ((byteBuffer.hasArray() || byteBuffer.isDirect()) && (byteBuffer2.hasArray() || byteBuffer2.isDirect())) {
            byte[] array;
            int arrayOffset;
            ByteBuffer byteBuffer3;
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
                arrayOffset2 = byteBuffer2.arrayOffset() + i2;
                bArr = byteBuffer2.array();
                byteBuffer4 = null;
            } else if ($assertionsDisabled || byteBuffer2.isDirect()) {
                byteBuffer4 = byteBuffer2;
                arrayOffset2 = i2;
            } else {
                throw new AssertionError();
            }
            int LZ4_decompress_fast = LZ4JNI.LZ4_decompress_fast(array, byteBuffer3, arrayOffset, bArr, byteBuffer4, arrayOffset2, i3);
            if (LZ4_decompress_fast >= 0) {
                return LZ4_decompress_fast;
            }
            throw new LZ4Exception("Error decoding offset " + (arrayOffset - LZ4_decompress_fast) + " of input buffer");
        }
        LZ4FastDecompressor lZ4FastDecompressor = SAFE_INSTANCE;
        if (lZ4FastDecompressor == null) {
            lZ4FastDecompressor = LZ4Factory.safeInstance().fastDecompressor();
            SAFE_INSTANCE = lZ4FastDecompressor;
        }
        return lZ4FastDecompressor.decompress(byteBuffer, i, byteBuffer2, i2, i3);
    }

    public final int decompress(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        C2675c.m12580a(bArr, i);
        C2675c.m12581a(bArr2, i2, i3);
        int LZ4_decompress_fast = LZ4JNI.LZ4_decompress_fast(bArr, null, i, bArr2, null, i2, i3);
        if (LZ4_decompress_fast >= 0) {
            return LZ4_decompress_fast;
        }
        throw new LZ4Exception("Error decoding offset " + (i - LZ4_decompress_fast) + " of input buffer");
    }
}
