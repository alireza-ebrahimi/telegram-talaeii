package com.googlecode.mp4parser.h264.write;

import com.googlecode.mp4parser.h264.Debug;
import java.io.IOException;
import java.io.OutputStream;

public class BitstreamWriter {
    private int curBit;
    private int[] curByte = new int[8];
    private final OutputStream os;

    public BitstreamWriter(OutputStream out) {
        this.os = out;
    }

    public void flush() throws IOException {
        for (int i = this.curBit; i < 8; i++) {
            this.curByte[i] = 0;
        }
        this.curBit = 0;
        writeCurByte();
    }

    private void writeCurByte() throws IOException {
        this.os.write((((((((this.curByte[0] << 7) | (this.curByte[1] << 6)) | (this.curByte[2] << 5)) | (this.curByte[3] << 4)) | (this.curByte[4] << 3)) | (this.curByte[5] << 2)) | (this.curByte[6] << 1)) | this.curByte[7]);
    }

    public void write1Bit(int value) throws IOException {
        Debug.print(value);
        if (this.curBit == 8) {
            this.curBit = 0;
            writeCurByte();
        }
        int[] iArr = this.curByte;
        int i = this.curBit;
        this.curBit = i + 1;
        iArr[i] = value;
    }

    public void writeNBit(long value, int n) throws IOException {
        for (int i = 0; i < n; i++) {
            write1Bit(((int) (value >> ((n - i) - 1))) & 1);
        }
    }

    public void writeRemainingZero() throws IOException {
        writeNBit(0, 8 - this.curBit);
    }

    public void writeByte(int b) throws IOException {
        this.os.write(b);
    }
}
