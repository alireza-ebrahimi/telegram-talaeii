package org.telegram.messenger.audioinfo.util;

import java.io.InputStream;

public class RangeInputStream extends PositionInputStream {
    private final long endPosition;

    public RangeInputStream(InputStream inputStream, long j, long j2) {
        super(inputStream, j);
        this.endPosition = j + j2;
    }

    public long getRemainingLength() {
        return this.endPosition - getPosition();
    }

    public int read() {
        return getPosition() == this.endPosition ? -1 : super.read();
    }

    public int read(byte[] bArr, int i, int i2) {
        if (getPosition() + ((long) i2) > this.endPosition) {
            i2 = (int) (this.endPosition - getPosition());
            if (i2 == 0) {
                return -1;
            }
        }
        return super.read(bArr, i, i2);
    }

    public long skip(long j) {
        if (getPosition() + j > this.endPosition) {
            j = (long) ((int) (this.endPosition - getPosition()));
        }
        return super.skip(j);
    }
}
