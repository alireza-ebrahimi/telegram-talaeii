package org.telegram.customization.compression.lz4;

import java.io.IOException;
import java.io.OutputStream;

public class LZ4OutputStream extends OutputStream {
    private static final int ONE_MEGABYTE = 1048576;
    private static final LZ4Factory lz4Factory = LZ4Factory.fastestInstance();
    private int bytesRemainingInCompressionInputBuffer;
    private final byte[] compressionInputBuffer;
    private final byte[] compressionOutputBuffer;
    private final LZ4Compressor compressor;
    private int currentCompressionInputBufferPosition;
    private final OutputStream underlyingOutputStream;

    public LZ4OutputStream(OutputStream os) throws IOException {
        this(os, 1048576, lz4Factory.fastCompressor());
    }

    public LZ4OutputStream(OutputStream os, int size) throws IOException {
        this(os, size, lz4Factory.fastCompressor());
    }

    public LZ4OutputStream(OutputStream underlyingOutputStream, int blocksize, LZ4Compressor compressor) throws IOException {
        this.bytesRemainingInCompressionInputBuffer = 0;
        this.currentCompressionInputBufferPosition = 0;
        this.compressionInputBuffer = new byte[blocksize];
        this.compressor = compressor;
        this.underlyingOutputStream = underlyingOutputStream;
        this.bytesRemainingInCompressionInputBuffer = blocksize;
        this.currentCompressionInputBufferPosition = 0;
        this.compressionOutputBuffer = new byte[compressor.maxCompressedLength(blocksize)];
    }

    public void write(byte[] b, int off, int len) throws IOException {
        if (len <= this.bytesRemainingInCompressionInputBuffer) {
            System.arraycopy(b, off, this.compressionInputBuffer, this.currentCompressionInputBufferPosition, len);
            this.currentCompressionInputBufferPosition += len;
            this.bytesRemainingInCompressionInputBuffer -= len;
            return;
        }
        while (len > 0) {
            int bytesToCopy = Math.min(this.bytesRemainingInCompressionInputBuffer, len);
            System.arraycopy(b, off, this.compressionInputBuffer, this.currentCompressionInputBufferPosition, bytesToCopy);
            this.currentCompressionInputBufferPosition += bytesToCopy;
            this.bytesRemainingInCompressionInputBuffer -= bytesToCopy;
            flush();
            len -= bytesToCopy;
            off += bytesToCopy;
        }
    }

    public void write(int i) throws IOException {
        byte b = (byte) i;
        if (this.bytesRemainingInCompressionInputBuffer == 0) {
            flush();
        }
        this.compressionInputBuffer[this.currentCompressionInputBufferPosition] = b;
        this.bytesRemainingInCompressionInputBuffer--;
        this.currentCompressionInputBufferPosition++;
    }

    public void flush() throws IOException {
        if (this.currentCompressionInputBufferPosition > 0) {
            LZ4StreamHelper.writeLength(this.currentCompressionInputBufferPosition, this.underlyingOutputStream);
            int bytesCompressed = this.compressor.compress(this.compressionInputBuffer, 0, this.currentCompressionInputBufferPosition, this.compressionOutputBuffer, 0, this.compressionOutputBuffer.length);
            LZ4StreamHelper.writeLength(bytesCompressed, this.underlyingOutputStream);
            this.underlyingOutputStream.write(this.compressionOutputBuffer, 0, bytesCompressed);
            this.bytesRemainingInCompressionInputBuffer = this.compressionInputBuffer.length;
            this.currentCompressionInputBufferPosition = 0;
        }
    }

    public void close() throws IOException {
        flush();
        this.underlyingOutputStream.close();
    }
}
