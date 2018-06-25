package com.googlecode.mp4parser.h264.read;

import com.googlecode.mp4parser.h264.CharCache;
import java.io.IOException;
import java.io.InputStream;

public class BitstreamReader {
    protected static int bitsRead;
    private int curByte;
    protected CharCache debugBits = new CharCache(50);
    private InputStream is;
    int nBit;
    private int nextByte;

    public BitstreamReader(InputStream is) throws IOException {
        this.is = is;
        this.curByte = is.read();
        this.nextByte = is.read();
    }

    public boolean readBool() throws IOException {
        return read1Bit() == 1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read1Bit() throws java.io.IOException {
        /*
        r3 = this;
        r0 = -1;
        r1 = r3.nBit;
        r2 = 8;
        if (r1 != r2) goto L_0x000f;
    L_0x0007:
        r3.advance();
        r1 = r3.curByte;
        if (r1 != r0) goto L_0x000f;
    L_0x000e:
        return r0;
    L_0x000f:
        r1 = r3.curByte;
        r2 = r3.nBit;
        r2 = 7 - r2;
        r1 = r1 >> r2;
        r0 = r1 & 1;
        r1 = r3.nBit;
        r1 = r1 + 1;
        r3.nBit = r1;
        r2 = r3.debugBits;
        if (r0 != 0) goto L_0x002e;
    L_0x0022:
        r1 = 48;
    L_0x0024:
        r2.append(r1);
        r1 = bitsRead;
        r1 = r1 + 1;
        bitsRead = r1;
        goto L_0x000e;
    L_0x002e:
        r1 = 49;
        goto L_0x0024;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.googlecode.mp4parser.h264.read.BitstreamReader.read1Bit():int");
    }

    public long readNBit(int n) throws IOException {
        if (n > 64) {
            throw new IllegalArgumentException("Can not readByte more then 64 bit");
        }
        long val = 0;
        for (int i = 0; i < n; i++) {
            val = (val << 1) | ((long) read1Bit());
        }
        return val;
    }

    private void advance() throws IOException {
        this.curByte = this.nextByte;
        this.nextByte = this.is.read();
        this.nBit = 0;
    }

    public int readByte() throws IOException {
        if (this.nBit > 0) {
            advance();
        }
        int res = this.curByte;
        advance();
        return res;
    }

    public boolean moreRBSPData() throws IOException {
        if (this.nBit == 8) {
            advance();
        }
        int tail = 1 << ((8 - this.nBit) - 1);
        boolean hasTail;
        if ((this.curByte & ((tail << 1) - 1)) == tail) {
            hasTail = true;
        } else {
            hasTail = false;
        }
        if (this.curByte == -1 || (this.nextByte == -1 && hasTail)) {
            return false;
        }
        return true;
    }

    public long getBitPosition() {
        return (long) ((bitsRead * 8) + (this.nBit % 8));
    }

    public long readRemainingByte() throws IOException {
        return readNBit(8 - this.nBit);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int peakNextBits(int r9) throws java.io.IOException {
        /*
        r8 = this;
        r4 = -1;
        r7 = 8;
        if (r9 <= r7) goto L_0x000e;
    L_0x0005:
        r5 = new java.lang.IllegalArgumentException;
        r6 = "N should be less then 8";
        r5.<init>(r6);
        throw r5;
    L_0x000e:
        r5 = r8.nBit;
        if (r5 != r7) goto L_0x001a;
    L_0x0012:
        r8.advance();
        r5 = r8.curByte;
        if (r5 != r4) goto L_0x001a;
    L_0x0019:
        return r4;
    L_0x001a:
        r5 = r8.nBit;
        r5 = 16 - r5;
        r0 = new int[r5];
        r1 = 0;
        r3 = r8.nBit;
        r2 = r1;
    L_0x0024:
        if (r3 < r7) goto L_0x0035;
    L_0x0026:
        r3 = 0;
    L_0x0027:
        if (r3 < r7) goto L_0x0044;
    L_0x0029:
        r4 = 0;
        r3 = 0;
    L_0x002b:
        if (r3 >= r9) goto L_0x0019;
    L_0x002d:
        r4 = r4 << 1;
        r5 = r0[r3];
        r4 = r4 | r5;
        r3 = r3 + 1;
        goto L_0x002b;
    L_0x0035:
        r1 = r2 + 1;
        r5 = r8.curByte;
        r6 = 7 - r3;
        r5 = r5 >> r6;
        r5 = r5 & 1;
        r0[r2] = r5;
        r3 = r3 + 1;
        r2 = r1;
        goto L_0x0024;
    L_0x0044:
        r1 = r2 + 1;
        r5 = r8.nextByte;
        r6 = 7 - r3;
        r5 = r5 >> r6;
        r5 = r5 & 1;
        r0[r2] = r5;
        r3 = r3 + 1;
        r2 = r1;
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.googlecode.mp4parser.h264.read.BitstreamReader.peakNextBits(int):int");
    }

    public boolean isByteAligned() {
        return this.nBit % 8 == 0;
    }

    public void close() throws IOException {
    }

    public int getCurBit() {
        return this.nBit;
    }
}
