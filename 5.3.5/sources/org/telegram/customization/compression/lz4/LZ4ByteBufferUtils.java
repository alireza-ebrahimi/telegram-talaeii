package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.telegram.customization.compression.util.ByteBufferUtils;

enum LZ4ByteBufferUtils {
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

    static {
        $assertionsDisabled = !LZ4ByteBufferUtils.class.desiredAssertionStatus();
        $VALUES = new LZ4ByteBufferUtils[0];
    }

    static int hash(ByteBuffer buf, int i) {
        return LZ4Utils.hash(ByteBufferUtils.readInt(buf, i));
    }

    static int hash64k(ByteBuffer buf, int i) {
        return LZ4Utils.hash64k(ByteBufferUtils.readInt(buf, i));
    }

    static boolean readIntEquals(ByteBuffer buf, int i, int j) {
        return buf.getInt(i) == buf.getInt(j);
    }

    static void safeIncrementalCopy(ByteBuffer dest, int matchOff, int dOff, int matchLen) {
        for (int i = 0; i < matchLen; i++) {
            dest.put(dOff + i, dest.get(matchOff + i));
        }
    }

    static void wildIncrementalCopy(ByteBuffer dest, int matchOff, int dOff, int matchCopyEnd) {
        if (dOff - matchOff < 4) {
            for (int i = 0; i < 4; i++) {
                ByteBufferUtils.writeByte(dest, dOff + i, ByteBufferUtils.readByte(dest, matchOff + i));
            }
            dOff += 4;
            matchOff += 4;
            int dec = 0;
            if ($assertionsDisabled || (dOff >= matchOff && dOff - matchOff < 8)) {
                switch (dOff - matchOff) {
                    case 1:
                        matchOff -= 3;
                        break;
                    case 2:
                        matchOff -= 2;
                        break;
                    case 3:
                        matchOff -= 3;
                        dec = -1;
                        break;
                    case 5:
                        dec = 1;
                        break;
                    case 6:
                        dec = 2;
                        break;
                    case 7:
                        dec = 3;
                        break;
                }
                ByteBufferUtils.writeInt(dest, dOff, ByteBufferUtils.readInt(dest, matchOff));
                dOff += 4;
                matchOff -= dec;
            } else {
                throw new AssertionError();
            }
        } else if (dOff - matchOff < 8) {
            ByteBufferUtils.writeLong(dest, dOff, ByteBufferUtils.readLong(dest, matchOff));
            dOff += dOff - matchOff;
        }
        while (dOff < matchCopyEnd) {
            ByteBufferUtils.writeLong(dest, dOff, ByteBufferUtils.readLong(dest, matchOff));
            dOff += 8;
            matchOff += 8;
        }
    }

    static int commonBytes(ByteBuffer src, int ref, int sOff, int srcLimit) {
        int matchLen = 0;
        while (sOff <= srcLimit - 8) {
            if (ByteBufferUtils.readLong(src, sOff) == ByteBufferUtils.readLong(src, ref)) {
                matchLen += 8;
                ref += 8;
                sOff += 8;
            } else {
                int zeroBits;
                if (src.order() == ByteOrder.BIG_ENDIAN) {
                    zeroBits = Long.numberOfLeadingZeros(ByteBufferUtils.readLong(src, sOff) ^ ByteBufferUtils.readLong(src, ref));
                } else {
                    zeroBits = Long.numberOfTrailingZeros(ByteBufferUtils.readLong(src, sOff) ^ ByteBufferUtils.readLong(src, ref));
                }
                return (zeroBits >>> 3) + matchLen;
            }
        }
        int sOff2 = sOff;
        int ref2 = ref;
        while (sOff2 < srcLimit) {
            ref = ref2 + 1;
            sOff = sOff2 + 1;
            if (ByteBufferUtils.readByte(src, ref2) != ByteBufferUtils.readByte(src, sOff2)) {
                break;
            }
            matchLen++;
            sOff2 = sOff;
            ref2 = ref;
        }
        sOff = sOff2;
        ref = ref2;
        return matchLen;
    }

    static int commonBytesBackward(ByteBuffer b, int o1, int o2, int l1, int l2) {
        int count = 0;
        while (o1 > l1 && o2 > l2) {
            o1--;
            o2--;
            if (b.get(o1) != b.get(o2)) {
                break;
            }
            count++;
        }
        return count;
    }

    static void safeArraycopy(ByteBuffer src, int sOff, ByteBuffer dest, int dOff, int len) {
        for (int i = 0; i < len; i++) {
            dest.put(dOff + i, src.get(sOff + i));
        }
    }

    static void wildArraycopy(ByteBuffer src, int sOff, ByteBuffer dest, int dOff, int len) {
        if ($assertionsDisabled || src.order().equals(dest.order())) {
            int i = 0;
            while (i < len) {
                try {
                    dest.putLong(dOff + i, src.getLong(sOff + i));
                    i += 8;
                } catch (IndexOutOfBoundsException e) {
                    throw new LZ4Exception("Malformed input at offset " + sOff);
                }
            }
            return;
        }
        throw new AssertionError();
    }

    static int encodeSequence(ByteBuffer src, int anchor, int matchOff, int matchRef, int matchLen, ByteBuffer dest, int dOff, int destEnd) {
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
        dest.put(dOff, (byte) matchDec);
        dOff = dOff2 + 1;
        dest.put(dOff2, (byte) (matchDec >>> 8));
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
        dest.put(tokenOff, (byte) token);
        return dOff;
    }

    static int lastLiterals(ByteBuffer src, int sOff, int srcLen, ByteBuffer dest, int dOff, int destEnd) {
        int runLen = srcLen;
        if (((dOff + runLen) + 1) + (((runLen + 255) - 15) / 255) > destEnd) {
            throw new LZ4Exception();
        }
        int dOff2;
        if (runLen >= 15) {
            dOff2 = dOff + 1;
            dest.put(dOff, (byte) -16);
            dOff = writeLen(runLen - 15, dest, dOff2);
        } else {
            dOff2 = dOff + 1;
            dest.put(dOff, (byte) (runLen << 4));
            dOff = dOff2;
        }
        safeArraycopy(src, sOff, dest, dOff, runLen);
        return dOff + runLen;
    }

    static int writeLen(int len, ByteBuffer dest, int dOff) {
        int dOff2 = dOff;
        while (len >= 255) {
            dOff = dOff2 + 1;
            dest.put(dOff2, (byte) -1);
            len -= 255;
            dOff2 = dOff;
        }
        dOff = dOff2 + 1;
        dest.put(dOff2, (byte) len);
        return dOff;
    }

    static void copyTo(Match m1, Match m2) {
        m2.len = m1.len;
        m2.start = m1.start;
        m2.ref = m1.ref;
    }
}
