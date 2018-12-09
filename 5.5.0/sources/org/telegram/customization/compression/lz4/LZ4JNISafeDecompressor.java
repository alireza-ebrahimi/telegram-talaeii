package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.p160a.C2672a;
import org.telegram.customization.compression.p160a.C2675c;

final class LZ4JNISafeDecompressor extends LZ4SafeDecompressor {
    static final /* synthetic */ boolean $assertionsDisabled = (!LZ4JNISafeDecompressor.class.desiredAssertionStatus());
    public static final LZ4JNISafeDecompressor INSTANCE = new LZ4JNISafeDecompressor();
    private static LZ4SafeDecompressor SAFE_INSTANCE;

    LZ4JNISafeDecompressor() {
    }

    public int decompress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
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
            int LZ4_decompress_safe = LZ4JNI.LZ4_decompress_safe(array, byteBuffer3, arrayOffset, i2, array2, byteBuffer4, arrayOffset2, i4);
            if (LZ4_decompress_safe >= 0) {
                return LZ4_decompress_safe;
            }
            throw new LZ4Exception("Error decoding offset " + (arrayOffset - LZ4_decompress_safe) + " of input buffer");
        }
        LZ4SafeDecompressor lZ4SafeDecompressor = SAFE_INSTANCE;
        if (lZ4SafeDecompressor == null) {
            lZ4SafeDecompressor = LZ4Factory.safeInstance().safeDecompressor();
            SAFE_INSTANCE = lZ4SafeDecompressor;
        }
        return lZ4SafeDecompressor.decompress(byteBuffer, i, i2, byteBuffer2, i3, i4);
    }

    public final int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        C2675c.m12581a(bArr, i, i2);
        C2675c.m12581a(bArr2, i3, i4);
        int LZ4_decompress_safe = LZ4JNI.LZ4_decompress_safe(bArr, null, i, i2, bArr2, null, i3, i4);
        if (LZ4_decompress_safe >= 0) {
            return LZ4_decompress_safe;
        }
        throw new LZ4Exception("Error decoding offset " + (i - LZ4_decompress_safe) + " of input buffer");
    }
}
