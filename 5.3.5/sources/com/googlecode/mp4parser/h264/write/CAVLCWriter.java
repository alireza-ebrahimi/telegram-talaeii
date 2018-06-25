package com.googlecode.mp4parser.h264.write;

import com.googlecode.mp4parser.h264.Debug;
import java.io.IOException;
import java.io.OutputStream;

public class CAVLCWriter extends BitstreamWriter {
    public CAVLCWriter(OutputStream out) {
        super(out);
    }

    public void writeU(int value, int n, String string) throws IOException {
        Debug.print(new StringBuilder(String.valueOf(string)).append("\t").toString());
        writeNBit((long) value, n);
        Debug.println("\t" + value);
    }

    public void writeUE(int value) throws IOException {
        int bits = 0;
        int cumul = 0;
        int i = 0;
        while (i < 15) {
            if (value < (1 << i) + cumul) {
                bits = i;
                break;
            } else {
                cumul += 1 << i;
                i++;
            }
        }
        writeNBit(0, bits);
        write1Bit(1);
        writeNBit((long) (value - cumul), bits);
    }

    public void writeUE(int value, String string) throws IOException {
        Debug.print(new StringBuilder(String.valueOf(string)).append("\t").toString());
        writeUE(value);
        Debug.println("\t" + value);
    }

    public void writeSE(int value, String string) throws IOException {
        int i;
        int i2 = 1;
        Debug.print(new StringBuilder(String.valueOf(string)).append("\t").toString());
        int i3 = value << 1;
        if (value < 0) {
            i = -1;
        } else {
            i = 1;
        }
        i *= i3;
        if (value <= 0) {
            i2 = 0;
        }
        writeUE(i2 + i);
        Debug.println("\t" + value);
    }

    public void writeBool(boolean value, String string) throws IOException {
        Debug.print(new StringBuilder(String.valueOf(string)).append("\t").toString());
        write1Bit(value ? 1 : 0);
        Debug.println("\t" + value);
    }

    public void writeU(int i, int n) throws IOException {
        writeNBit((long) i, n);
    }

    public void writeNBit(long value, int n, String string) throws IOException {
        Debug.print(new StringBuilder(String.valueOf(string)).append("\t").toString());
        for (int i = 0; i < n; i++) {
            write1Bit(((int) (value >> ((n - i) - 1))) & 1);
        }
        Debug.println("\t" + value);
    }

    public void writeTrailingBits() throws IOException {
        write1Bit(1);
        writeRemainingZero();
        flush();
    }

    public void writeSliceTrailingBits() {
        throw new IllegalStateException("todo");
    }
}
