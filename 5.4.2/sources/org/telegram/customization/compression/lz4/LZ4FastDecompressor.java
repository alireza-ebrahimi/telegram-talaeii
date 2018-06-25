package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;

public abstract class LZ4FastDecompressor implements LZ4Decompressor {
    public abstract int decompress(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3);

    public abstract int decompress(byte[] bArr, int i, byte[] bArr2, int i2, int i3);

    public final int decompress(byte[] bArr, byte[] bArr2) {
        return decompress(bArr, bArr2, bArr2.length);
    }

    public final int decompress(byte[] bArr, byte[] bArr2, int i) {
        return decompress(bArr, 0, bArr2, 0, i);
    }

    public final void decompress(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        int decompress = decompress(byteBuffer, byteBuffer.position(), byteBuffer2, byteBuffer2.position(), byteBuffer2.remaining());
        byteBuffer2.position(byteBuffer2.limit());
        byteBuffer.position(decompress + byteBuffer.position());
    }

    public final byte[] decompress(byte[] bArr, int i) {
        return decompress(bArr, 0, i);
    }

    public final byte[] decompress(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        decompress(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
