package com.googlecode.mp4parser.boxes.mp4.objectdescriptors;

import java.nio.ByteBuffer;

public class BitReaderBuffer {
    private ByteBuffer buffer;
    int initialPos;
    int position;

    public BitReaderBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
        this.initialPos = buffer.position();
    }

    public boolean readBool() {
        return readBits(1) == 1;
    }

    public int readBits(int i) {
        int rc;
        byte b = this.buffer.get(this.initialPos + (this.position / 8));
        if (b < (byte) 0) {
            int v = b + 256;
        } else {
            byte v2 = b;
        }
        int left = 8 - (this.position % 8);
        if (i <= left) {
            rc = ((v << (this.position % 8)) & 255) >> ((this.position % 8) + (left - i));
            this.position += i;
        } else {
            int then = i - left;
            rc = (readBits(left) << then) + readBits(then);
        }
        this.buffer.position(this.initialPos + ((int) Math.ceil(((double) this.position) / 8.0d)));
        return rc;
    }

    public int getPosition() {
        return this.position;
    }

    public int byteSync() {
        int left = 8 - (this.position % 8);
        if (left == 8) {
            left = 0;
        }
        readBits(left);
        return left;
    }

    public int remainingBits() {
        return (this.buffer.limit() * 8) - this.position;
    }
}
