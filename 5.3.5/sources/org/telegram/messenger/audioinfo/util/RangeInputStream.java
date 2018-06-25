package org.telegram.messenger.audioinfo.util;

import java.io.IOException;
import java.io.InputStream;

public class RangeInputStream extends PositionInputStream {
    private final long endPosition;

    public RangeInputStream(InputStream delegate, long position, long length) throws IOException {
        super(delegate, position);
        this.endPosition = position + length;
    }

    public long getRemainingLength() {
        return this.endPosition - getPosition();
    }

    public int read() throws IOException {
        if (getPosition() == this.endPosition) {
            return -1;
        }
        return super.read();
    }

    public int read(byte[] b, int off, int len) throws IOException {
        if (getPosition() + ((long) len) > this.endPosition) {
            len = (int) (this.endPosition - getPosition());
            if (len == 0) {
                return -1;
            }
        }
        return super.read(b, off, len);
    }

    public long skip(long n) throws IOException {
        if (getPosition() + n > this.endPosition) {
            n = (long) ((int) (this.endPosition - getPosition()));
        }
        return super.skip(n);
    }
}
