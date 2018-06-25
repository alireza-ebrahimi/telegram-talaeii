package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.telegram.customization.compression.lz4.LZ4Utils.Match;
import org.telegram.customization.compression.util.ByteBufferUtils;
import org.telegram.customization.compression.util.SafeUtils;

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
        private final int[] hashTable = new int[32768];
        int nextToUpdate;

        HashTable(int base) {
            this.base = base;
            this.nextToUpdate = base;
            Arrays.fill(this.hashTable, -1);
            this.chainTable = new short[65536];
        }

        private int hashPointer(byte[] bytes, int off) {
            return hashPointer(SafeUtils.readInt(bytes, off));
        }

        private int hashPointer(ByteBuffer bytes, int off) {
            return hashPointer(ByteBufferUtils.readInt(bytes, off));
        }

        private int hashPointer(int v) {
            return this.hashTable[LZ4Utils.hashHC(v)];
        }

        private int next(int off) {
            return off - (this.chainTable[off & 65535] & 65535);
        }

        private void addHash(byte[] bytes, int off) {
            addHash(SafeUtils.readInt(bytes, off), off);
        }

        private void addHash(ByteBuffer bytes, int off) {
            addHash(ByteBufferUtils.readInt(bytes, off), off);
        }

        private void addHash(int v, int off) {
            int h = LZ4Utils.hashHC(v);
            int delta = off - this.hashTable[h];
            if ($assertionsDisabled || delta > 0) {
                if (delta >= 65536) {
                    delta = 65535;
                }
                this.chainTable[65535 & off] = (short) delta;
                this.hashTable[h] = off;
                return;
            }
            throw new AssertionError(delta);
        }

        void insert(int off, byte[] bytes) {
            while (this.nextToUpdate < off) {
                addHash(bytes, this.nextToUpdate);
                this.nextToUpdate++;
            }
        }

        void insert(int off, ByteBuffer bytes) {
            while (this.nextToUpdate < off) {
                addHash(bytes, this.nextToUpdate);
                this.nextToUpdate++;
            }
        }

        boolean insertAndFindBestMatch(byte[] buf, int off, int matchLimit, Match match) {
            match.start = off;
            match.len = 0;
            int delta = 0;
            int repl = 0;
            insert(off, buf);
            int ref = hashPointer(buf, off);
            if (ref >= off - 4 && ref <= off && ref >= this.base) {
                if (LZ4SafeUtils.readIntEquals(buf, ref, off)) {
                    delta = off - ref;
                    repl = LZ4SafeUtils.commonBytes(buf, ref + 4, off + 4, matchLimit) + 4;
                    match.len = repl;
                    match.ref = ref;
                }
                ref = next(ref);
            }
            for (int i = 0; i < LZ4HCJavaSafeCompressor.this.maxAttempts && ref >= Math.max(this.base, (off - 65536) + 1) && ref <= off; i++) {
                if (LZ4SafeUtils.readIntEquals(buf, ref, off)) {
                    int matchLen = LZ4SafeUtils.commonBytes(buf, ref + 4, off + 4, matchLimit) + 4;
                    if (matchLen > match.len) {
                        match.ref = ref;
                        match.len = matchLen;
                    }
                }
                ref = next(ref);
            }
            if (repl != 0) {
                int ptr = off;
                int end = (off + repl) - 3;
                while (ptr < end - delta) {
                    this.chainTable[65535 & ptr] = (short) delta;
                    ptr++;
                }
                do {
                    this.chainTable[65535 & ptr] = (short) delta;
                    this.hashTable[LZ4Utils.hashHC(SafeUtils.readInt(buf, ptr))] = ptr;
                    ptr++;
                } while (ptr < end);
                this.nextToUpdate = end;
            }
            return match.len != 0;
        }

        boolean insertAndFindWiderMatch(byte[] buf, int off, int startLimit, int matchLimit, int minLen, Match match) {
            match.len = minLen;
            insert(off, buf);
            int delta = off - startLimit;
            int ref = hashPointer(buf, off);
            for (int i = 0; i < LZ4HCJavaSafeCompressor.this.maxAttempts && ref >= Math.max(this.base, (off - 65536) + 1) && ref <= off; i++) {
                if (LZ4SafeUtils.readIntEquals(buf, ref, off)) {
                    int matchLenForward = LZ4SafeUtils.commonBytes(buf, ref + 4, off + 4, matchLimit) + 4;
                    int matchLenBackward = LZ4SafeUtils.commonBytesBackward(buf, ref, off, this.base, startLimit);
                    int matchLen = matchLenBackward + matchLenForward;
                    if (matchLen > match.len) {
                        match.len = matchLen;
                        match.ref = ref - matchLenBackward;
                        match.start = off - matchLenBackward;
                    }
                }
                ref = next(ref);
            }
            if (match.len > minLen) {
                return true;
            }
            return false;
        }

        boolean insertAndFindBestMatch(ByteBuffer buf, int off, int matchLimit, Match match) {
            match.start = off;
            match.len = 0;
            int delta = 0;
            int repl = 0;
            insert(off, buf);
            int ref = hashPointer(buf, off);
            if (ref >= off - 4 && ref <= off && ref >= this.base) {
                if (LZ4ByteBufferUtils.readIntEquals(buf, ref, off)) {
                    delta = off - ref;
                    repl = LZ4ByteBufferUtils.commonBytes(buf, ref + 4, off + 4, matchLimit) + 4;
                    match.len = repl;
                    match.ref = ref;
                }
                ref = next(ref);
            }
            for (int i = 0; i < LZ4HCJavaSafeCompressor.this.maxAttempts && ref >= Math.max(this.base, (off - 65536) + 1) && ref <= off; i++) {
                if (LZ4ByteBufferUtils.readIntEquals(buf, ref, off)) {
                    int matchLen = LZ4ByteBufferUtils.commonBytes(buf, ref + 4, off + 4, matchLimit) + 4;
                    if (matchLen > match.len) {
                        match.ref = ref;
                        match.len = matchLen;
                    }
                }
                ref = next(ref);
            }
            if (repl != 0) {
                int ptr = off;
                int end = (off + repl) - 3;
                while (ptr < end - delta) {
                    this.chainTable[65535 & ptr] = (short) delta;
                    ptr++;
                }
                do {
                    this.chainTable[65535 & ptr] = (short) delta;
                    this.hashTable[LZ4Utils.hashHC(ByteBufferUtils.readInt(buf, ptr))] = ptr;
                    ptr++;
                } while (ptr < end);
                this.nextToUpdate = end;
            }
            return match.len != 0;
        }

        boolean insertAndFindWiderMatch(ByteBuffer buf, int off, int startLimit, int matchLimit, int minLen, Match match) {
            match.len = minLen;
            insert(off, buf);
            int delta = off - startLimit;
            int ref = hashPointer(buf, off);
            for (int i = 0; i < LZ4HCJavaSafeCompressor.this.maxAttempts && ref >= Math.max(this.base, (off - 65536) + 1) && ref <= off; i++) {
                if (LZ4ByteBufferUtils.readIntEquals(buf, ref, off)) {
                    int matchLenForward = LZ4ByteBufferUtils.commonBytes(buf, ref + 4, off + 4, matchLimit) + 4;
                    int matchLenBackward = LZ4ByteBufferUtils.commonBytesBackward(buf, ref, off, this.base, startLimit);
                    int matchLen = matchLenBackward + matchLenForward;
                    if (matchLen > match.len) {
                        match.len = matchLen;
                        match.ref = ref - matchLenBackward;
                        match.start = off - matchLenBackward;
                    }
                }
                ref = next(ref);
            }
            if (match.len > minLen) {
                return true;
            }
            return false;
        }
    }

    LZ4HCJavaSafeCompressor() {
        this(9);
    }

    LZ4HCJavaSafeCompressor(int compressionLevel) {
        this.maxAttempts = 1 << (compressionLevel - 1);
        this.compressionLevel = compressionLevel;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compress(byte[] r34, int r35, int r36, byte[] r37, int r38, int r39) {
        /*
        r33 = this;
        org.telegram.customization.compression.util.SafeUtils.checkRange(r34, r35, r36);
        org.telegram.customization.compression.util.SafeUtils.checkRange(r37, r38, r39);
        r32 = r35 + r36;
        r17 = r38 + r39;
        r28 = r32 + -12;
        r7 = r32 + -5;
        r30 = r35;
        r16 = r38;
        r31 = r30 + 1;
        r11 = r30;
        r3 = new org.telegram.customization.compression.lz4.LZ4HCJavaSafeCompressor$HashTable;
        r0 = r33;
        r1 = r35;
        r3.<init>(r1);
        r26 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r26.<init>();
        r27 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r27.<init>();
        r9 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r9.<init>();
        r24 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r24.<init>();
        r30 = r31;
    L_0x0035:
        r0 = r30;
        r1 = r28;
        if (r0 >= r1) goto L_0x025f;
    L_0x003b:
        r0 = r34;
        r1 = r30;
        r2 = r27;
        r4 = r3.insertAndFindBestMatch(r0, r1, r7, r2);
        if (r4 != 0) goto L_0x004a;
    L_0x0047:
        r30 = r30 + 1;
        goto L_0x0035;
    L_0x004a:
        r0 = r27;
        r1 = r26;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r1);
    L_0x0051:
        r4 = $assertionsDisabled;
        if (r4 != 0) goto L_0x0061;
    L_0x0055:
        r0 = r27;
        r4 = r0.start;
        if (r4 >= r11) goto L_0x0061;
    L_0x005b:
        r4 = new java.lang.AssertionError;
        r4.<init>();
        throw r4;
    L_0x0061:
        r4 = r27.end();
        r0 = r28;
        if (r4 >= r0) goto L_0x0081;
    L_0x0069:
        r4 = r27.end();
        r5 = r4 + -2;
        r0 = r27;
        r4 = r0.start;
        r6 = r4 + 1;
        r0 = r27;
        r8 = r0.len;
        r4 = r34;
        r4 = r3.insertAndFindWiderMatch(r4, r5, r6, r7, r8, r9);
        if (r4 != 0) goto L_0x009c;
    L_0x0081:
        r0 = r27;
        r12 = r0.start;
        r0 = r27;
        r13 = r0.ref;
        r0 = r27;
        r14 = r0.len;
        r10 = r34;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4SafeUtils.encodeSequence(r10, r11, r12, r13, r14, r15, r16, r17);
        r30 = r27.end();
        r11 = r30;
        goto L_0x0035;
    L_0x009c:
        r0 = r26;
        r4 = r0.start;
        r0 = r27;
        r5 = r0.start;
        if (r4 >= r5) goto L_0x00b6;
    L_0x00a6:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r0 = r26;
        r6 = r0.len;
        r5 = r5 + r6;
        if (r4 >= r5) goto L_0x00b6;
    L_0x00b3:
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r26, r27);
    L_0x00b6:
        r4 = $assertionsDisabled;
        if (r4 != 0) goto L_0x00c8;
    L_0x00ba:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        if (r4 > r5) goto L_0x00c8;
    L_0x00c2:
        r4 = new java.lang.AssertionError;
        r4.<init>();
        throw r4;
    L_0x00c8:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r5 = 3;
        if (r4 >= r5) goto L_0x00de;
    L_0x00d2:
        r0 = r27;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r9, r0);
        goto L_0x0051;
    L_0x00d9:
        r0 = r24;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r9);
    L_0x00de:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r5 = 18;
        if (r4 >= r5) goto L_0x0121;
    L_0x00e9:
        r0 = r27;
        r0 = r0.len;
        r29 = r0;
        r4 = 18;
        r0 = r29;
        if (r0 <= r4) goto L_0x00f7;
    L_0x00f5:
        r29 = 18;
    L_0x00f7:
        r0 = r27;
        r4 = r0.start;
        r4 = r4 + r29;
        r5 = r9.end();
        r5 = r5 + -4;
        if (r4 <= r5) goto L_0x0111;
    L_0x0105:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r5 = r9.len;
        r4 = r4 + r5;
        r29 = r4 + -4;
    L_0x0111:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r25 = r29 - r4;
        if (r25 <= 0) goto L_0x0121;
    L_0x011c:
        r0 = r25;
        r9.fix(r0);
    L_0x0121:
        r4 = r9.start;
        r5 = r9.len;
        r4 = r4 + r5;
        r0 = r28;
        if (r4 >= r0) goto L_0x0144;
    L_0x012a:
        r4 = r9.end();
        r20 = r4 + -3;
        r0 = r9.start;
        r21 = r0;
        r0 = r9.len;
        r23 = r0;
        r18 = r3;
        r19 = r34;
        r22 = r7;
        r4 = r18.insertAndFindWiderMatch(r19, r20, r21, r22, r23, r24);
        if (r4 != 0) goto L_0x0187;
    L_0x0144:
        r4 = r9.start;
        r5 = r27.end();
        if (r4 >= r5) goto L_0x0157;
    L_0x014c:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r0 = r27;
        r0.len = r4;
    L_0x0157:
        r0 = r27;
        r12 = r0.start;
        r0 = r27;
        r13 = r0.ref;
        r0 = r27;
        r14 = r0.len;
        r10 = r34;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4SafeUtils.encodeSequence(r10, r11, r12, r13, r14, r15, r16, r17);
        r30 = r27.end();
        r11 = r30;
        r12 = r9.start;
        r13 = r9.ref;
        r14 = r9.len;
        r10 = r34;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4SafeUtils.encodeSequence(r10, r11, r12, r13, r14, r15, r16, r17);
        r30 = r9.end();
        r11 = r30;
        goto L_0x0035;
    L_0x0187:
        r0 = r24;
        r4 = r0.start;
        r5 = r27.end();
        r5 = r5 + 3;
        if (r4 >= r5) goto L_0x01e4;
    L_0x0193:
        r0 = r24;
        r4 = r0.start;
        r5 = r27.end();
        if (r4 < r5) goto L_0x00d9;
    L_0x019d:
        r4 = r9.start;
        r5 = r27.end();
        if (r4 >= r5) goto L_0x01bc;
    L_0x01a5:
        r4 = r27.end();
        r5 = r9.start;
        r25 = r4 - r5;
        r0 = r25;
        r9.fix(r0);
        r4 = r9.len;
        r5 = 4;
        if (r4 >= r5) goto L_0x01bc;
    L_0x01b7:
        r0 = r24;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r9);
    L_0x01bc:
        r0 = r27;
        r12 = r0.start;
        r0 = r27;
        r13 = r0.ref;
        r0 = r27;
        r14 = r0.len;
        r10 = r34;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4SafeUtils.encodeSequence(r10, r11, r12, r13, r14, r15, r16, r17);
        r30 = r27.end();
        r11 = r30;
        r0 = r24;
        r1 = r27;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r1);
        r0 = r26;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r9, r0);
        goto L_0x0051;
    L_0x01e4:
        r4 = r9.start;
        r5 = r27.end();
        if (r4 >= r5) goto L_0x022d;
    L_0x01ec:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r5 = 15;
        if (r4 >= r5) goto L_0x0253;
    L_0x01f7:
        r0 = r27;
        r4 = r0.len;
        r5 = 18;
        if (r4 <= r5) goto L_0x0205;
    L_0x01ff:
        r4 = 18;
        r0 = r27;
        r0.len = r4;
    L_0x0205:
        r4 = r27.end();
        r5 = r9.end();
        r5 = r5 + -4;
        if (r4 <= r5) goto L_0x0220;
    L_0x0211:
        r4 = r9.end();
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r4 = r4 + -4;
        r0 = r27;
        r0.len = r4;
    L_0x0220:
        r4 = r27.end();
        r5 = r9.start;
        r25 = r4 - r5;
        r0 = r25;
        r9.fix(r0);
    L_0x022d:
        r0 = r27;
        r12 = r0.start;
        r0 = r27;
        r13 = r0.ref;
        r0 = r27;
        r14 = r0.len;
        r10 = r34;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4SafeUtils.encodeSequence(r10, r11, r12, r13, r14, r15, r16, r17);
        r30 = r27.end();
        r11 = r30;
        r0 = r27;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r9, r0);
        r0 = r24;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r9);
        goto L_0x00de;
    L_0x0253:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r0 = r27;
        r0.len = r4;
        goto L_0x022d;
    L_0x025f:
        r14 = r32 - r11;
        r12 = r34;
        r13 = r11;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4SafeUtils.lastLiterals(r12, r13, r14, r15, r16, r17);
        r4 = r16 - r38;
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.customization.compression.lz4.LZ4HCJavaSafeCompressor.compress(byte[], int, int, byte[], int, int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compress(java.nio.ByteBuffer r34, int r35, int r36, java.nio.ByteBuffer r37, int r38, int r39) {
        /*
        r33 = this;
        r4 = r34.hasArray();
        if (r4 == 0) goto L_0x002b;
    L_0x0006:
        r4 = r37.hasArray();
        if (r4 == 0) goto L_0x002b;
    L_0x000c:
        r4 = r34.array();
        r5 = r34.arrayOffset();
        r5 = r5 + r35;
        r7 = r37.array();
        r6 = r37.arrayOffset();
        r8 = r38 + r6;
        r3 = r33;
        r6 = r36;
        r9 = r39;
        r4 = r3.compress(r4, r5, r6, r7, r8, r9);
    L_0x002a:
        return r4;
    L_0x002b:
        r34 = org.telegram.customization.compression.util.ByteBufferUtils.inNativeByteOrder(r34);
        r37 = org.telegram.customization.compression.util.ByteBufferUtils.inNativeByteOrder(r37);
        org.telegram.customization.compression.util.ByteBufferUtils.checkRange(r34, r35, r36);
        org.telegram.customization.compression.util.ByteBufferUtils.checkRange(r37, r38, r39);
        r32 = r35 + r36;
        r17 = r38 + r39;
        r28 = r32 + -12;
        r7 = r32 + -5;
        r30 = r35;
        r16 = r38;
        r31 = r30 + 1;
        r11 = r30;
        r3 = new org.telegram.customization.compression.lz4.LZ4HCJavaSafeCompressor$HashTable;
        r0 = r33;
        r1 = r35;
        r3.<init>(r1);
        r26 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r26.<init>();
        r27 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r27.<init>();
        r9 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r9.<init>();
        r24 = new org.telegram.customization.compression.lz4.LZ4Utils$Match;
        r24.<init>();
        r30 = r31;
    L_0x0068:
        r0 = r30;
        r1 = r28;
        if (r0 >= r1) goto L_0x0292;
    L_0x006e:
        r0 = r34;
        r1 = r30;
        r2 = r27;
        r4 = r3.insertAndFindBestMatch(r0, r1, r7, r2);
        if (r4 != 0) goto L_0x007d;
    L_0x007a:
        r30 = r30 + 1;
        goto L_0x0068;
    L_0x007d:
        r0 = r27;
        r1 = r26;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r1);
    L_0x0084:
        r4 = $assertionsDisabled;
        if (r4 != 0) goto L_0x0094;
    L_0x0088:
        r0 = r27;
        r4 = r0.start;
        if (r4 >= r11) goto L_0x0094;
    L_0x008e:
        r4 = new java.lang.AssertionError;
        r4.<init>();
        throw r4;
    L_0x0094:
        r4 = r27.end();
        r0 = r28;
        if (r4 >= r0) goto L_0x00b4;
    L_0x009c:
        r4 = r27.end();
        r5 = r4 + -2;
        r0 = r27;
        r4 = r0.start;
        r6 = r4 + 1;
        r0 = r27;
        r8 = r0.len;
        r4 = r34;
        r4 = r3.insertAndFindWiderMatch(r4, r5, r6, r7, r8, r9);
        if (r4 != 0) goto L_0x00cf;
    L_0x00b4:
        r0 = r27;
        r12 = r0.start;
        r0 = r27;
        r13 = r0.ref;
        r0 = r27;
        r14 = r0.len;
        r10 = r34;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.encodeSequence(r10, r11, r12, r13, r14, r15, r16, r17);
        r30 = r27.end();
        r11 = r30;
        goto L_0x0068;
    L_0x00cf:
        r0 = r26;
        r4 = r0.start;
        r0 = r27;
        r5 = r0.start;
        if (r4 >= r5) goto L_0x00e9;
    L_0x00d9:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r0 = r26;
        r6 = r0.len;
        r5 = r5 + r6;
        if (r4 >= r5) goto L_0x00e9;
    L_0x00e6:
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r26, r27);
    L_0x00e9:
        r4 = $assertionsDisabled;
        if (r4 != 0) goto L_0x00fb;
    L_0x00ed:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        if (r4 > r5) goto L_0x00fb;
    L_0x00f5:
        r4 = new java.lang.AssertionError;
        r4.<init>();
        throw r4;
    L_0x00fb:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r5 = 3;
        if (r4 >= r5) goto L_0x0111;
    L_0x0105:
        r0 = r27;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r9, r0);
        goto L_0x0084;
    L_0x010c:
        r0 = r24;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r9);
    L_0x0111:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r5 = 18;
        if (r4 >= r5) goto L_0x0154;
    L_0x011c:
        r0 = r27;
        r0 = r0.len;
        r29 = r0;
        r4 = 18;
        r0 = r29;
        if (r0 <= r4) goto L_0x012a;
    L_0x0128:
        r29 = 18;
    L_0x012a:
        r0 = r27;
        r4 = r0.start;
        r4 = r4 + r29;
        r5 = r9.end();
        r5 = r5 + -4;
        if (r4 <= r5) goto L_0x0144;
    L_0x0138:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r5 = r9.len;
        r4 = r4 + r5;
        r29 = r4 + -4;
    L_0x0144:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r25 = r29 - r4;
        if (r25 <= 0) goto L_0x0154;
    L_0x014f:
        r0 = r25;
        r9.fix(r0);
    L_0x0154:
        r4 = r9.start;
        r5 = r9.len;
        r4 = r4 + r5;
        r0 = r28;
        if (r4 >= r0) goto L_0x0177;
    L_0x015d:
        r4 = r9.end();
        r20 = r4 + -3;
        r0 = r9.start;
        r21 = r0;
        r0 = r9.len;
        r23 = r0;
        r18 = r3;
        r19 = r34;
        r22 = r7;
        r4 = r18.insertAndFindWiderMatch(r19, r20, r21, r22, r23, r24);
        if (r4 != 0) goto L_0x01ba;
    L_0x0177:
        r4 = r9.start;
        r5 = r27.end();
        if (r4 >= r5) goto L_0x018a;
    L_0x017f:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r0 = r27;
        r0.len = r4;
    L_0x018a:
        r0 = r27;
        r12 = r0.start;
        r0 = r27;
        r13 = r0.ref;
        r0 = r27;
        r14 = r0.len;
        r10 = r34;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.encodeSequence(r10, r11, r12, r13, r14, r15, r16, r17);
        r30 = r27.end();
        r11 = r30;
        r12 = r9.start;
        r13 = r9.ref;
        r14 = r9.len;
        r10 = r34;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.encodeSequence(r10, r11, r12, r13, r14, r15, r16, r17);
        r30 = r9.end();
        r11 = r30;
        goto L_0x0068;
    L_0x01ba:
        r0 = r24;
        r4 = r0.start;
        r5 = r27.end();
        r5 = r5 + 3;
        if (r4 >= r5) goto L_0x0217;
    L_0x01c6:
        r0 = r24;
        r4 = r0.start;
        r5 = r27.end();
        if (r4 < r5) goto L_0x010c;
    L_0x01d0:
        r4 = r9.start;
        r5 = r27.end();
        if (r4 >= r5) goto L_0x01ef;
    L_0x01d8:
        r4 = r27.end();
        r5 = r9.start;
        r25 = r4 - r5;
        r0 = r25;
        r9.fix(r0);
        r4 = r9.len;
        r5 = 4;
        if (r4 >= r5) goto L_0x01ef;
    L_0x01ea:
        r0 = r24;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r9);
    L_0x01ef:
        r0 = r27;
        r12 = r0.start;
        r0 = r27;
        r13 = r0.ref;
        r0 = r27;
        r14 = r0.len;
        r10 = r34;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.encodeSequence(r10, r11, r12, r13, r14, r15, r16, r17);
        r30 = r27.end();
        r11 = r30;
        r0 = r24;
        r1 = r27;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r1);
        r0 = r26;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r9, r0);
        goto L_0x0084;
    L_0x0217:
        r4 = r9.start;
        r5 = r27.end();
        if (r4 >= r5) goto L_0x0260;
    L_0x021f:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r5 = 15;
        if (r4 >= r5) goto L_0x0286;
    L_0x022a:
        r0 = r27;
        r4 = r0.len;
        r5 = 18;
        if (r4 <= r5) goto L_0x0238;
    L_0x0232:
        r4 = 18;
        r0 = r27;
        r0.len = r4;
    L_0x0238:
        r4 = r27.end();
        r5 = r9.end();
        r5 = r5 + -4;
        if (r4 <= r5) goto L_0x0253;
    L_0x0244:
        r4 = r9.end();
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r4 = r4 + -4;
        r0 = r27;
        r0.len = r4;
    L_0x0253:
        r4 = r27.end();
        r5 = r9.start;
        r25 = r4 - r5;
        r0 = r25;
        r9.fix(r0);
    L_0x0260:
        r0 = r27;
        r12 = r0.start;
        r0 = r27;
        r13 = r0.ref;
        r0 = r27;
        r14 = r0.len;
        r10 = r34;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.encodeSequence(r10, r11, r12, r13, r14, r15, r16, r17);
        r30 = r27.end();
        r11 = r30;
        r0 = r27;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r9, r0);
        r0 = r24;
        org.telegram.customization.compression.lz4.LZ4Utils.copyTo(r0, r9);
        goto L_0x0111;
    L_0x0286:
        r4 = r9.start;
        r0 = r27;
        r5 = r0.start;
        r4 = r4 - r5;
        r0 = r27;
        r0.len = r4;
        goto L_0x0260;
    L_0x0292:
        r14 = r32 - r11;
        r12 = r34;
        r13 = r11;
        r15 = r37;
        r16 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.lastLiterals(r12, r13, r14, r15, r16, r17);
        r4 = r16 - r38;
        goto L_0x002a;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.customization.compression.lz4.LZ4HCJavaSafeCompressor.compress(java.nio.ByteBuffer, int, int, java.nio.ByteBuffer, int, int):int");
    }
}
