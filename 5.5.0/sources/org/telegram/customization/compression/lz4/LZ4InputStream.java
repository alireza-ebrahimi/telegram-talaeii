package org.telegram.customization.compression.lz4;

import java.io.InputStream;
import org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;

public class LZ4InputStream extends InputStream {
    private static LZ4Factory factory = LZ4Factory.fastestInstance();
    private byte[] compressedBuffer;
    private byte[] decompressedBuffer;
    private int decompressedBufferLength;
    private int decompressedBufferPosition;
    private final LZ4Decompressor decompressor;
    private final InputStream inputStream;

    public LZ4InputStream(InputStream inputStream) {
        this(inputStream, ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES);
    }

    public LZ4InputStream(InputStream inputStream, int i) {
        this.decompressedBufferPosition = 0;
        this.decompressedBufferLength = 0;
        this.decompressor = factory.decompressor();
        this.inputStream = inputStream;
        this.compressedBuffer = new byte[i];
        this.decompressedBuffer = new byte[i];
    }

    private boolean blockHeadersIndicateNoMoreData(int i, int i2) {
        return i < 0 || i2 < 0;
    }

    private void ensureBufferCapacity(int i, int i2) {
        if (i > this.compressedBuffer.length) {
            this.compressedBuffer = new byte[i];
        }
        if (i2 > this.decompressedBuffer.length) {
            this.decompressedBuffer = new byte[i2];
        }
    }

    private boolean ensureBytesAvailableInDecompressedBuffer() {
        while (this.decompressedBufferPosition >= this.decompressedBufferLength) {
            if (!fillBuffer()) {
                return false;
            }
        }
        return true;
    }

    private boolean fillBuffer() {
        this.decompressedBufferLength = LZ4StreamHelper.readLength(this.inputStream);
        int readLength = LZ4StreamHelper.readLength(this.inputStream);
        if (blockHeadersIndicateNoMoreData(readLength, this.decompressedBufferLength)) {
            return false;
        }
        ensureBufferCapacity(readLength, this.decompressedBufferLength);
        if (!fillCompressedBuffer(readLength)) {
            return false;
        }
        this.decompressor.decompress(this.compressedBuffer, 0, this.decompressedBuffer, 0, this.decompressedBufferLength);
        this.decompressedBufferPosition = 0;
        return true;
    }

    private boolean fillCompressedBuffer(int i) {
        int i2 = 0;
        while (i2 < i) {
            int read = this.inputStream.read(this.compressedBuffer, i2, i - i2);
            if (read < 0) {
                return false;
            }
            i2 += read;
        }
        return true;
    }

    public void close() {
        this.inputStream.close();
    }

    public boolean hasBytesAvailableInDecompressedBuffer(int i) {
        return this.decompressedBufferPosition + i <= this.decompressedBufferLength;
    }

    public int read() {
        if (!ensureBytesAvailableInDecompressedBuffer()) {
            return -1;
        }
        byte[] bArr = this.decompressedBuffer;
        int i = this.decompressedBufferPosition;
        this.decompressedBufferPosition = i + 1;
        return bArr[i] & 255;
    }

    public int read(byte[] bArr, int i, int i2) {
        if (!ensureBytesAvailableInDecompressedBuffer()) {
            return -1;
        }
        int i3 = i2 - i;
        while (i3 > 0 && ensureBytesAvailableInDecompressedBuffer()) {
            int i4 = this.decompressedBufferLength - this.decompressedBufferPosition;
            if (i3 <= i4) {
                i4 = i3;
            }
            System.arraycopy(this.decompressedBuffer, this.decompressedBufferPosition, bArr, i, i4);
            this.decompressedBufferPosition += i4;
            i += i4;
            i3 -= i4;
        }
        return i2 - i3;
    }

    public long skip(long j) {
        long j2 = j;
        while (j2 > 0 && ensureBytesAvailableInDecompressedBuffer()) {
            int i = this.decompressedBufferLength - this.decompressedBufferPosition;
            long j3 = j2 > ((long) i) ? (long) i : j2;
            j2 -= j3;
            this.decompressedBufferPosition = (int) (j3 + ((long) this.decompressedBufferPosition));
        }
        return j - j2;
    }
}
