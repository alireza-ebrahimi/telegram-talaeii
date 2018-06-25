package org.telegram.messenger.audioinfo.mp3;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class ID3v2DataInput {
    private final InputStream input;

    public ID3v2DataInput(InputStream in) {
        this.input = in;
    }

    public final void readFully(byte[] b, int off, int len) throws IOException {
        int total = 0;
        while (total < len) {
            int current = this.input.read(b, off + total, len - total);
            if (current > 0) {
                total += current;
            } else {
                throw new EOFException();
            }
        }
    }

    public byte[] readFully(int len) throws IOException {
        byte[] bytes = new byte[len];
        readFully(bytes, 0, len);
        return bytes;
    }

    public void skipFully(long len) throws IOException {
        long total = 0;
        while (total < len) {
            long current = this.input.skip(len - total);
            if (current > 0) {
                total += current;
            } else {
                throw new EOFException();
            }
        }
    }

    public byte readByte() throws IOException {
        int b = this.input.read();
        if (b >= 0) {
            return (byte) b;
        }
        throw new EOFException();
    }

    public int readInt() throws IOException {
        return ((((readByte() & 255) << 24) | ((readByte() & 255) << 16)) | ((readByte() & 255) << 8)) | (readByte() & 255);
    }

    public int readSyncsafeInt() throws IOException {
        return ((((readByte() & 127) << 21) | ((readByte() & 127) << 14)) | ((readByte() & 127) << 7)) | (readByte() & 127);
    }
}
