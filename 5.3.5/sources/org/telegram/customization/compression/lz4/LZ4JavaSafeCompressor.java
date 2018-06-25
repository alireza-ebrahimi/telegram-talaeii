package org.telegram.customization.compression.lz4;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.telegram.customization.compression.util.ByteBufferUtils;
import org.telegram.customization.compression.util.SafeUtils;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

public final class LZ4JavaSafeCompressor extends LZ4Compressor {
    public static final LZ4Compressor INSTANCE = new LZ4JavaSafeCompressor();

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int compress64k(byte[] r27, int r28, int r29, byte[] r30, int r31, int r32) {
        /*
        r23 = r28 + r29;
        r24 = r23 + -5;
        r16 = r23 + -12;
        r19 = r28;
        r8 = r31;
        r5 = r19;
        r4 = 13;
        r0 = r29;
        if (r0 < r4) goto L_0x002f;
    L_0x0012:
        r4 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r14 = new short[r4];
        r19 = r19 + 1;
    L_0x0018:
        r12 = r19;
        r25 = 1;
        r4 = 1;
        r6 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r21 = r4 << r6;
    L_0x0021:
        r19 = r12;
        r12 = r12 + r25;
        r22 = r21 + 1;
        r4 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r25 = r21 >>> r4;
        r0 = r16;
        if (r12 <= r0) goto L_0x003e;
    L_0x002f:
        r6 = r23 - r5;
        r4 = r27;
        r7 = r30;
        r9 = r32;
        r8 = org.telegram.customization.compression.lz4.LZ4SafeUtils.lastLiterals(r4, r5, r6, r7, r8, r9);
        r4 = r8 - r31;
        return r4;
    L_0x003e:
        r0 = r27;
        r1 = r19;
        r4 = org.telegram.customization.compression.util.SafeUtils.readInt(r0, r1);
        r13 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r4);
        r4 = org.telegram.customization.compression.util.SafeUtils.readShort(r14, r13);
        r17 = r28 + r4;
        r4 = r19 - r28;
        org.telegram.customization.compression.util.SafeUtils.writeShort(r14, r13, r4);
        r0 = r27;
        r1 = r17;
        r2 = r19;
        r4 = org.telegram.customization.compression.lz4.LZ4SafeUtils.readIntEquals(r0, r1, r2);
        if (r4 == 0) goto L_0x016e;
    L_0x0061:
        r0 = r27;
        r1 = r17;
        r2 = r19;
        r3 = r28;
        r11 = org.telegram.customization.compression.lz4.LZ4SafeUtils.commonBytesBackward(r0, r1, r2, r3, r5);
        r19 = r19 - r11;
        r17 = r17 - r11;
        r18 = r19 - r5;
        r10 = r8 + 1;
        r26 = r8;
        r4 = r10 + r18;
        r4 = r4 + 8;
        r6 = r18 >>> 8;
        r4 = r4 + r6;
        r0 = r32;
        if (r4 <= r0) goto L_0x008b;
    L_0x0082:
        r4 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r6 = "maxDestLen is too small";
        r4.<init>(r6);
        throw r4;
    L_0x008b:
        r4 = 15;
        r0 = r18;
        if (r0 < r4) goto L_0x00d9;
    L_0x0091:
        r4 = 240; // 0xf0 float:3.36E-43 double:1.186E-321;
        r0 = r30;
        r1 = r26;
        org.telegram.customization.compression.util.SafeUtils.writeByte(r0, r1, r4);
        r4 = r18 + -15;
        r0 = r30;
        r8 = org.telegram.customization.compression.lz4.LZ4SafeUtils.writeLen(r4, r0, r10);
    L_0x00a2:
        r0 = r27;
        r1 = r30;
        r2 = r18;
        org.telegram.customization.compression.lz4.LZ4SafeUtils.wildArraycopy(r0, r5, r1, r8, r2);
        r8 = r8 + r18;
    L_0x00ad:
        r4 = r19 - r17;
        r4 = (short) r4;
        r0 = r30;
        org.telegram.customization.compression.util.SafeUtils.writeShortLE(r0, r8, r4);
        r8 = r8 + 2;
        r19 = r19 + 4;
        r17 = r17 + 4;
        r0 = r27;
        r1 = r17;
        r2 = r19;
        r3 = r24;
        r15 = org.telegram.customization.compression.lz4.LZ4SafeUtils.commonBytes(r0, r1, r2, r3);
        r4 = r8 + 6;
        r6 = r15 >>> 8;
        r4 = r4 + r6;
        r0 = r32;
        if (r4 <= r0) goto L_0x00e4;
    L_0x00d0:
        r4 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r6 = "maxDestLen is too small";
        r4.<init>(r6);
        throw r4;
    L_0x00d9:
        r4 = r18 << 4;
        r0 = r30;
        r1 = r26;
        org.telegram.customization.compression.util.SafeUtils.writeByte(r0, r1, r4);
        r8 = r10;
        goto L_0x00a2;
    L_0x00e4:
        r19 = r19 + r15;
        r4 = 15;
        if (r15 < r4) goto L_0x010f;
    L_0x00ea:
        r0 = r30;
        r1 = r26;
        r4 = org.telegram.customization.compression.util.SafeUtils.readByte(r0, r1);
        r4 = r4 | 15;
        r0 = r30;
        r1 = r26;
        org.telegram.customization.compression.util.SafeUtils.writeByte(r0, r1, r4);
        r4 = r15 + -15;
        r0 = r30;
        r8 = org.telegram.customization.compression.lz4.LZ4SafeUtils.writeLen(r4, r0, r8);
        r10 = r8;
    L_0x0104:
        r0 = r19;
        r1 = r16;
        if (r0 <= r1) goto L_0x0121;
    L_0x010a:
        r5 = r19;
        r8 = r10;
        goto L_0x002f;
    L_0x010f:
        r0 = r30;
        r1 = r26;
        r4 = org.telegram.customization.compression.util.SafeUtils.readByte(r0, r1);
        r4 = r4 | r15;
        r0 = r30;
        r1 = r26;
        org.telegram.customization.compression.util.SafeUtils.writeByte(r0, r1, r4);
        r10 = r8;
        goto L_0x0104;
    L_0x0121:
        r4 = r19 + -2;
        r0 = r27;
        r4 = org.telegram.customization.compression.util.SafeUtils.readInt(r0, r4);
        r4 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r4);
        r6 = r19 + -2;
        r6 = r6 - r28;
        org.telegram.customization.compression.util.SafeUtils.writeShort(r14, r4, r6);
        r0 = r27;
        r1 = r19;
        r4 = org.telegram.customization.compression.util.SafeUtils.readInt(r0, r1);
        r13 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r4);
        r4 = org.telegram.customization.compression.util.SafeUtils.readShort(r14, r13);
        r17 = r28 + r4;
        r4 = r19 - r28;
        org.telegram.customization.compression.util.SafeUtils.writeShort(r14, r13, r4);
        r0 = r27;
        r1 = r19;
        r2 = r17;
        r4 = org.telegram.customization.compression.lz4.LZ4SafeUtils.readIntEquals(r0, r1, r2);
        if (r4 != 0) goto L_0x0160;
    L_0x0157:
        r20 = r19 + 1;
        r5 = r19;
        r8 = r10;
        r19 = r20;
        goto L_0x0018;
    L_0x0160:
        r8 = r10 + 1;
        r26 = r10;
        r4 = 0;
        r0 = r30;
        r1 = r26;
        org.telegram.customization.compression.util.SafeUtils.writeByte(r0, r1, r4);
        goto L_0x00ad;
    L_0x016e:
        r21 = r22;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.customization.compression.lz4.LZ4JavaSafeCompressor.compress64k(byte[], int, int, byte[], int, int):int");
    }

    public int compress(byte[] src, int srcOff, int srcLen, byte[] dest, int destOff, int maxDestLen) {
        SafeUtils.checkRange(src, srcOff, srcLen);
        SafeUtils.checkRange(dest, destOff, maxDestLen);
        int destEnd = destOff + maxDestLen;
        if (srcLen < 65547) {
            return compress64k(src, srcOff, srcLen, dest, destOff, destEnd);
        }
        int dOff;
        int srcEnd = srcOff + srcLen;
        int srcLimit = srcEnd - 5;
        int mflimit = srcEnd - 12;
        int sOff = srcOff;
        int dOff2 = destOff;
        int sOff2 = sOff + 1;
        int anchor = sOff;
        int[] hashTable = new int[4096];
        Arrays.fill(hashTable, anchor);
        sOff = sOff2;
        loop0:
        while (true) {
            int ref;
            int forwardOff = sOff;
            int step = 1;
            int searchMatchNb = 1 << LZ4Constants.SKIP_STRENGTH;
            while (true) {
                int back;
                sOff = forwardOff;
                forwardOff += step;
                int searchMatchNb2 = searchMatchNb + 1;
                step = searchMatchNb >>> LZ4Constants.SKIP_STRENGTH;
                if (forwardOff <= mflimit) {
                    int h = LZ4Utils.hash(SafeUtils.readInt(src, sOff));
                    ref = SafeUtils.readInt(hashTable, h);
                    back = sOff - ref;
                    SafeUtils.writeInt(hashTable, h, sOff);
                    if (back < 65536 && LZ4SafeUtils.readIntEquals(src, ref, sOff)) {
                        break;
                    }
                    searchMatchNb = searchMatchNb2;
                } else {
                    break loop0;
                }
            }
            int excess = LZ4SafeUtils.commonBytesBackward(src, ref, sOff, srcOff, anchor);
            sOff -= excess;
            ref -= excess;
            int runLen = sOff - anchor;
            dOff = dOff2 + 1;
            int tokenOff = dOff2;
            if (((dOff + runLen) + 8) + (runLen >>> 8) > destEnd) {
                throw new LZ4Exception("maxDestLen is too small");
            }
            if (runLen >= 15) {
                SafeUtils.writeByte(dest, tokenOff, PsExtractor.VIDEO_STREAM_MASK);
                dOff2 = LZ4SafeUtils.writeLen(runLen - 15, dest, dOff);
            } else {
                SafeUtils.writeByte(dest, tokenOff, runLen << 4);
                dOff2 = dOff;
            }
            LZ4SafeUtils.wildArraycopy(src, anchor, dest, dOff2, runLen);
            dOff2 += runLen;
            while (true) {
                SafeUtils.writeShortLE(dest, dOff2, back);
                dOff2 += 2;
                sOff += 4;
                int matchLen = LZ4SafeUtils.commonBytes(src, ref + 4, sOff, srcLimit);
                if ((dOff2 + 6) + (matchLen >>> 8) > destEnd) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                sOff += matchLen;
                if (matchLen >= 15) {
                    SafeUtils.writeByte(dest, tokenOff, SafeUtils.readByte(dest, tokenOff) | 15);
                    dOff = LZ4SafeUtils.writeLen(matchLen - 15, dest, dOff2);
                } else {
                    SafeUtils.writeByte(dest, tokenOff, SafeUtils.readByte(dest, tokenOff) | matchLen);
                    dOff = dOff2;
                }
                if (sOff > mflimit) {
                    break loop0;
                }
                SafeUtils.writeInt(hashTable, LZ4Utils.hash(SafeUtils.readInt(src, sOff - 2)), sOff - 2);
                h = LZ4Utils.hash(SafeUtils.readInt(src, sOff));
                ref = SafeUtils.readInt(hashTable, h);
                SafeUtils.writeInt(hashTable, h, sOff);
                back = sOff - ref;
                if (back >= 65536 || !LZ4SafeUtils.readIntEquals(src, ref, sOff)) {
                    anchor = sOff;
                    dOff2 = dOff;
                    sOff++;
                } else {
                    dOff2 = dOff + 1;
                    tokenOff = dOff;
                    SafeUtils.writeByte(dest, tokenOff, 0);
                }
            }
            anchor = sOff;
            dOff2 = dOff;
            sOff++;
        }
        anchor = sOff;
        dOff2 = dOff;
        return LZ4SafeUtils.lastLiterals(src, anchor, srcEnd - anchor, dest, dOff2, destEnd) - destOff;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int compress64k(java.nio.ByteBuffer r27, int r28, int r29, java.nio.ByteBuffer r30, int r31, int r32) {
        /*
        r23 = r28 + r29;
        r24 = r23 + -5;
        r16 = r23 + -12;
        r19 = r28;
        r8 = r31;
        r5 = r19;
        r4 = 13;
        r0 = r29;
        if (r0 < r4) goto L_0x002f;
    L_0x0012:
        r4 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r14 = new short[r4];
        r19 = r19 + 1;
    L_0x0018:
        r12 = r19;
        r25 = 1;
        r4 = 1;
        r6 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r21 = r4 << r6;
    L_0x0021:
        r19 = r12;
        r12 = r12 + r25;
        r22 = r21 + 1;
        r4 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r25 = r21 >>> r4;
        r0 = r16;
        if (r12 <= r0) goto L_0x003e;
    L_0x002f:
        r6 = r23 - r5;
        r4 = r27;
        r7 = r30;
        r9 = r32;
        r8 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.lastLiterals(r4, r5, r6, r7, r8, r9);
        r4 = r8 - r31;
        return r4;
    L_0x003e:
        r0 = r27;
        r1 = r19;
        r4 = org.telegram.customization.compression.util.ByteBufferUtils.readInt(r0, r1);
        r13 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r4);
        r4 = org.telegram.customization.compression.util.SafeUtils.readShort(r14, r13);
        r17 = r28 + r4;
        r4 = r19 - r28;
        org.telegram.customization.compression.util.SafeUtils.writeShort(r14, r13, r4);
        r0 = r27;
        r1 = r17;
        r2 = r19;
        r4 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.readIntEquals(r0, r1, r2);
        if (r4 == 0) goto L_0x016e;
    L_0x0061:
        r0 = r27;
        r1 = r17;
        r2 = r19;
        r3 = r28;
        r11 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.commonBytesBackward(r0, r1, r2, r3, r5);
        r19 = r19 - r11;
        r17 = r17 - r11;
        r18 = r19 - r5;
        r10 = r8 + 1;
        r26 = r8;
        r4 = r10 + r18;
        r4 = r4 + 8;
        r6 = r18 >>> 8;
        r4 = r4 + r6;
        r0 = r32;
        if (r4 <= r0) goto L_0x008b;
    L_0x0082:
        r4 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r6 = "maxDestLen is too small";
        r4.<init>(r6);
        throw r4;
    L_0x008b:
        r4 = 15;
        r0 = r18;
        if (r0 < r4) goto L_0x00d9;
    L_0x0091:
        r4 = 240; // 0xf0 float:3.36E-43 double:1.186E-321;
        r0 = r30;
        r1 = r26;
        org.telegram.customization.compression.util.ByteBufferUtils.writeByte(r0, r1, r4);
        r4 = r18 + -15;
        r0 = r30;
        r8 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.writeLen(r4, r0, r10);
    L_0x00a2:
        r0 = r27;
        r1 = r30;
        r2 = r18;
        org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.wildArraycopy(r0, r5, r1, r8, r2);
        r8 = r8 + r18;
    L_0x00ad:
        r4 = r19 - r17;
        r4 = (short) r4;
        r0 = r30;
        org.telegram.customization.compression.util.ByteBufferUtils.writeShortLE(r0, r8, r4);
        r8 = r8 + 2;
        r19 = r19 + 4;
        r17 = r17 + 4;
        r0 = r27;
        r1 = r17;
        r2 = r19;
        r3 = r24;
        r15 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.commonBytes(r0, r1, r2, r3);
        r4 = r8 + 6;
        r6 = r15 >>> 8;
        r4 = r4 + r6;
        r0 = r32;
        if (r4 <= r0) goto L_0x00e4;
    L_0x00d0:
        r4 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r6 = "maxDestLen is too small";
        r4.<init>(r6);
        throw r4;
    L_0x00d9:
        r4 = r18 << 4;
        r0 = r30;
        r1 = r26;
        org.telegram.customization.compression.util.ByteBufferUtils.writeByte(r0, r1, r4);
        r8 = r10;
        goto L_0x00a2;
    L_0x00e4:
        r19 = r19 + r15;
        r4 = 15;
        if (r15 < r4) goto L_0x010f;
    L_0x00ea:
        r0 = r30;
        r1 = r26;
        r4 = org.telegram.customization.compression.util.ByteBufferUtils.readByte(r0, r1);
        r4 = r4 | 15;
        r0 = r30;
        r1 = r26;
        org.telegram.customization.compression.util.ByteBufferUtils.writeByte(r0, r1, r4);
        r4 = r15 + -15;
        r0 = r30;
        r8 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.writeLen(r4, r0, r8);
        r10 = r8;
    L_0x0104:
        r0 = r19;
        r1 = r16;
        if (r0 <= r1) goto L_0x0121;
    L_0x010a:
        r5 = r19;
        r8 = r10;
        goto L_0x002f;
    L_0x010f:
        r0 = r30;
        r1 = r26;
        r4 = org.telegram.customization.compression.util.ByteBufferUtils.readByte(r0, r1);
        r4 = r4 | r15;
        r0 = r30;
        r1 = r26;
        org.telegram.customization.compression.util.ByteBufferUtils.writeByte(r0, r1, r4);
        r10 = r8;
        goto L_0x0104;
    L_0x0121:
        r4 = r19 + -2;
        r0 = r27;
        r4 = org.telegram.customization.compression.util.ByteBufferUtils.readInt(r0, r4);
        r4 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r4);
        r6 = r19 + -2;
        r6 = r6 - r28;
        org.telegram.customization.compression.util.SafeUtils.writeShort(r14, r4, r6);
        r0 = r27;
        r1 = r19;
        r4 = org.telegram.customization.compression.util.ByteBufferUtils.readInt(r0, r1);
        r13 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r4);
        r4 = org.telegram.customization.compression.util.SafeUtils.readShort(r14, r13);
        r17 = r28 + r4;
        r4 = r19 - r28;
        org.telegram.customization.compression.util.SafeUtils.writeShort(r14, r13, r4);
        r0 = r27;
        r1 = r19;
        r2 = r17;
        r4 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.readIntEquals(r0, r1, r2);
        if (r4 != 0) goto L_0x0160;
    L_0x0157:
        r20 = r19 + 1;
        r5 = r19;
        r8 = r10;
        r19 = r20;
        goto L_0x0018;
    L_0x0160:
        r8 = r10 + 1;
        r26 = r10;
        r4 = 0;
        r0 = r30;
        r1 = r26;
        org.telegram.customization.compression.util.ByteBufferUtils.writeByte(r0, r1, r4);
        goto L_0x00ad;
    L_0x016e:
        r21 = r22;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.customization.compression.lz4.LZ4JavaSafeCompressor.compress64k(java.nio.ByteBuffer, int, int, java.nio.ByteBuffer, int, int):int");
    }

    public int compress(ByteBuffer src, int srcOff, int srcLen, ByteBuffer dest, int destOff, int maxDestLen) {
        if (src.hasArray() && dest.hasArray()) {
            return compress(src.array(), srcOff + src.arrayOffset(), srcLen, dest.array(), destOff + dest.arrayOffset(), maxDestLen);
        }
        src = ByteBufferUtils.inNativeByteOrder(src);
        dest = ByteBufferUtils.inNativeByteOrder(dest);
        ByteBufferUtils.checkRange(src, srcOff, srcLen);
        ByteBufferUtils.checkRange(dest, destOff, maxDestLen);
        int destEnd = destOff + maxDestLen;
        if (srcLen < 65547) {
            return compress64k(src, srcOff, srcLen, dest, destOff, destEnd);
        }
        int dOff;
        int srcEnd = srcOff + srcLen;
        int srcLimit = srcEnd - 5;
        int mflimit = srcEnd - 12;
        int sOff = srcOff;
        int dOff2 = destOff;
        int sOff2 = sOff + 1;
        int anchor = sOff;
        int[] hashTable = new int[4096];
        Arrays.fill(hashTable, anchor);
        sOff = sOff2;
        loop0:
        while (true) {
            int ref;
            int forwardOff = sOff;
            int step = 1;
            int searchMatchNb = 1 << LZ4Constants.SKIP_STRENGTH;
            while (true) {
                int back;
                sOff = forwardOff;
                forwardOff += step;
                int searchMatchNb2 = searchMatchNb + 1;
                step = searchMatchNb >>> LZ4Constants.SKIP_STRENGTH;
                if (forwardOff <= mflimit) {
                    int h = LZ4Utils.hash(ByteBufferUtils.readInt(src, sOff));
                    ref = SafeUtils.readInt(hashTable, h);
                    back = sOff - ref;
                    SafeUtils.writeInt(hashTable, h, sOff);
                    if (back < 65536 && LZ4ByteBufferUtils.readIntEquals(src, ref, sOff)) {
                        break;
                    }
                    searchMatchNb = searchMatchNb2;
                } else {
                    break loop0;
                }
            }
            int excess = LZ4ByteBufferUtils.commonBytesBackward(src, ref, sOff, srcOff, anchor);
            sOff -= excess;
            ref -= excess;
            int runLen = sOff - anchor;
            dOff = dOff2 + 1;
            int tokenOff = dOff2;
            if (((dOff + runLen) + 8) + (runLen >>> 8) > destEnd) {
                throw new LZ4Exception("maxDestLen is too small");
            }
            if (runLen >= 15) {
                ByteBufferUtils.writeByte(dest, tokenOff, PsExtractor.VIDEO_STREAM_MASK);
                dOff2 = LZ4ByteBufferUtils.writeLen(runLen - 15, dest, dOff);
            } else {
                ByteBufferUtils.writeByte(dest, tokenOff, runLen << 4);
                dOff2 = dOff;
            }
            LZ4ByteBufferUtils.wildArraycopy(src, anchor, dest, dOff2, runLen);
            dOff2 += runLen;
            while (true) {
                ByteBufferUtils.writeShortLE(dest, dOff2, back);
                dOff2 += 2;
                sOff += 4;
                int matchLen = LZ4ByteBufferUtils.commonBytes(src, ref + 4, sOff, srcLimit);
                if ((dOff2 + 6) + (matchLen >>> 8) > destEnd) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                sOff += matchLen;
                if (matchLen >= 15) {
                    ByteBufferUtils.writeByte(dest, tokenOff, ByteBufferUtils.readByte(dest, tokenOff) | 15);
                    dOff = LZ4ByteBufferUtils.writeLen(matchLen - 15, dest, dOff2);
                } else {
                    ByteBufferUtils.writeByte(dest, tokenOff, ByteBufferUtils.readByte(dest, tokenOff) | matchLen);
                    dOff = dOff2;
                }
                if (sOff > mflimit) {
                    break loop0;
                }
                int[] iArr = hashTable;
                SafeUtils.writeInt(iArr, LZ4Utils.hash(ByteBufferUtils.readInt(src, sOff - 2)), sOff - 2);
                h = LZ4Utils.hash(ByteBufferUtils.readInt(src, sOff));
                ref = SafeUtils.readInt(hashTable, h);
                SafeUtils.writeInt(hashTable, h, sOff);
                back = sOff - ref;
                if (back >= 65536 || !LZ4ByteBufferUtils.readIntEquals(src, ref, sOff)) {
                    anchor = sOff;
                    dOff2 = dOff;
                    sOff++;
                } else {
                    dOff2 = dOff + 1;
                    tokenOff = dOff;
                    ByteBufferUtils.writeByte(dest, tokenOff, 0);
                }
            }
        }
        anchor = sOff;
        dOff2 = dOff;
        return LZ4ByteBufferUtils.lastLiterals(src, anchor, srcEnd - anchor, dest, dOff2, destEnd) - destOff;
    }
}
