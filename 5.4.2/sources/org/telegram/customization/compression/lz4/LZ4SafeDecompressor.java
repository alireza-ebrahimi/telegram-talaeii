package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import java.util.Arrays;

public abstract class LZ4SafeDecompressor implements LZ4UnknownSizeDecompressor {
    public abstract int decompress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4);

    public final int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        return decompress(bArr, i, i2, bArr2, i3, bArr2.length - i3);
    }

    public abstract int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4);

    public final int decompress(byte[] bArr, byte[] bArr2) {
        return decompress(bArr, 0, bArr.length, bArr2, 0);
    }

    public final void decompress(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        int decompress = decompress(byteBuffer, byteBuffer.position(), byteBuffer.remaining(), byteBuffer2, byteBuffer2.position(), byteBuffer2.remaining());
        byteBuffer.position(byteBuffer.limit());
        byteBuffer2.position(decompress + byteBuffer2.position());
    }

    public final byte[] decompress(byte[] bArr, int i) {
        return decompress(bArr, 0, bArr.length, i);
    }

    public final byte[] decompress(byte[] bArr, int i, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        int decompress = decompress(bArr, i, i2, bArr2, 0, i3);
        return decompress != bArr2.length ? Arrays.copyOf(bArr2, decompress) : bArr2;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
