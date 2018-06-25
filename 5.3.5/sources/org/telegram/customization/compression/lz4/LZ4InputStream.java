package org.telegram.customization.compression.lz4;

import java.io.IOException;
import java.io.InputStream;

public class LZ4InputStream extends InputStream {
    private static LZ4Factory factory = LZ4Factory.fastestInstance();
    private byte[] compressedBuffer;
    private byte[] decompressedBuffer;
    private int decompressedBufferLength;
    private int decompressedBufferPosition;
    private final LZ4Decompressor decompressor;
    private final InputStream inputStream;

    public LZ4InputStream(InputStream stream) {
        this(stream, 1048576);
    }

    public LZ4InputStream(InputStream stream, int size) {
        this.decompressedBufferPosition = 0;
        this.decompressedBufferLength = 0;
        this.decompressor = factory.decompressor();
        this.inputStream = stream;
        this.compressedBuffer = new byte[size];
        this.decompressedBuffer = new byte[size];
    }

    public void close() throws IOException {
        this.inputStream.close();
    }

    public int read() throws IOException {
        if (!ensureBytesAvailableInDecompressedBuffer()) {
            return -1;
        }
        byte[] bArr = this.decompressedBuffer;
        int i = this.decompressedBufferPosition;
        this.decompressedBufferPosition = i + 1;
        return bArr[i] & 255;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        if (!ensureBytesAvailableInDecompressedBuffer()) {
            return -1;
        }
        int numBytesRemainingToRead = len - off;
        while (numBytesRemainingToRead > 0 && ensureBytesAvailableInDecompressedBuffer()) {
            int numBytesToRead = numBytesRemainingToRead;
            int numBytesRemainingInBlock = this.decompressedBufferLength - this.decompressedBufferPosition;
            if (numBytesToRead > numBytesRemainingInBlock) {
                numBytesToRead = numBytesRemainingInBlock;
            }
            System.arraycopy(this.decompressedBuffer, this.decompressedBufferPosition, b, off, numBytesToRead);
            this.decompressedBufferPosition += numBytesToRead;
            off += numBytesToRead;
            numBytesRemainingToRead -= numBytesToRead;
        }
        return len - numBytesRemainingToRead;
    }

    public long skip(long n) throws IOException {
        long numBytesRemainingToSkip = n;
        while (numBytesRemainingToSkip > 0 && ensureBytesAvailableInDecompressedBuffer()) {
            long numBytesToSkip = numBytesRemainingToSkip;
            int numBytesRemainingInBlock = this.decompressedBufferLength - this.decompressedBufferPosition;
            if (numBytesToSkip > ((long) numBytesRemainingInBlock)) {
                numBytesToSkip = (long) numBytesRemainingInBlock;
            }
            numBytesRemainingToSkip -= numBytesToSkip;
            this.decompressedBufferPosition = (int) (((long) this.decompressedBufferPosition) + numBytesToSkip);
        }
        return n - numBytesRemainingToSkip;
    }

    public boolean hasBytesAvailableInDecompressedBuffer(int bytes) {
        return this.decompressedBufferPosition + bytes <= this.decompressedBufferLength;
    }

    private boolean ensureBytesAvailableInDecompressedBuffer() throws IOException {
        while (this.decompressedBufferPosition >= this.decompressedBufferLength) {
            if (!fillBuffer()) {
                return false;
            }
        }
        return true;
    }

    private boolean fillBuffer() throws IOException {
        this.decompressedBufferLength = LZ4StreamHelper.readLength(this.inputStream);
        int compressedBufferLength = LZ4StreamHelper.readLength(this.inputStream);
        if (blockHeadersIndicateNoMoreData(compressedBufferLength, this.decompressedBufferLength)) {
            return false;
        }
        ensureBufferCapacity(compressedBufferLength, this.decompressedBufferLength);
        if (!fillCompressedBuffer(compressedBufferLength)) {
            return false;
        }
        this.decompressor.decompress(this.compressedBuffer, 0, this.decompressedBuffer, 0, this.decompressedBufferLength);
        this.decompressedBufferPosition = 0;
        return true;
    }

    private boolean blockHeadersIndicateNoMoreData(int compressedBufferLength, int decompressedBufferLength) {
        return compressedBufferLength < 0 || decompressedBufferLength < 0;
    }

    private boolean fillCompressedBuffer(int compressedBufferLength) throws IOException {
        int bytesRead = 0;
        while (bytesRead < compressedBufferLength) {
            int bytesReadInAttempt = this.inputStream.read(this.compressedBuffer, bytesRead, compressedBufferLength - bytesRead);
            if (bytesReadInAttempt < 0) {
                return false;
            }
            bytesRead += bytesReadInAttempt;
        }
        return true;
    }

    private void ensureBufferCapacity(int compressedBufferLength, int decompressedBufferLength) {
        if (compressedBufferLength > this.compressedBuffer.length) {
            this.compressedBuffer = new byte[compressedBufferLength];
        }
        if (decompressedBufferLength > this.decompressedBuffer.length) {
            this.decompressedBuffer = new byte[decompressedBufferLength];
        }
    }
}
