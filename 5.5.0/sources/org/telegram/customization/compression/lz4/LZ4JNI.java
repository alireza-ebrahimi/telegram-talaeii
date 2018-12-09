package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import org.telegram.customization.compression.p160a.C2674b;

enum LZ4JNI {
    ;

    static {
        C2674b.m12573b();
        init();
    }

    static native int LZ4_compressBound(int i);

    static native int LZ4_compressHC(byte[] bArr, ByteBuffer byteBuffer, int i, int i2, byte[] bArr2, ByteBuffer byteBuffer2, int i3, int i4, int i5);

    static native int LZ4_compress_limitedOutput(byte[] bArr, ByteBuffer byteBuffer, int i, int i2, byte[] bArr2, ByteBuffer byteBuffer2, int i3, int i4);

    static native int LZ4_decompress_fast(byte[] bArr, ByteBuffer byteBuffer, int i, byte[] bArr2, ByteBuffer byteBuffer2, int i2, int i3);

    static native int LZ4_decompress_safe(byte[] bArr, ByteBuffer byteBuffer, int i, int i2, byte[] bArr2, ByteBuffer byteBuffer2, int i3, int i4);

    static native void init();
}
