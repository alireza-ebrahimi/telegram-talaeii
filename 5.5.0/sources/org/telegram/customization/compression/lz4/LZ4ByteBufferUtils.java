package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.telegram.customization.compression.p160a.C2672a;

enum LZ4ByteBufferUtils {
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

    static {
        $assertionsDisabled = !LZ4ByteBufferUtils.class.desiredAssertionStatus();
        $VALUES = new LZ4ByteBufferUtils[0];
    }

    static int commonBytes(ByteBuffer byteBuffer, int i, int i2, int i3) {
        int i4 = 0;
        int i5 = i2;
        int i6 = i;
        while (i5 <= i3 - 8) {
            if (C2672a.m12569d(byteBuffer, i5) == C2672a.m12569d(byteBuffer, i6)) {
                i4 += 8;
                i6 += 8;
                i5 += 8;
            } else {
                if (byteBuffer.order() == ByteOrder.BIG_ENDIAN) {
                    i5 = Long.numberOfLeadingZeros(C2672a.m12569d(byteBuffer, i6) ^ C2672a.m12569d(byteBuffer, i5));
                } else {
                    i5 = Long.numberOfTrailingZeros(C2672a.m12569d(byteBuffer, i6) ^ C2672a.m12569d(byteBuffer, i5));
                }
                return (i5 >>> 3) + i4;
            }
        }
        int i7 = i4;
        i4 = i5;
        i5 = i7;
        while (i4 < i3) {
            int i8 = i6 + 1;
            byte b = C2672a.m12564b(byteBuffer, i6);
            i6 = i4 + 1;
            if (b != C2672a.m12564b(byteBuffer, i4)) {
                return i5;
            }
            i5++;
            i4 = i6;
            i6 = i8;
        }
        return i5;
    }

    static int commonBytesBackward(ByteBuffer byteBuffer, int i, int i2, int i3, int i4) {
        int i5 = 0;
        while (i > i3 && i2 > i4) {
            i--;
            i2--;
            if (byteBuffer.get(i) != byteBuffer.get(i2)) {
                break;
            }
            i5++;
        }
        return i5;
    }

    static void copyTo(Match match, Match match2) {
        match2.len = match.len;
        match2.start = match.start;
        match2.ref = match.ref;
    }

    static int encodeSequence(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, ByteBuffer byteBuffer2, int i5, int i6) {
        int i7 = i2 - i;
        int i8 = i5 + 1;
        if (((i8 + i7) + 8) + (i7 >>> 8) > i6) {
            throw new LZ4Exception("maxDestLen is too small");
        }
        int i9;
        if (i7 >= 15) {
            i9 = -16;
            i8 = writeLen(i7 - 15, byteBuffer2, i8);
        } else {
            i9 = i7 << 4;
        }
        wildArraycopy(byteBuffer, i, byteBuffer2, i8, i7);
        i8 += i7;
        i7 = i2 - i3;
        int i10 = i8 + 1;
        byteBuffer2.put(i8, (byte) i7);
        i8 = i10 + 1;
        byteBuffer2.put(i10, (byte) (i7 >>> 8));
        i7 = i4 - 4;
        if ((i8 + 6) + (i7 >>> 8) > i6) {
            throw new LZ4Exception("maxDestLen is too small");
        }
        if (i7 >= 15) {
            i9 |= 15;
            i8 = writeLen(i7 - 15, byteBuffer2, i8);
        } else {
            i9 |= i7;
        }
        byteBuffer2.put(i5, (byte) i9);
        return i8;
    }

    static int hash(ByteBuffer byteBuffer, int i) {
        return LZ4Utils.hash(C2672a.m12567c(byteBuffer, i));
    }

    static int hash64k(ByteBuffer byteBuffer, int i) {
        return LZ4Utils.hash64k(C2672a.m12567c(byteBuffer, i));
    }

    static int lastLiterals(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        if (((i3 + i2) + 1) + (((i2 + 255) - 15) / 255) > i4) {
            throw new LZ4Exception();
        }
        int i5;
        if (i2 >= 15) {
            i5 = i3 + 1;
            byteBuffer2.put(i3, (byte) -16);
            i5 = writeLen(i2 - 15, byteBuffer2, i5);
        } else {
            i5 = i3 + 1;
            byteBuffer2.put(i3, (byte) (i2 << 4));
        }
        safeArraycopy(byteBuffer, i, byteBuffer2, i5, i2);
        return i5 + i2;
    }

    static boolean readIntEquals(ByteBuffer byteBuffer, int i, int i2) {
        return byteBuffer.getInt(i) == byteBuffer.getInt(i2);
    }

    static void safeArraycopy(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            byteBuffer2.put(i2 + i4, byteBuffer.get(i + i4));
        }
    }

    static void safeIncrementalCopy(ByteBuffer byteBuffer, int i, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            byteBuffer.put(i2 + i4, byteBuffer.get(i + i4));
        }
    }

    static void wildArraycopy(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3) {
        if ($assertionsDisabled || byteBuffer.order().equals(byteBuffer2.order())) {
            int i4 = 0;
            while (i4 < i3) {
                try {
                    byteBuffer2.putLong(i2 + i4, byteBuffer.getLong(i + i4));
                    i4 += 8;
                } catch (IndexOutOfBoundsException e) {
                    throw new LZ4Exception("Malformed input at offset " + i);
                }
            }
            return;
        }
        throw new AssertionError();
    }

    static void wildIncrementalCopy(ByteBuffer byteBuffer, int i, int i2, int i3) {
        int i4 = 0;
        if (i2 - i < 4) {
            int i5;
            for (i5 = 0; i5 < 4; i5++) {
                C2672a.m12568c(byteBuffer, i2 + i5, C2672a.m12564b(byteBuffer, i + i5));
            }
            int i6 = i2 + 4;
            i5 = i + 4;
            if ($assertionsDisabled || (i6 >= i5 && i6 - i5 < 8)) {
                switch (i6 - i5) {
                    case 1:
                        i5 -= 3;
                        break;
                    case 2:
                        i5 -= 2;
                        break;
                    case 3:
                        i5 -= 3;
                        i4 = -1;
                        break;
                    case 5:
                        i4 = 1;
                        break;
                    case 6:
                        i4 = 2;
                        break;
                    case 7:
                        i4 = 3;
                        break;
                }
                C2672a.m12566b(byteBuffer, i6, C2672a.m12567c(byteBuffer, i5));
                i2 = i6 + 4;
                i = i5 - i4;
            } else {
                throw new AssertionError();
            }
        } else if (i2 - i < 8) {
            C2672a.m12563a(byteBuffer, i2, C2672a.m12569d(byteBuffer, i));
            i2 += i2 - i;
        }
        while (i2 < i3) {
            C2672a.m12563a(byteBuffer, i2, C2672a.m12569d(byteBuffer, i));
            i2 += 8;
            i += 8;
        }
    }

    static int writeLen(int i, ByteBuffer byteBuffer, int i2) {
        int i3;
        while (i >= 255) {
            i3 = i2 + 1;
            byteBuffer.put(i2, (byte) -1);
            i -= 255;
            i2 = i3;
        }
        i3 = i2 + 1;
        byteBuffer.put(i2, (byte) i);
        return i3;
    }
}
