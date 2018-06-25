package org.telegram.customization.compression.lz4;

import org.telegram.customization.compression.util.SafeUtils;

enum LZ4SafeUtils {
    ;

    static class Match {
        int len;
        int ref;
        int start;

        Match() {
        }

        void fix(int correction) {
            this.start += correction;
            this.ref += correction;
            this.len -= correction;
        }

        int end() {
            return this.start + this.len;
        }
    }

    static int hash(byte[] buf, int i) {
        return LZ4Utils.hash(SafeUtils.readInt(buf, i));
    }

    static int hash64k(byte[] buf, int i) {
        return LZ4Utils.hash64k(SafeUtils.readInt(buf, i));
    }

    static boolean readIntEquals(byte[] buf, int i, int j) {
        return buf[i] == buf[j] && buf[i + 1] == buf[j + 1] && buf[i + 2] == buf[j + 2] && buf[i + 3] == buf[j + 3];
    }

    static void safeIncrementalCopy(byte[] dest, int matchOff, int dOff, int matchLen) {
        for (int i = 0; i < matchLen; i++) {
            dest[dOff + i] = dest[matchOff + i];
        }
    }

    static void wildIncrementalCopy(byte[] dest, int matchOff, int dOff, int matchCopyEnd) {
        do {
            copy8Bytes(dest, matchOff, dest, dOff);
            matchOff += 8;
            dOff += 8;
        } while (dOff < matchCopyEnd);
    }

    static void copy8Bytes(byte[] src, int sOff, byte[] dest, int dOff) {
        for (int i = 0; i < 8; i++) {
            dest[dOff + i] = src[sOff + i];
        }
    }

    static int commonBytes(byte[] b, int o1, int o2, int limit) {
        int count = 0;
        int o22 = o2;
        int o12 = o1;
        while (o22 < limit) {
            o1 = o12 + 1;
            o2 = o22 + 1;
            if (b[o12] != b[o22]) {
                break;
            }
            count++;
            o22 = o2;
            o12 = o1;
        }
        o2 = o22;
        o1 = o12;
        return count;
    }

    static int commonBytesBackward(byte[] b, int o1, int o2, int l1, int l2) {
        int count = 0;
        while (o1 > l1 && o2 > l2) {
            o1--;
            o2--;
            if (b[o1] != b[o2]) {
                break;
            }
            count++;
        }
        return count;
    }

    static void safeArraycopy(byte[] src, int sOff, byte[] dest, int dOff, int len) {
        System.arraycopy(src, sOff, dest, dOff, len);
    }

    static void wildArraycopy(byte[] src, int sOff, byte[] dest, int dOff, int len) {
        int i = 0;
        while (i < len) {
            try {
                copy8Bytes(src, sOff + i, dest, dOff + i);
                i += 8;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new LZ4Exception("Malformed input at offset " + sOff);
            }
        }
    }

    static int encodeSequence(byte[] src, int anchor, int matchOff, int matchRef, int matchLen, byte[] dest, int dOff, int destEnd) {
        int runLen = matchOff - anchor;
        int dOff2 = dOff + 1;
        int tokenOff = dOff;
        if (((dOff2 + runLen) + 8) + (runLen >>> 8) > destEnd) {
            throw new LZ4Exception("maxDestLen is too small");
        }
        int token;
        if (runLen >= 15) {
            token = -16;
            dOff = writeLen(runLen - 15, dest, dOff2);
        } else {
            token = runLen << 4;
            dOff = dOff2;
        }
        wildArraycopy(src, anchor, dest, dOff, runLen);
        dOff += runLen;
        int matchDec = matchOff - matchRef;
        dOff2 = dOff + 1;
        dest[dOff] = (byte) matchDec;
        dOff = dOff2 + 1;
        dest[dOff2] = (byte) (matchDec >>> 8);
        matchLen -= 4;
        if ((dOff + 6) + (matchLen >>> 8) > destEnd) {
            throw new LZ4Exception("maxDestLen is too small");
        }
        if (matchLen >= 15) {
            token |= 15;
            dOff = writeLen(matchLen - 15, dest, dOff);
        } else {
            token |= matchLen;
        }
        dest[tokenOff] = (byte) token;
        return dOff;
    }

    static int lastLiterals(byte[] src, int sOff, int srcLen, byte[] dest, int dOff, int destEnd) {
        int runLen = srcLen;
        if (((dOff + runLen) + 1) + (((runLen + 255) - 15) / 255) > destEnd) {
            throw new LZ4Exception();
        }
        int dOff2;
        if (runLen >= 15) {
            dOff2 = dOff + 1;
            dest[dOff] = (byte) -16;
            dOff = writeLen(runLen - 15, dest, dOff2);
        } else {
            dOff2 = dOff + 1;
            dest[dOff] = (byte) (runLen << 4);
            dOff = dOff2;
        }
        System.arraycopy(src, sOff, dest, dOff, runLen);
        return dOff + runLen;
    }

    static int writeLen(int len, byte[] dest, int dOff) {
        int dOff2 = dOff;
        while (len >= 255) {
            dOff = dOff2 + 1;
            dest[dOff2] = (byte) -1;
            len -= 255;
            dOff2 = dOff;
        }
        dOff = dOff2 + 1;
        dest[dOff2] = (byte) len;
        return dOff;
    }

    static void copyTo(Match m1, Match m2) {
        m2.len = m1.len;
        m2.start = m1.start;
        m2.ref = m1.ref;
    }
}
