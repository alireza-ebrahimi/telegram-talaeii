package com.googlecode.mp4parser.h264.read;

import com.googlecode.mp4parser.h264.BTree;
import com.googlecode.mp4parser.h264.Debug;
import com.thin.downloadmanager.BuildConfig;
import java.io.IOException;
import java.io.InputStream;

public class CAVLCReader extends BitstreamReader {
    public CAVLCReader(InputStream is) throws IOException {
        super(is);
    }

    public long readNBit(int n, String message) throws IOException {
        long val = readNBit(n);
        trace(message, String.valueOf(val));
        return val;
    }

    private int readUE() throws IOException {
        int cnt = 0;
        while (read1Bit() == 0) {
            cnt++;
        }
        if (cnt <= 0) {
            return 0;
        }
        return (int) (((long) ((1 << cnt) - 1)) + readNBit(cnt));
    }

    public int readUE(String message) throws IOException {
        int res = readUE();
        trace(message, String.valueOf(res));
        return res;
    }

    public int readSE(String message) throws IOException {
        int val = readUE();
        val = ((val >> 1) + (val & 1)) * (((val & 1) << 1) - 1);
        trace(message, String.valueOf(val));
        return val;
    }

    public boolean readBool(String message) throws IOException {
        boolean res = read1Bit() != 0;
        trace(message, res ? BuildConfig.VERSION_NAME : "0");
        return res;
    }

    public int readU(int i, String string) throws IOException {
        return (int) readNBit(i, string);
    }

    public byte[] read(int payloadSize) throws IOException {
        byte[] result = new byte[payloadSize];
        for (int i = 0; i < payloadSize; i++) {
            result[i] = (byte) readByte();
        }
        return result;
    }

    public boolean readAE() {
        throw new UnsupportedOperationException("Stan");
    }

    public int readTE(int max) throws IOException {
        if (max > 1) {
            return readUE();
        }
        return (read1Bit() ^ -1) & 1;
    }

    public int readAEI() {
        throw new UnsupportedOperationException("Stan");
    }

    public int readME(String string) throws IOException {
        return readUE(string);
    }

    public Object readCE(BTree bt, String message) throws IOException {
        Object i;
        do {
            bt = bt.down(read1Bit());
            if (bt == null) {
                throw new RuntimeException("Illegal code");
            }
            i = bt.getValue();
        } while (i == null);
        trace(message, i.toString());
        return i;
    }

    public int readZeroBitCount(String message) throws IOException {
        int count = 0;
        while (read1Bit() == 0) {
            count++;
        }
        trace(message, String.valueOf(count));
        return count;
    }

    public void readTrailingBits() throws IOException {
        read1Bit();
        readRemainingByte();
    }

    private void trace(String message, String val) {
        int i;
        StringBuilder traceBuilder = new StringBuilder();
        String pos = String.valueOf(BitstreamReader.bitsRead - this.debugBits.length());
        int spaces = 8 - pos.length();
        traceBuilder.append("@" + pos);
        for (i = 0; i < spaces; i++) {
            traceBuilder.append(' ');
        }
        traceBuilder.append(message);
        spaces = (100 - traceBuilder.length()) - this.debugBits.length();
        for (i = 0; i < spaces; i++) {
            traceBuilder.append(' ');
        }
        traceBuilder.append(this.debugBits);
        traceBuilder.append(" (" + val + ")");
        this.debugBits.clear();
        Debug.println(traceBuilder.toString());
    }
}
