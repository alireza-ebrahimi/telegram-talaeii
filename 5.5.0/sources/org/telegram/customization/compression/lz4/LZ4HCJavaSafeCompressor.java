package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.telegram.customization.compression.lz4.LZ4Utils.Match;
import org.telegram.customization.compression.p160a.C2672a;
import org.telegram.customization.compression.p160a.C2675c;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.tgnet.TLRPC;

final class LZ4HCJavaSafeCompressor extends LZ4Compressor {
    static final /* synthetic */ boolean $assertionsDisabled = (!LZ4HCJavaSafeCompressor.class.desiredAssertionStatus());
    public static final LZ4Compressor INSTANCE = new LZ4HCJavaSafeCompressor();
    final int compressionLevel;
    private final int maxAttempts;

    private class HashTable {
        static final /* synthetic */ boolean $assertionsDisabled = (!LZ4HCJavaSafeCompressor.class.desiredAssertionStatus());
        static final int MASK = 65535;
        private final int base;
        private final short[] chainTable;
        private final int[] hashTable = new int[TLRPC.MESSAGE_FLAG_EDITED];
        int nextToUpdate;

        HashTable(int i) {
            this.base = i;
            this.nextToUpdate = i;
            Arrays.fill(this.hashTable, -1);
            this.chainTable = new short[C3446C.DEFAULT_BUFFER_SEGMENT_SIZE];
        }

        private void addHash(int i, int i2) {
            int hashHC = LZ4Utils.hashHC(i);
            int i3 = i2 - this.hashTable[hashHC];
            if ($assertionsDisabled || i3 > 0) {
                if (i3 >= C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) {
                    i3 = MASK;
                }
                this.chainTable[MASK & i2] = (short) i3;
                this.hashTable[hashHC] = i2;
                return;
            }
            throw new AssertionError(i3);
        }

        private void addHash(ByteBuffer byteBuffer, int i) {
            addHash(C2672a.m12567c(byteBuffer, i), i);
        }

        private void addHash(byte[] bArr, int i) {
            addHash(C2675c.m12589e(bArr, i), i);
        }

        private int hashPointer(int i) {
            return this.hashTable[LZ4Utils.hashHC(i)];
        }

        private int hashPointer(ByteBuffer byteBuffer, int i) {
            return hashPointer(C2672a.m12567c(byteBuffer, i));
        }

        private int hashPointer(byte[] bArr, int i) {
            return hashPointer(C2675c.m12589e(bArr, i));
        }

        private int next(int i) {
            return i - (this.chainTable[i & MASK] & MASK);
        }

        void insert(int i, ByteBuffer byteBuffer) {
            while (this.nextToUpdate < i) {
                addHash(byteBuffer, this.nextToUpdate);
                this.nextToUpdate++;
            }
        }

        void insert(int i, byte[] bArr) {
            while (this.nextToUpdate < i) {
                addHash(bArr, this.nextToUpdate);
                this.nextToUpdate++;
            }
        }

        boolean insertAndFindBestMatch(ByteBuffer byteBuffer, int i, int i2, Match match) {
            int i3;
            int i4;
            match.start = i;
            match.len = 0;
            insert(i, byteBuffer);
            int hashPointer = hashPointer(byteBuffer, i);
            if (hashPointer < i - 4 || hashPointer > i || hashPointer < this.base) {
                i3 = hashPointer;
                i4 = 0;
                hashPointer = 0;
            } else {
                if (LZ4ByteBufferUtils.readIntEquals(byteBuffer, hashPointer, i)) {
                    i4 = i - hashPointer;
                    i3 = LZ4ByteBufferUtils.commonBytes(byteBuffer, hashPointer + 4, i + 4, i2) + 4;
                    match.len = i3;
                    match.ref = hashPointer;
                } else {
                    i3 = 0;
                    i4 = 0;
                }
                int next = next(hashPointer);
                hashPointer = i4;
                i4 = i3;
                i3 = next;
            }
            int i5 = i3;
            for (i3 = 0; i3 < LZ4HCJavaSafeCompressor.this.maxAttempts && i5 >= Math.max(this.base, (i - C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) + 1) && i5 <= i; i3++) {
                if (LZ4ByteBufferUtils.readIntEquals(byteBuffer, i5, i)) {
                    int commonBytes = LZ4ByteBufferUtils.commonBytes(byteBuffer, i5 + 4, i + 4, i2) + 4;
                    if (commonBytes > match.len) {
                        match.ref = i5;
                        match.len = commonBytes;
                    }
                }
                i5 = next(i5);
            }
            if (i4 != 0) {
                i4 = (i + i4) - 3;
                i3 = i;
                while (i3 < i4 - hashPointer) {
                    this.chainTable[i3 & MASK] = (short) hashPointer;
                    i3++;
                }
                do {
                    this.chainTable[i3 & MASK] = (short) hashPointer;
                    this.hashTable[LZ4Utils.hashHC(C2672a.m12567c(byteBuffer, i3))] = i3;
                    i3++;
                } while (i3 < i4);
                this.nextToUpdate = i4;
            }
            return match.len != 0;
        }

        boolean insertAndFindBestMatch(byte[] bArr, int i, int i2, Match match) {
            int i3;
            int i4;
            match.start = i;
            match.len = 0;
            insert(i, bArr);
            int hashPointer = hashPointer(bArr, i);
            if (hashPointer < i - 4 || hashPointer > i || hashPointer < this.base) {
                i3 = hashPointer;
                i4 = 0;
                hashPointer = 0;
            } else {
                if (LZ4SafeUtils.readIntEquals(bArr, hashPointer, i)) {
                    i4 = i - hashPointer;
                    i3 = LZ4SafeUtils.commonBytes(bArr, hashPointer + 4, i + 4, i2) + 4;
                    match.len = i3;
                    match.ref = hashPointer;
                } else {
                    i3 = 0;
                    i4 = 0;
                }
                int next = next(hashPointer);
                hashPointer = i4;
                i4 = i3;
                i3 = next;
            }
            int i5 = i3;
            for (i3 = 0; i3 < LZ4HCJavaSafeCompressor.this.maxAttempts && i5 >= Math.max(this.base, (i - C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) + 1) && i5 <= i; i3++) {
                if (LZ4SafeUtils.readIntEquals(bArr, i5, i)) {
                    int commonBytes = LZ4SafeUtils.commonBytes(bArr, i5 + 4, i + 4, i2) + 4;
                    if (commonBytes > match.len) {
                        match.ref = i5;
                        match.len = commonBytes;
                    }
                }
                i5 = next(i5);
            }
            if (i4 != 0) {
                i4 = (i + i4) - 3;
                i3 = i;
                while (i3 < i4 - hashPointer) {
                    this.chainTable[i3 & MASK] = (short) hashPointer;
                    i3++;
                }
                do {
                    this.chainTable[i3 & MASK] = (short) hashPointer;
                    this.hashTable[LZ4Utils.hashHC(C2675c.m12589e(bArr, i3))] = i3;
                    i3++;
                } while (i3 < i4);
                this.nextToUpdate = i4;
            }
            return match.len != 0;
        }

        boolean insertAndFindWiderMatch(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, Match match) {
            match.len = i4;
            insert(i, byteBuffer);
            int i5 = i - i2;
            int hashPointer = hashPointer(byteBuffer, i);
            for (i5 = 0; i5 < LZ4HCJavaSafeCompressor.this.maxAttempts && hashPointer >= Math.max(this.base, (i - C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) + 1) && hashPointer <= i; i5++) {
                if (LZ4ByteBufferUtils.readIntEquals(byteBuffer, hashPointer, i)) {
                    int commonBytes = LZ4ByteBufferUtils.commonBytes(byteBuffer, hashPointer + 4, i + 4, i3) + 4;
                    int commonBytesBackward = LZ4ByteBufferUtils.commonBytesBackward(byteBuffer, hashPointer, i, this.base, i2);
                    commonBytes += commonBytesBackward;
                    if (commonBytes > match.len) {
                        match.len = commonBytes;
                        match.ref = hashPointer - commonBytesBackward;
                        match.start = i - commonBytesBackward;
                    }
                }
                hashPointer = next(hashPointer);
            }
            return match.len > i4;
        }

        boolean insertAndFindWiderMatch(byte[] bArr, int i, int i2, int i3, int i4, Match match) {
            match.len = i4;
            insert(i, bArr);
            int i5 = i - i2;
            int hashPointer = hashPointer(bArr, i);
            for (i5 = 0; i5 < LZ4HCJavaSafeCompressor.this.maxAttempts && hashPointer >= Math.max(this.base, (i - C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) + 1) && hashPointer <= i; i5++) {
                if (LZ4SafeUtils.readIntEquals(bArr, hashPointer, i)) {
                    int commonBytes = LZ4SafeUtils.commonBytes(bArr, hashPointer + 4, i + 4, i3) + 4;
                    int commonBytesBackward = LZ4SafeUtils.commonBytesBackward(bArr, hashPointer, i, this.base, i2);
                    commonBytes += commonBytesBackward;
                    if (commonBytes > match.len) {
                        match.len = commonBytes;
                        match.ref = hashPointer - commonBytesBackward;
                        match.start = i - commonBytesBackward;
                    }
                }
                hashPointer = next(hashPointer);
            }
            return match.len > i4;
        }
    }

    LZ4HCJavaSafeCompressor() {
        this(9);
    }

    LZ4HCJavaSafeCompressor(int i) {
        this.maxAttempts = 1 << (i - 1);
        this.compressionLevel = i;
    }

    public int compress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        if (byteBuffer.hasArray() && byteBuffer2.hasArray()) {
            return compress(byteBuffer.array(), i + byteBuffer.arrayOffset(), i2, byteBuffer2.array(), i3 + byteBuffer2.arrayOffset(), i4);
        }
        ByteBuffer a = C2672a.m12560a(byteBuffer);
        ByteBuffer a2 = C2672a.m12560a(byteBuffer2);
        C2672a.m12562a(a, i, i2);
        C2672a.m12562a(a2, i3, i4);
        int i5 = i + i2;
        int i6 = i3 + i4;
        int i7 = i5 - 12;
        int i8 = i5 - 5;
        int i9 = i + 1;
        HashTable hashTable = new HashTable(i);
        Match match = new Match();
        Match match2 = new Match();
        Match match3 = new Match();
        Match match4 = new Match();
        int i10 = i3;
        int i11 = i9;
        i9 = i;
        while (i11 < i7) {
            if (hashTable.insertAndFindBestMatch(a, i11, i8, match2)) {
                LZ4Utils.copyTo(match2, match);
                int i12 = i9;
                while (true) {
                    if (!$assertionsDisabled && match2.start < i12) {
                        throw new AssertionError();
                    } else if (match2.end() >= i7 || !hashTable.insertAndFindWiderMatch(a, match2.end() - 2, match2.start + 1, i8, match2.len, match3)) {
                        i10 = LZ4ByteBufferUtils.encodeSequence(a, i12, match2.start, match2.ref, match2.len, a2, i10, i6);
                        i9 = match2.end();
                        i11 = i9;
                    } else {
                        if (match.start < match2.start && match3.start < match2.start + match.len) {
                            LZ4Utils.copyTo(match, match2);
                        }
                        if (!$assertionsDisabled && match3.start <= match2.start) {
                            throw new AssertionError();
                        } else if (match3.start - match2.start < 3) {
                            LZ4Utils.copyTo(match3, match2);
                        } else {
                            while (true) {
                                if (match3.start - match2.start < 18) {
                                    i11 = match2.len;
                                    if (i11 > 18) {
                                        i11 = 18;
                                    }
                                    if (match2.start + i11 > match3.end() - 4) {
                                        i11 = ((match3.start - match2.start) + match3.len) - 4;
                                    }
                                    i11 -= match3.start - match2.start;
                                    if (i11 > 0) {
                                        match3.fix(i11);
                                    }
                                }
                                if (match3.start + match3.len >= i7) {
                                    break;
                                }
                                if (!hashTable.insertAndFindWiderMatch(a, match3.end() - 3, match3.start, i8, match3.len, match4)) {
                                    break;
                                } else if (match4.start >= match2.end() + 3) {
                                    if (match3.start < match2.end()) {
                                        if (match3.start - match2.start < 15) {
                                            if (match2.len > 18) {
                                                match2.len = 18;
                                            }
                                            if (match2.end() > match3.end() - 4) {
                                                match2.len = (match3.end() - match2.start) - 4;
                                            }
                                            match3.fix(match2.end() - match3.start);
                                        } else {
                                            match2.len = match3.start - match2.start;
                                        }
                                    }
                                    i10 = LZ4ByteBufferUtils.encodeSequence(a, i12, match2.start, match2.ref, match2.len, a2, i10, i6);
                                    i12 = match2.end();
                                    LZ4Utils.copyTo(match3, match2);
                                    LZ4Utils.copyTo(match4, match3);
                                } else if (match4.start >= match2.end()) {
                                    break;
                                } else {
                                    LZ4Utils.copyTo(match4, match3);
                                }
                            }
                            if (match3.start < match2.end()) {
                                match3.fix(match2.end() - match3.start);
                                if (match3.len < 4) {
                                    LZ4Utils.copyTo(match4, match3);
                                }
                            }
                            i10 = LZ4ByteBufferUtils.encodeSequence(a, i12, match2.start, match2.ref, match2.len, a2, i10, i6);
                            i12 = match2.end();
                            LZ4Utils.copyTo(match4, match2);
                            LZ4Utils.copyTo(match3, match);
                        }
                    }
                }
                if (match3.start < match2.end()) {
                    match2.len = match3.start - match2.start;
                }
                i10 = LZ4ByteBufferUtils.encodeSequence(a, i12, match2.start, match2.ref, match2.len, a2, i10, i6);
                i10 = LZ4ByteBufferUtils.encodeSequence(a, match2.end(), match3.start, match3.ref, match3.len, a2, i10, i6);
                i9 = match3.end();
                i11 = i9;
            } else {
                i11++;
            }
        }
        return LZ4ByteBufferUtils.lastLiterals(a, i9, i5 - i9, a2, i10, i6) - i3;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compress(byte[] r23, int r24, int r25, byte[] r26, int r27, int r28) {
        /*
        r22 = this;
        org.telegram.customization.compression.p160a.C2675c.m12581a(r23, r24, r25);
        org.telegram.customization.compression.p160a.C2675c.m12581a(r26, r27, r28);
        r17 = r24 + r25;
        r16 = r27 + r28;
        r18 = r17 + -12;
        r6 = r17 + -5;
        r12 = r24 + 1;
        r2 = new org.telegram.customization.compression.lz4.LZ4HCJavaSafeCompressor$HashTable;
        r0 = r22;
        r1 = r24;
        r2.<init>(r1);
        r19 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r19.<init>();
        r20 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r20.<init>();
        r8 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r8.<init>();
        r21 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r21.<init>();
        r15 = r27;
        r3 = r12;
        r12 = r24;
    L_0x0032:
        r0 = r18;
        if (r3 >= r0) goto L_0x0247;
    L_0x0036:
        r0 = r23;
        r1 = r20;
        r4 = r2.insertAndFindBestMatch(r0, r3, r6, r1);
        if (r4 != 0) goto L_0x0043;
    L_0x0040:
        r3 = r3 + 1;
        goto L_0x0032;
    L_0x0043:
        r0 = r20;
        r1 = r19;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r1);
        r10 = r12;
    L_0x004b:
        r3 = $assertionsDisabled;
        if (r3 != 0) goto L_0x005b;
    L_0x004f:
        r0 = r20;
        r3 = r0.start;
        if (r3 >= r10) goto L_0x005b;
    L_0x0055:
        r2 = new java.lang.AssertionError;
        r2.<init>();
        throw r2;
    L_0x005b:
        r3 = r20.end();
        r0 = r18;
        if (r3 >= r0) goto L_0x007b;
    L_0x0063:
        r3 = r20.end();
        r4 = r3 + -2;
        r0 = r20;
        r3 = r0.start;
        r5 = r3 + 1;
        r0 = r20;
        r7 = r0.len;
        r3 = r23;
        r3 = r2.insertAndFindWiderMatch(r3, r4, r5, r6, r7, r8);
        if (r3 != 0) goto L_0x0095;
    L_0x007b:
        r0 = r20;
        r11 = r0.start;
        r0 = r20;
        r12 = r0.ref;
        r0 = r20;
        r13 = r0.len;
        r9 = r23;
        r14 = r26;
        r15 = org.telegram.customization.compression.lz4.LZ4SafeUtils.encodeSequence(r9, r10, r11, r12, r13, r14, r15, r16);
        r12 = r20.end();
        r3 = r12;
        goto L_0x0032;
    L_0x0095:
        r0 = r19;
        r3 = r0.start;
        r0 = r20;
        r4 = r0.start;
        if (r3 >= r4) goto L_0x00af;
    L_0x009f:
        r3 = r8.start;
        r0 = r20;
        r4 = r0.start;
        r0 = r19;
        r5 = r0.len;
        r4 = r4 + r5;
        if (r3 >= r4) goto L_0x00af;
    L_0x00ac:
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r19, r20);
    L_0x00af:
        r3 = $assertionsDisabled;
        if (r3 != 0) goto L_0x00c1;
    L_0x00b3:
        r3 = r8.start;
        r0 = r20;
        r4 = r0.start;
        if (r3 > r4) goto L_0x00c1;
    L_0x00bb:
        r2 = new java.lang.AssertionError;
        r2.<init>();
        throw r2;
    L_0x00c1:
        r3 = r8.start;
        r0 = r20;
        r4 = r0.start;
        r3 = r3 - r4;
        r4 = 3;
        if (r3 >= r4) goto L_0x0254;
    L_0x00cb:
        r0 = r20;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r8, r0);
        goto L_0x004b;
    L_0x00d2:
        r0 = r21;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r8);
    L_0x00d7:
        r5 = r8.start;
        r0 = r20;
        r7 = r0.start;
        r5 = r5 - r7;
        r7 = 18;
        if (r5 >= r7) goto L_0x0112;
    L_0x00e2:
        r0 = r20;
        r5 = r0.len;
        r7 = 18;
        if (r5 <= r7) goto L_0x00ec;
    L_0x00ea:
        r5 = 18;
    L_0x00ec:
        r0 = r20;
        r7 = r0.start;
        r7 = r7 + r5;
        r9 = r8.end();
        r9 = r9 + -4;
        if (r7 <= r9) goto L_0x0105;
    L_0x00f9:
        r5 = r8.start;
        r0 = r20;
        r7 = r0.start;
        r5 = r5 - r7;
        r7 = r8.len;
        r5 = r5 + r7;
        r5 = r5 + -4;
    L_0x0105:
        r7 = r8.start;
        r0 = r20;
        r9 = r0.start;
        r7 = r7 - r9;
        r5 = r5 - r7;
        if (r5 <= 0) goto L_0x0112;
    L_0x010f:
        r8.fix(r5);
    L_0x0112:
        r5 = r8.start;
        r7 = r8.len;
        r5 = r5 + r7;
        r0 = r18;
        if (r5 >= r0) goto L_0x0131;
    L_0x011b:
        r5 = r8.end();
        r11 = r5 + -3;
        r12 = r8.start;
        r14 = r8.len;
        r9 = r2;
        r10 = r23;
        r13 = r6;
        r15 = r21;
        r5 = r9.insertAndFindWiderMatch(r10, r11, r12, r13, r14, r15);
        if (r5 != 0) goto L_0x0173;
    L_0x0131:
        r5 = r8.start;
        r7 = r20.end();
        if (r5 >= r7) goto L_0x0144;
    L_0x0139:
        r5 = r8.start;
        r0 = r20;
        r7 = r0.start;
        r5 = r5 - r7;
        r0 = r20;
        r0.len = r5;
    L_0x0144:
        r0 = r20;
        r11 = r0.start;
        r0 = r20;
        r12 = r0.ref;
        r0 = r20;
        r13 = r0.len;
        r9 = r23;
        r10 = r3;
        r14 = r26;
        r15 = r4;
        r15 = org.telegram.customization.compression.lz4.LZ4SafeUtils.encodeSequence(r9, r10, r11, r12, r13, r14, r15, r16);
        r10 = r20.end();
        r11 = r8.start;
        r12 = r8.ref;
        r13 = r8.len;
        r9 = r23;
        r14 = r26;
        r15 = org.telegram.customization.compression.lz4.LZ4SafeUtils.encodeSequence(r9, r10, r11, r12, r13, r14, r15, r16);
        r12 = r8.end();
        r3 = r12;
        goto L_0x0032;
    L_0x0173:
        r0 = r21;
        r5 = r0.start;
        r7 = r20.end();
        r7 = r7 + 3;
        if (r5 >= r7) goto L_0x01cd;
    L_0x017f:
        r0 = r21;
        r5 = r0.start;
        r7 = r20.end();
        if (r5 < r7) goto L_0x00d2;
    L_0x0189:
        r5 = r8.start;
        r7 = r20.end();
        if (r5 >= r7) goto L_0x01a5;
    L_0x0191:
        r5 = r20.end();
        r7 = r8.start;
        r5 = r5 - r7;
        r8.fix(r5);
        r5 = r8.len;
        r7 = 4;
        if (r5 >= r7) goto L_0x01a5;
    L_0x01a0:
        r0 = r21;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r8);
    L_0x01a5:
        r0 = r20;
        r11 = r0.start;
        r0 = r20;
        r12 = r0.ref;
        r0 = r20;
        r13 = r0.len;
        r9 = r23;
        r10 = r3;
        r14 = r26;
        r15 = r4;
        r15 = org.telegram.customization.compression.lz4.LZ4SafeUtils.encodeSequence(r9, r10, r11, r12, r13, r14, r15, r16);
        r10 = r20.end();
        r0 = r21;
        r1 = r20;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r1);
        r0 = r19;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r8, r0);
        goto L_0x004b;
    L_0x01cd:
        r5 = r8.start;
        r7 = r20.end();
        if (r5 >= r7) goto L_0x0213;
    L_0x01d5:
        r5 = r8.start;
        r0 = r20;
        r7 = r0.start;
        r5 = r5 - r7;
        r7 = 15;
        if (r5 >= r7) goto L_0x023b;
    L_0x01e0:
        r0 = r20;
        r5 = r0.len;
        r7 = 18;
        if (r5 <= r7) goto L_0x01ee;
    L_0x01e8:
        r5 = 18;
        r0 = r20;
        r0.len = r5;
    L_0x01ee:
        r5 = r20.end();
        r7 = r8.end();
        r7 = r7 + -4;
        if (r5 <= r7) goto L_0x0209;
    L_0x01fa:
        r5 = r8.end();
        r0 = r20;
        r7 = r0.start;
        r5 = r5 - r7;
        r5 = r5 + -4;
        r0 = r20;
        r0.len = r5;
    L_0x0209:
        r5 = r20.end();
        r7 = r8.start;
        r5 = r5 - r7;
        r8.fix(r5);
    L_0x0213:
        r0 = r20;
        r11 = r0.start;
        r0 = r20;
        r12 = r0.ref;
        r0 = r20;
        r13 = r0.len;
        r9 = r23;
        r10 = r3;
        r14 = r26;
        r15 = r4;
        r15 = org.telegram.customization.compression.lz4.LZ4SafeUtils.encodeSequence(r9, r10, r11, r12, r13, r14, r15, r16);
        r10 = r20.end();
        r0 = r20;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r8, r0);
        r0 = r21;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r8);
        r3 = r10;
        r4 = r15;
        goto L_0x00d7;
    L_0x023b:
        r5 = r8.start;
        r0 = r20;
        r7 = r0.start;
        r5 = r5 - r7;
        r0 = r20;
        r0.len = r5;
        goto L_0x0213;
    L_0x0247:
        r13 = r17 - r12;
        r11 = r23;
        r14 = r26;
        r2 = org.telegram.customization.compression.lz4.LZ4SafeUtils.lastLiterals(r11, r12, r13, r14, r15, r16);
        r2 = r2 - r27;
        return r2;
    L_0x0254:
        r3 = r10;
        r4 = r15;
        goto L_0x00d7;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.customization.compression.lz4.LZ4HCJavaSafeCompressor.compress(byte[], int, int, byte[], int, int):int");
    }
}
