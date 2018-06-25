package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import java.util.Arrays;

public abstract class LZ4Compressor {
    public abstract int compress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4);

    public final int compress(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        return compress(bArr, i, i2, bArr2, i3, bArr2.length - i3);
    }

    public abstract int compress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4);

    public final int compress(byte[] bArr, byte[] bArr2) {
        return compress(bArr, 0, bArr.length, bArr2, 0);
    }

    public final void compress(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        int compress = compress(byteBuffer, byteBuffer.position(), byteBuffer.remaining(), byteBuffer2, byteBuffer2.position(), byteBuffer2.remaining());
        byteBuffer.position(byteBuffer.limit());
        byteBuffer2.position(compress + byteBuffer2.position());
    }

    public final byte[] compress(byte[] bArr) {
        return compress(bArr, 0, bArr.length);
    }

    public final byte[] compress(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[maxCompressedLength(i2)];
        return Arrays.copyOf(bArr2, compress(bArr, i, i2, bArr2, 0));
    }

    public final int maxCompressedLength(int i) {
        return LZ4Utils.maxCompressedLength(i);
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
