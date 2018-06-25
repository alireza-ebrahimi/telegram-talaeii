package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import java.util.Arrays;

public abstract class LZ4Compressor {
    public abstract int compress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4);

    public abstract int compress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4);

    public final int maxCompressedLength(int length) {
        return LZ4Utils.maxCompressedLength(length);
    }

    public final int compress(byte[] src, int srcOff, int srcLen, byte[] dest, int destOff) {
        return compress(src, srcOff, srcLen, dest, destOff, dest.length - destOff);
    }

    public final int compress(byte[] src, byte[] dest) {
        return compress(src, 0, src.length, dest, 0);
    }

    public final byte[] compress(byte[] src, int srcOff, int srcLen) {
        byte[] compressed = new byte[maxCompressedLength(srcLen)];
        return Arrays.copyOf(compressed, compress(src, srcOff, srcLen, compressed, 0));
    }

    public final byte[] compress(byte[] src) {
        return compress(src, 0, src.length);
    }

    public final void compress(ByteBuffer src, ByteBuffer dest) {
        int cpLen = compress(src, src.position(), src.remaining(), dest, dest.position(), dest.remaining());
        src.position(src.limit());
        dest.position(dest.position() + cpLen);
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
