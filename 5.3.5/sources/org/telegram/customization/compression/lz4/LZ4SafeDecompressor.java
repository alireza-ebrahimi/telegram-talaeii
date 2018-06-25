package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import java.util.Arrays;

public abstract class LZ4SafeDecompressor implements LZ4UnknownSizeDecompressor {
    public abstract int decompress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4);

    public abstract int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4);

    public final int decompress(byte[] src, int srcOff, int srcLen, byte[] dest, int destOff) {
        return decompress(src, srcOff, srcLen, dest, destOff, dest.length - destOff);
    }

    public final int decompress(byte[] src, byte[] dest) {
        return decompress(src, 0, src.length, dest, 0);
    }

    public final byte[] decompress(byte[] src, int srcOff, int srcLen, int maxDestLen) {
        byte[] decompressed = new byte[maxDestLen];
        int decompressedLength = decompress(src, srcOff, srcLen, decompressed, 0, maxDestLen);
        if (decompressedLength != decompressed.length) {
            return Arrays.copyOf(decompressed, decompressedLength);
        }
        return decompressed;
    }

    public final byte[] decompress(byte[] src, int maxDestLen) {
        return decompress(src, 0, src.length, maxDestLen);
    }

    public final void decompress(ByteBuffer src, ByteBuffer dest) {
        int decompressed = decompress(src, src.position(), src.remaining(), dest, dest.position(), dest.remaining());
        src.position(src.limit());
        dest.position(dest.position() + decompressed);
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
