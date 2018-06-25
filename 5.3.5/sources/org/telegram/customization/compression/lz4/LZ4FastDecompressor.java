package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;

public abstract class LZ4FastDecompressor implements LZ4Decompressor {
    public abstract int decompress(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3);

    public abstract int decompress(byte[] bArr, int i, byte[] bArr2, int i2, int i3);

    public final int decompress(byte[] src, byte[] dest, int destLen) {
        return decompress(src, 0, dest, 0, destLen);
    }

    public final int decompress(byte[] src, byte[] dest) {
        return decompress(src, dest, dest.length);
    }

    public final byte[] decompress(byte[] src, int srcOff, int destLen) {
        byte[] decompressed = new byte[destLen];
        decompress(src, srcOff, decompressed, 0, destLen);
        return decompressed;
    }

    public final byte[] decompress(byte[] src, int destLen) {
        return decompress(src, 0, destLen);
    }

    public final void decompress(ByteBuffer src, ByteBuffer dest) {
        int read = decompress(src, src.position(), dest, dest.position(), dest.remaining());
        dest.position(dest.limit());
        src.position(src.position() + read);
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
