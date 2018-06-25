package org.telegram.customization.compression.lz4;

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

    public LZ4OutputStream(OutputStream outputStream) {
        this(outputStream, 1048576, lz4Factory.fastCompressor());
    }

    public LZ4OutputStream(OutputStream outputStream, int i) {
        this(outputStream, i, lz4Factory.fastCompressor());
    }

    public LZ4OutputStream(OutputStream outputStream, int i, LZ4Compressor lZ4Compressor) {
        this.bytesRemainingInCompressionInputBuffer = 0;
        this.currentCompressionInputBufferPosition = 0;
        this.compressionInputBuffer = new byte[i];
        this.compressor = lZ4Compressor;
        this.underlyingOutputStream = outputStream;
        this.bytesRemainingInCompressionInputBuffer = i;
        this.currentCompressionInputBufferPosition = 0;
        this.compressionOutputBuffer = new byte[lZ4Compressor.maxCompressedLength(i)];
    }

    public void close() {
        flush();
        this.underlyingOutputStream.close();
    }

    public void flush() {
        if (this.currentCompressionInputBufferPosition > 0) {
            LZ4StreamHelper.writeLength(this.currentCompressionInputBufferPosition, this.underlyingOutputStream);
            int compress = this.compressor.compress(this.compressionInputBuffer, 0, this.currentCompressionInputBufferPosition, this.compressionOutputBuffer, 0, this.compressionOutputBuffer.length);
            LZ4StreamHelper.writeLength(compress, this.underlyingOutputStream);
            this.underlyingOutputStream.write(this.compressionOutputBuffer, 0, compress);
            this.bytesRemainingInCompressionInputBuffer = this.compressionInputBuffer.length;
            this.currentCompressionInputBufferPosition = 0;
        }
    }

    public void write(int i) {
        byte b = (byte) i;
        if (this.bytesRemainingInCompressionInputBuffer == 0) {
            flush();
        }
        this.compressionInputBuffer[this.currentCompressionInputBufferPosition] = b;
        this.bytesRemainingInCompressionInputBuffer--;
        this.currentCompressionInputBufferPosition++;
    }

    public void write(byte[] bArr, int i, int i2) {
        if (i2 <= this.bytesRemainingInCompressionInputBuffer) {
            System.arraycopy(bArr, i, this.compressionInputBuffer, this.currentCompressionInputBufferPosition, i2);
            this.currentCompressionInputBufferPosition += i2;
            this.bytesRemainingInCompressionInputBuffer -= i2;
            return;
        }
        while (i2 > 0) {
            int min = Math.min(this.bytesRemainingInCompressionInputBuffer, i2);
            System.arraycopy(bArr, i, this.compressionInputBuffer, this.currentCompressionInputBufferPosition, min);
            this.currentCompressionInputBufferPosition += min;
            this.bytesRemainingInCompressionInputBuffer -= min;
            flush();
            i2 -= min;
            i += min;
        }
    }
}
