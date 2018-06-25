package com.googlecode.mp4parser;

import com.googlecode.mp4parser.util.CastUtils;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class DirectFileReadDataSource implements DataSource {
    private static final int TRANSFER_SIZE = 8192;
    private String filename;
    private RandomAccessFile raf;

    public DirectFileReadDataSource(File f) throws IOException {
        this.raf = new RandomAccessFile(f, "r");
        this.filename = f.getName();
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        int len = byteBuffer.remaining();
        int totalRead = 0;
        int bytesRead = 0;
        byte[] buf = new byte[8192];
        while (totalRead < len) {
            bytesRead = this.raf.read(buf, 0, Math.min(len - totalRead, 8192));
            if (bytesRead < 0) {
                break;
            }
            totalRead += bytesRead;
            byteBuffer.put(buf, 0, bytesRead);
        }
        return (bytesRead >= 0 || totalRead != 0) ? totalRead : -1;
    }

    public int readAllInOnce(ByteBuffer byteBuffer) throws IOException {
        byte[] buf = new byte[byteBuffer.remaining()];
        int read = this.raf.read(buf);
        byteBuffer.put(buf, 0, read);
        return read;
    }

    public long size() throws IOException {
        return this.raf.length();
    }

    public long position() throws IOException {
        return this.raf.getFilePointer();
    }

    public void position(long nuPos) throws IOException {
        this.raf.seek(nuPos);
    }

    public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
        return (long) target.write(map(position, count));
    }

    public ByteBuffer map(long startPosition, long size) throws IOException {
        this.raf.seek(startPosition);
        byte[] payload = new byte[CastUtils.l2i(size)];
        this.raf.readFully(payload);
        return ByteBuffer.wrap(payload);
    }

    public void close() throws IOException {
        this.raf.close();
    }

    public String toString() {
        return this.filename;
    }
}
