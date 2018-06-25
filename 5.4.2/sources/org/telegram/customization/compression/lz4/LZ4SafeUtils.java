package org.telegram.customization.compression.lz4;

import org.telegram.customization.compression.p160a.C2675c;

enum LZ4SafeUtils {
    ;

    static class Match {
        int len;
        int ref;
        int start;

        Match() {
        }

        int end() {
            return this.start + this.len;
        }

        void fix(int i) {
            this.start += i;
            this.ref += i;
            this.len -= i;
        }
    }

    static int commonBytes(byte[] bArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i2 < i3) {
            int i5 = i + 1;
            int i6 = i2 + 1;
            if (bArr[i] != bArr[i2]) {
                break;
            }
            i4++;
            i2 = i6;
            i = i5;
        }
        return i4;
    }

    static int commonBytesBackward(byte[] bArr, int i, int i2, int i3, int i4) {
        int i5 = 0;
        while (i > i3 && i2 > i4) {
            i--;
            i2--;
            if (bArr[i] != bArr[i2]) {
                break;
            }
            i5++;
        }
        return i5;
    }

    static void copy8Bytes(byte[] bArr, int i, byte[] bArr2, int i2) {
        for (int i3 = 0; i3 < 8; i3++) {
            bArr2[i2 + i3] = bArr[i + i3];
        }
    }

    static void copyTo(Match match, Match match2) {
        match2.len = match.len;
        match2.start = match.start;
        match2.ref = match.ref;
    }

    static int encodeSequence(byte[] bArr, int i, int i2, int i3, int i4, byte[] bArr2, int i5, int i6) {
        int i7 = i2 - i;
        int i8 = i5 + 1;
        if (((i8 + i7) + 8) + (i7 >>> 8) > i6) {
            throw new LZ4Exception("maxDestLen is too small");
        }
        int i9;
        if (i7 >= 15) {
            i9 = -16;
            i8 = writeLen(i7 - 15, bArr2, i8);
        } else {
            i9 = i7 << 4;
        }
        wildArraycopy(bArr, i, bArr2, i8, i7);
        i8 += i7;
        i7 = i2 - i3;
        int i10 = i8 + 1;
        bArr2[i8] = (byte) i7;
        i8 = i10 + 1;
        bArr2[i10] = (byte) (i7 >>> 8);
        i7 = i4 - 4;
        if ((i8 + 6) + (i7 >>> 8) > i6) {
            throw new LZ4Exception("maxDestLen is too small");
        }
        if (i7 >= 15) {
            i9 |= 15;
            i8 = writeLen(i7 - 15, bArr2, i8);
        } else {
            i9 |= i7;
        }
        bArr2[i5] = (byte) i9;
        return i8;
    }

    static int hash(byte[] bArr, int i) {
        return LZ4Utils.hash(C2675c.m12589e(bArr, i));
    }

    static int hash64k(byte[] bArr, int i) {
        return LZ4Utils.hash64k(C2675c.m12589e(bArr, i));
    }

    static int lastLiterals(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        if (((i3 + i2) + 1) + (((i2 + 255) - 15) / 255) > i4) {
            throw new LZ4Exception();
        }
        int i5;
        if (i2 >= 15) {
            i5 = i3 + 1;
            bArr2[i3] = (byte) -16;
            i5 = writeLen(i2 - 15, bArr2, i5);
        } else {
            i5 = i3 + 1;
            bArr2[i3] = (byte) (i2 << 4);
        }
        System.arraycopy(bArr, i, bArr2, i5, i2);
        return i5 + i2;
    }

    static boolean readIntEquals(byte[] bArr, int i, int i2) {
        return bArr[i] == bArr[i2] && bArr[i + 1] == bArr[i2 + 1] && bArr[i + 2] == bArr[i2 + 2] && bArr[i + 3] == bArr[i2 + 3];
    }

    static void safeArraycopy(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        System.arraycopy(bArr, i, bArr2, i2, i3);
    }

    static void safeIncrementalCopy(byte[] bArr, int i, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            bArr[i2 + i4] = bArr[i + i4];
        }
    }

    static void wildArraycopy(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        int i4 = 0;
        while (i4 < i3) {
            try {
                copy8Bytes(bArr, i + i4, bArr2, i2 + i4);
                i4 += 8;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new LZ4Exception("Malformed input at offset " + i);
            }
        }
    }

    static void wildIncrementalCopy(byte[] bArr, int i, int i2, int i3) {
        do {
            copy8Bytes(bArr, i, bArr, i2);
            i += 8;
            i2 += 8;
        } while (i2 < i3);
    }

    static int writeLen(int i, byte[] bArr, int i2) {
        int i3;
        while (i >= 255) {
            i3 = i2 + 1;
            bArr[i2] = (byte) -1;
            i -= 255;
            i2 = i3;
        }
        i3 = i2 + 1;
        bArr[i2] = (byte) i;
        return i3;
    }
}
