package org.telegram.messenger.audioinfo.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PositionInputStream extends FilterInputStream {
    private long position;
    private long positionMark;

    public PositionInputStream(InputStream delegate) {
        this(delegate, 0);
    }

    public PositionInputStream(InputStream delegate, long position) {
        super(delegate);
        this.position = position;
    }

    public synchronized void mark(int readlimit) {
        this.positionMark = this.position;
        super.mark(readlimit);
    }

    public synchronized void reset() throws IOException {
        super.reset();
        this.position = this.positionMark;
    }

    public int read() throws IOException {
        int data = super.read();
        if (data >= 0) {
            this.position++;
        }
        return data;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        long p = this.position;
        int read = super.read(b, off, len);
        if (read > 0) {
            this.position = ((long) read) + p;
        }
        return read;
    }

    public final int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public long skip(long n) throws IOException {
        long p = this.position;
        long skipped = super.skip(n);
        this.position = p + skipped;
        return skipped;
    }

    public long getPosition() {
        return this.position;
    }
}
