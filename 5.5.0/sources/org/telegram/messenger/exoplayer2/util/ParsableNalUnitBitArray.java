package org.telegram.messenger.exoplayer2.util;

public final class ParsableNalUnitBitArray {
    private int bitOffset;
    private int byteLimit;
    private int byteOffset;
    private byte[] data;

    public ParsableNalUnitBitArray(byte[] bArr, int i, int i2) {
        reset(bArr, i, i2);
    }

    private void assertValidOffset() {
        boolean z = this.byteOffset >= 0 && (this.byteOffset < this.byteLimit || (this.byteOffset == this.byteLimit && this.bitOffset == 0));
        Assertions.checkState(z);
    }

    private int readExpGolombCodeNum() {
        int i = 0;
        int i2 = 0;
        while (!readBit()) {
            i2++;
        }
        int i3 = (1 << i2) - 1;
        if (i2 > 0) {
            i = readBits(i2);
        }
        return i3 + i;
    }

    private boolean shouldSkipByte(int i) {
        return 2 <= i && i < this.byteLimit && this.data[i] == (byte) 3 && this.data[i - 2] == (byte) 0 && this.data[i - 1] == (byte) 0;
    }

    public boolean canReadBits(int i) {
        int i2 = this.byteOffset;
        int i3 = i / 8;
        int i4 = this.byteOffset + i3;
        i3 = (this.bitOffset + i) - (i3 * 8);
        if (i3 > 7) {
            i4++;
            i3 -= 8;
        }
        int i5 = i2 + 1;
        i2 = i4;
        i4 = i5;
        while (i4 <= i2 && i2 < this.byteLimit) {
            if (shouldSkipByte(i4)) {
                i2++;
                i4 += 2;
            }
            i4++;
        }
        return i2 < this.byteLimit || (i2 == this.byteLimit && i3 == 0);
    }

    public boolean canReadExpGolombCodedNum() {
        int i = this.byteOffset;
        int i2 = this.bitOffset;
        int i3 = 0;
        while (this.byteOffset < this.byteLimit && !readBit()) {
            i3++;
        }
        boolean z = this.byteOffset == this.byteLimit;
        this.byteOffset = i;
        this.bitOffset = i2;
        return !z && canReadBits((i3 * 2) + 1);
    }

    public boolean readBit() {
        boolean z = (this.data[this.byteOffset] & (128 >> this.bitOffset)) != 0;
        skipBit();
        return z;
    }

    public int readBits(int i) {
        int i2 = 2;
        this.bitOffset += i;
        int i3 = 0;
        while (this.bitOffset > 8) {
            this.bitOffset -= 8;
            i3 |= (this.data[this.byteOffset] & 255) << this.bitOffset;
            this.byteOffset = (shouldSkipByte(this.byteOffset + 1) ? 2 : 1) + this.byteOffset;
        }
        i3 = (i3 | ((this.data[this.byteOffset] & 255) >> (8 - this.bitOffset))) & (-1 >>> (32 - i));
        if (this.bitOffset == 8) {
            this.bitOffset = 0;
            int i4 = this.byteOffset;
            if (!shouldSkipByte(this.byteOffset + 1)) {
                i2 = 1;
            }
            this.byteOffset = i4 + i2;
        }
        assertValidOffset();
        return i3;
    }

    public int readSignedExpGolombCodedInt() {
        int readExpGolombCodeNum = readExpGolombCodeNum();
        return (readExpGolombCodeNum % 2 == 0 ? -1 : 1) * ((readExpGolombCodeNum + 1) / 2);
    }

    public int readUnsignedExpGolombCodedInt() {
        return readExpGolombCodeNum();
    }

    public void reset(byte[] bArr, int i, int i2) {
        this.data = bArr;
        this.byteOffset = i;
        this.byteLimit = i2;
        this.bitOffset = 0;
        assertValidOffset();
    }

    public void skipBit() {
        int i = this.bitOffset + 1;
        this.bitOffset = i;
        if (i == 8) {
            this.bitOffset = 0;
            this.byteOffset = (shouldSkipByte(this.byteOffset + 1) ? 2 : 1) + this.byteOffset;
        }
        assertValidOffset();
    }

    public void skipBits(int i) {
        int i2 = this.byteOffset;
        int i3 = i / 8;
        this.byteOffset += i3;
        this.bitOffset = (i - (i3 * 8)) + this.bitOffset;
        if (this.bitOffset > 7) {
            this.byteOffset++;
            this.bitOffset -= 8;
        }
        i2++;
        while (i2 <= this.byteOffset) {
            if (shouldSkipByte(i2)) {
                this.byteOffset++;
                i2 += 2;
            }
            i2++;
        }
        assertValidOffset();
    }
}
