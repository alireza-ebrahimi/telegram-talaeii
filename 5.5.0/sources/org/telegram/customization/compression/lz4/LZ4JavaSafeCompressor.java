package org.telegram.customization.compression.lz4;

public final class LZ4JavaSafeCompressor extends LZ4Compressor {
    public static final LZ4Compressor INSTANCE = new LZ4JavaSafeCompressor();

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int compress64k(java.nio.ByteBuffer r15, int r16, int r17, java.nio.ByteBuffer r18, int r19, int r20) {
        /*
        r7 = r16 + r17;
        r8 = r7 + -5;
        r9 = r7 + -12;
        r1 = 13;
        r0 = r17;
        if (r0 < r1) goto L_0x012d;
    L_0x000c:
        r1 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r10 = new short[r1];
        r1 = r16 + 1;
        r2 = r19;
        r3 = r1;
        r1 = r16;
    L_0x0017:
        r5 = 1;
        r4 = 1;
        r6 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r4 = r4 << r6;
        r14 = r4;
        r4 = r5;
        r5 = r3;
        r3 = r14;
    L_0x0020:
        r6 = r5 + r4;
        r4 = r3 + 1;
        r11 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r3 = r3 >>> r11;
        if (r6 <= r9) goto L_0x0039;
    L_0x0029:
        r5 = r2;
        r2 = r1;
    L_0x002b:
        r3 = r7 - r2;
        r1 = r15;
        r4 = r18;
        r6 = r20;
        r1 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.lastLiterals(r1, r2, r3, r4, r5, r6);
        r1 = r1 - r19;
        return r1;
    L_0x0039:
        r11 = org.telegram.customization.compression.p160a.C2672a.m12567c(r15, r5);
        r11 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r11);
        r12 = org.telegram.customization.compression.p160a.C2675c.m12578a(r10, r11);
        r12 = r12 + r16;
        r13 = r5 - r16;
        org.telegram.customization.compression.p160a.C2675c.m12583a(r10, r11, r13);
        r11 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.readIntEquals(r15, r12, r5);
        if (r11 == 0) goto L_0x0127;
    L_0x0052:
        r0 = r16;
        r3 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.commonBytesBackward(r15, r12, r5, r0, r1);
        r5 = r5 - r3;
        r4 = r12 - r3;
        r6 = r5 - r1;
        r3 = r2 + 1;
        r11 = r3 + r6;
        r11 = r11 + 8;
        r12 = r6 >>> 8;
        r11 = r11 + r12;
        r0 = r20;
        if (r11 <= r0) goto L_0x0073;
    L_0x006a:
        r1 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r2 = "maxDestLen is too small";
        r1.<init>(r2);
        throw r1;
    L_0x0073:
        r11 = 15;
        if (r6 < r11) goto L_0x00b4;
    L_0x0077:
        r11 = 240; // 0xf0 float:3.36E-43 double:1.186E-321;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2672a.m12568c(r0, r2, r11);
        r11 = r6 + -15;
        r0 = r18;
        r3 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.writeLen(r11, r0, r3);
    L_0x0086:
        r0 = r18;
        org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.wildArraycopy(r15, r1, r0, r3, r6);
        r1 = r3 + r6;
        r3 = r1;
        r1 = r4;
        r4 = r5;
    L_0x0090:
        r5 = r4 - r1;
        r5 = (short) r5;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2672a.m12570d(r0, r3, r5);
        r3 = r3 + 2;
        r4 = r4 + 4;
        r1 = r1 + 4;
        r5 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.commonBytes(r15, r1, r4, r8);
        r1 = r3 + 6;
        r6 = r5 >>> 8;
        r1 = r1 + r6;
        r0 = r20;
        if (r1 <= r0) goto L_0x00bc;
    L_0x00ab:
        r1 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r2 = "maxDestLen is too small";
        r1.<init>(r2);
        throw r1;
    L_0x00b4:
        r11 = r6 << 4;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2672a.m12568c(r0, r2, r11);
        goto L_0x0086;
    L_0x00bc:
        r1 = r4 + r5;
        r4 = 15;
        if (r5 < r4) goto L_0x00dd;
    L_0x00c2:
        r0 = r18;
        r4 = org.telegram.customization.compression.p160a.C2672a.m12564b(r0, r2);
        r4 = r4 | 15;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2672a.m12568c(r0, r2, r4);
        r2 = r5 + -15;
        r0 = r18;
        r2 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.writeLen(r2, r0, r3);
    L_0x00d7:
        if (r1 <= r9) goto L_0x00eb;
    L_0x00d9:
        r5 = r2;
        r2 = r1;
        goto L_0x002b;
    L_0x00dd:
        r0 = r18;
        r4 = org.telegram.customization.compression.p160a.C2672a.m12564b(r0, r2);
        r4 = r4 | r5;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2672a.m12568c(r0, r2, r4);
        r2 = r3;
        goto L_0x00d7;
    L_0x00eb:
        r3 = r1 + -2;
        r3 = org.telegram.customization.compression.p160a.C2672a.m12567c(r15, r3);
        r3 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r3);
        r4 = r1 + -2;
        r4 = r4 - r16;
        org.telegram.customization.compression.p160a.C2675c.m12583a(r10, r3, r4);
        r3 = org.telegram.customization.compression.p160a.C2672a.m12567c(r15, r1);
        r4 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r3);
        r3 = org.telegram.customization.compression.p160a.C2675c.m12578a(r10, r4);
        r3 = r3 + r16;
        r5 = r1 - r16;
        org.telegram.customization.compression.p160a.C2675c.m12583a(r10, r4, r5);
        r4 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.readIntEquals(r15, r1, r3);
        if (r4 != 0) goto L_0x0119;
    L_0x0115:
        r3 = r1 + 1;
        goto L_0x0017;
    L_0x0119:
        r4 = r2 + 1;
        r5 = 0;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2672a.m12568c(r0, r2, r5);
        r14 = r3;
        r3 = r4;
        r4 = r1;
        r1 = r14;
        goto L_0x0090;
    L_0x0127:
        r5 = r6;
        r14 = r3;
        r3 = r4;
        r4 = r14;
        goto L_0x0020;
    L_0x012d:
        r2 = r16;
        r5 = r19;
        goto L_0x002b;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.customization.compression.lz4.LZ4JavaSafeCompressor.compress64k(java.nio.ByteBuffer, int, int, java.nio.ByteBuffer, int, int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int compress64k(byte[] r15, int r16, int r17, byte[] r18, int r19, int r20) {
        /*
        r7 = r16 + r17;
        r8 = r7 + -5;
        r9 = r7 + -12;
        r1 = 13;
        r0 = r17;
        if (r0 < r1) goto L_0x012d;
    L_0x000c:
        r1 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r10 = new short[r1];
        r1 = r16 + 1;
        r2 = r19;
        r3 = r1;
        r1 = r16;
    L_0x0017:
        r5 = 1;
        r4 = 1;
        r6 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r4 = r4 << r6;
        r14 = r4;
        r4 = r5;
        r5 = r3;
        r3 = r14;
    L_0x0020:
        r6 = r5 + r4;
        r4 = r3 + 1;
        r11 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r3 = r3 >>> r11;
        if (r6 <= r9) goto L_0x0039;
    L_0x0029:
        r5 = r2;
        r2 = r1;
    L_0x002b:
        r3 = r7 - r2;
        r1 = r15;
        r4 = r18;
        r6 = r20;
        r1 = org.telegram.customization.compression.lz4.LZ4SafeUtils.lastLiterals(r1, r2, r3, r4, r5, r6);
        r1 = r1 - r19;
        return r1;
    L_0x0039:
        r11 = org.telegram.customization.compression.p160a.C2675c.m12589e(r15, r5);
        r11 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r11);
        r12 = org.telegram.customization.compression.p160a.C2675c.m12578a(r10, r11);
        r12 = r12 + r16;
        r13 = r5 - r16;
        org.telegram.customization.compression.p160a.C2675c.m12583a(r10, r11, r13);
        r11 = org.telegram.customization.compression.lz4.LZ4SafeUtils.readIntEquals(r15, r12, r5);
        if (r11 == 0) goto L_0x0127;
    L_0x0052:
        r0 = r16;
        r3 = org.telegram.customization.compression.lz4.LZ4SafeUtils.commonBytesBackward(r15, r12, r5, r0, r1);
        r5 = r5 - r3;
        r4 = r12 - r3;
        r6 = r5 - r1;
        r3 = r2 + 1;
        r11 = r3 + r6;
        r11 = r11 + 8;
        r12 = r6 >>> 8;
        r11 = r11 + r12;
        r0 = r20;
        if (r11 <= r0) goto L_0x0073;
    L_0x006a:
        r1 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r2 = "maxDestLen is too small";
        r1.<init>(r2);
        throw r1;
    L_0x0073:
        r11 = 15;
        if (r6 < r11) goto L_0x00b4;
    L_0x0077:
        r11 = 240; // 0xf0 float:3.36E-43 double:1.186E-321;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2675c.m12587c(r0, r2, r11);
        r11 = r6 + -15;
        r0 = r18;
        r3 = org.telegram.customization.compression.lz4.LZ4SafeUtils.writeLen(r11, r0, r3);
    L_0x0086:
        r0 = r18;
        org.telegram.customization.compression.lz4.LZ4SafeUtils.wildArraycopy(r15, r1, r0, r3, r6);
        r1 = r3 + r6;
        r3 = r1;
        r1 = r4;
        r4 = r5;
    L_0x0090:
        r5 = r4 - r1;
        r5 = (short) r5;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2675c.m12585b(r0, r3, r5);
        r3 = r3 + 2;
        r4 = r4 + 4;
        r1 = r1 + 4;
        r5 = org.telegram.customization.compression.lz4.LZ4SafeUtils.commonBytes(r15, r1, r4, r8);
        r1 = r3 + 6;
        r6 = r5 >>> 8;
        r1 = r1 + r6;
        r0 = r20;
        if (r1 <= r0) goto L_0x00bc;
    L_0x00ab:
        r1 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r2 = "maxDestLen is too small";
        r1.<init>(r2);
        throw r1;
    L_0x00b4:
        r11 = r6 << 4;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2675c.m12587c(r0, r2, r11);
        goto L_0x0086;
    L_0x00bc:
        r1 = r4 + r5;
        r4 = 15;
        if (r5 < r4) goto L_0x00dd;
    L_0x00c2:
        r0 = r18;
        r4 = org.telegram.customization.compression.p160a.C2675c.m12584b(r0, r2);
        r4 = r4 | 15;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2675c.m12587c(r0, r2, r4);
        r2 = r5 + -15;
        r0 = r18;
        r2 = org.telegram.customization.compression.lz4.LZ4SafeUtils.writeLen(r2, r0, r3);
    L_0x00d7:
        if (r1 <= r9) goto L_0x00eb;
    L_0x00d9:
        r5 = r2;
        r2 = r1;
        goto L_0x002b;
    L_0x00dd:
        r0 = r18;
        r4 = org.telegram.customization.compression.p160a.C2675c.m12584b(r0, r2);
        r4 = r4 | r5;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2675c.m12587c(r0, r2, r4);
        r2 = r3;
        goto L_0x00d7;
    L_0x00eb:
        r3 = r1 + -2;
        r3 = org.telegram.customization.compression.p160a.C2675c.m12589e(r15, r3);
        r3 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r3);
        r4 = r1 + -2;
        r4 = r4 - r16;
        org.telegram.customization.compression.p160a.C2675c.m12583a(r10, r3, r4);
        r3 = org.telegram.customization.compression.p160a.C2675c.m12589e(r15, r1);
        r4 = org.telegram.customization.compression.lz4.LZ4Utils.hash64k(r3);
        r3 = org.telegram.customization.compression.p160a.C2675c.m12578a(r10, r4);
        r3 = r3 + r16;
        r5 = r1 - r16;
        org.telegram.customization.compression.p160a.C2675c.m12583a(r10, r4, r5);
        r4 = org.telegram.customization.compression.lz4.LZ4SafeUtils.readIntEquals(r15, r1, r3);
        if (r4 != 0) goto L_0x0119;
    L_0x0115:
        r3 = r1 + 1;
        goto L_0x0017;
    L_0x0119:
        r4 = r2 + 1;
        r5 = 0;
        r0 = r18;
        org.telegram.customization.compression.p160a.C2675c.m12587c(r0, r2, r5);
        r14 = r3;
        r3 = r4;
        r4 = r1;
        r1 = r14;
        goto L_0x0090;
    L_0x0127:
        r5 = r6;
        r14 = r3;
        r3 = r4;
        r4 = r14;
        goto L_0x0020;
    L_0x012d:
        r2 = r16;
        r5 = r19;
        goto L_0x002b;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.customization.compression.lz4.LZ4JavaSafeCompressor.compress64k(byte[], int, int, byte[], int, int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compress(java.nio.ByteBuffer r20, int r21, int r22, java.nio.ByteBuffer r23, int r24, int r25) {
        /*
        r19 = this;
        r2 = r20.hasArray();
        if (r2 == 0) goto L_0x002b;
    L_0x0006:
        r2 = r23.hasArray();
        if (r2 == 0) goto L_0x002b;
    L_0x000c:
        r3 = r20.array();
        r2 = r20.arrayOffset();
        r4 = r21 + r2;
        r6 = r23.array();
        r2 = r23.arrayOffset();
        r7 = r24 + r2;
        r2 = r19;
        r5 = r22;
        r8 = r25;
        r2 = r2.compress(r3, r4, r5, r6, r7, r8);
    L_0x002a:
        return r2;
    L_0x002b:
        r2 = org.telegram.customization.compression.p160a.C2672a.m12560a(r20);
        r5 = org.telegram.customization.compression.p160a.C2672a.m12560a(r23);
        r0 = r21;
        r1 = r22;
        org.telegram.customization.compression.p160a.C2672a.m12562a(r2, r0, r1);
        r0 = r24;
        r1 = r25;
        org.telegram.customization.compression.p160a.C2672a.m12562a(r5, r0, r1);
        r7 = r24 + r25;
        r3 = 65547; // 0x1000b float:9.1851E-41 double:3.23845E-319;
        r0 = r22;
        if (r0 >= r3) goto L_0x0055;
    L_0x004a:
        r3 = r21;
        r4 = r22;
        r6 = r24;
        r2 = compress64k(r2, r3, r4, r5, r6, r7);
        goto L_0x002a;
    L_0x0055:
        r12 = r21 + r22;
        r13 = r12 + -5;
        r14 = r12 + -12;
        r3 = r21 + 1;
        r4 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r15 = new int[r4];
        r0 = r21;
        java.util.Arrays.fill(r15, r0);
        r4 = r24;
        r6 = r3;
        r3 = r21;
    L_0x006b:
        r9 = 1;
        r8 = 1;
        r10 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r8 = r8 << r10;
        r18 = r8;
        r8 = r9;
        r9 = r6;
        r6 = r18;
    L_0x0076:
        r10 = r9 + r8;
        r8 = r6 + 1;
        r11 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r6 = r6 >>> r11;
        if (r10 <= r14) goto L_0x0089;
    L_0x007f:
        r6 = r4;
    L_0x0080:
        r4 = r12 - r3;
        r2 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.lastLiterals(r2, r3, r4, r5, r6, r7);
        r2 = r2 - r24;
        goto L_0x002a;
    L_0x0089:
        r11 = org.telegram.customization.compression.p160a.C2672a.m12567c(r2, r9);
        r16 = org.telegram.customization.compression.lz4.LZ4Utils.hash(r11);
        r17 = org.telegram.customization.compression.p160a.C2675c.m12577a(r15, r16);
        r11 = r9 - r17;
        r0 = r16;
        org.telegram.customization.compression.p160a.C2675c.m12582a(r15, r0, r9);
        r16 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r0 = r16;
        if (r11 >= r0) goto L_0x0170;
    L_0x00a2:
        r0 = r17;
        r16 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.readIntEquals(r2, r0, r9);
        if (r16 == 0) goto L_0x0170;
    L_0x00aa:
        r0 = r17;
        r1 = r21;
        r6 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.commonBytesBackward(r2, r0, r9, r1, r3);
        r9 = r9 - r6;
        r8 = r17 - r6;
        r10 = r9 - r3;
        r6 = r4 + 1;
        r16 = r6 + r10;
        r16 = r16 + 8;
        r17 = r10 >>> 8;
        r16 = r16 + r17;
        r0 = r16;
        if (r0 <= r7) goto L_0x00ce;
    L_0x00c5:
        r2 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r3 = "maxDestLen is too small";
        r2.<init>(r3);
        throw r2;
    L_0x00ce:
        r16 = 15;
        r0 = r16;
        if (r10 < r0) goto L_0x0108;
    L_0x00d4:
        r16 = 240; // 0xf0 float:3.36E-43 double:1.186E-321;
        r0 = r16;
        org.telegram.customization.compression.p160a.C2672a.m12568c(r5, r4, r0);
        r16 = r10 + -15;
        r0 = r16;
        r6 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.writeLen(r0, r5, r6);
    L_0x00e3:
        org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.wildArraycopy(r2, r3, r5, r6, r10);
        r3 = r6 + r10;
        r6 = r8;
        r8 = r3;
        r3 = r11;
    L_0x00eb:
        org.telegram.customization.compression.p160a.C2672a.m12570d(r5, r8, r3);
        r8 = r8 + 2;
        r3 = r9 + 4;
        r6 = r6 + 4;
        r6 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.commonBytes(r2, r6, r3, r13);
        r9 = r8 + 6;
        r10 = r6 >>> 8;
        r9 = r9 + r10;
        if (r9 <= r7) goto L_0x0110;
    L_0x00ff:
        r2 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r3 = "maxDestLen is too small";
        r2.<init>(r3);
        throw r2;
    L_0x0108:
        r16 = r10 << 4;
        r0 = r16;
        org.telegram.customization.compression.p160a.C2672a.m12568c(r5, r4, r0);
        goto L_0x00e3;
    L_0x0110:
        r3 = r3 + r6;
        r9 = 15;
        if (r6 < r9) goto L_0x0129;
    L_0x0115:
        r9 = org.telegram.customization.compression.p160a.C2672a.m12564b(r5, r4);
        r9 = r9 | 15;
        org.telegram.customization.compression.p160a.C2672a.m12568c(r5, r4, r9);
        r4 = r6 + -15;
        r4 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.writeLen(r4, r5, r8);
    L_0x0124:
        if (r3 <= r14) goto L_0x0133;
    L_0x0126:
        r6 = r4;
        goto L_0x0080;
    L_0x0129:
        r9 = org.telegram.customization.compression.p160a.C2672a.m12564b(r5, r4);
        r6 = r6 | r9;
        org.telegram.customization.compression.p160a.C2672a.m12568c(r5, r4, r6);
        r4 = r8;
        goto L_0x0124;
    L_0x0133:
        r6 = r3 + -2;
        r6 = org.telegram.customization.compression.p160a.C2672a.m12567c(r2, r6);
        r6 = org.telegram.customization.compression.lz4.LZ4Utils.hash(r6);
        r8 = r3 + -2;
        org.telegram.customization.compression.p160a.C2675c.m12582a(r15, r6, r8);
        r6 = org.telegram.customization.compression.p160a.C2672a.m12567c(r2, r3);
        r6 = org.telegram.customization.compression.lz4.LZ4Utils.hash(r6);
        r8 = org.telegram.customization.compression.p160a.C2675c.m12577a(r15, r6);
        org.telegram.customization.compression.p160a.C2675c.m12582a(r15, r6, r3);
        r6 = r3 - r8;
        r9 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        if (r6 >= r9) goto L_0x015d;
    L_0x0157:
        r9 = org.telegram.customization.compression.lz4.LZ4ByteBufferUtils.readIntEquals(r2, r8, r3);
        if (r9 != 0) goto L_0x0161;
    L_0x015d:
        r6 = r3 + 1;
        goto L_0x006b;
    L_0x0161:
        r9 = r4 + 1;
        r10 = 0;
        org.telegram.customization.compression.p160a.C2672a.m12568c(r5, r4, r10);
        r18 = r6;
        r6 = r8;
        r8 = r9;
        r9 = r3;
        r3 = r18;
        goto L_0x00eb;
    L_0x0170:
        r9 = r10;
        r18 = r6;
        r6 = r8;
        r8 = r18;
        goto L_0x0076;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.customization.compression.lz4.LZ4JavaSafeCompressor.compress(java.nio.ByteBuffer, int, int, java.nio.ByteBuffer, int, int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compress(byte[] r18, int r19, int r20, byte[] r21, int r22, int r23) {
        /*
        r17 = this;
        org.telegram.customization.compression.p160a.C2675c.m12581a(r18, r19, r20);
        org.telegram.customization.compression.p160a.C2675c.m12581a(r21, r22, r23);
        r7 = r22 + r23;
        r2 = 65547; // 0x1000b float:9.1851E-41 double:3.23845E-319;
        r0 = r20;
        if (r0 >= r2) goto L_0x001e;
    L_0x000f:
        r2 = r18;
        r3 = r19;
        r4 = r20;
        r5 = r21;
        r6 = r22;
        r2 = compress64k(r2, r3, r4, r5, r6, r7);
    L_0x001d:
        return r2;
    L_0x001e:
        r10 = r19 + r20;
        r11 = r10 + -5;
        r12 = r10 + -12;
        r2 = r19 + 1;
        r3 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r13 = new int[r3];
        r0 = r19;
        java.util.Arrays.fill(r13, r0);
        r3 = r22;
        r4 = r2;
        r2 = r19;
    L_0x0034:
        r6 = 1;
        r5 = 1;
        r8 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r5 = r5 << r8;
        r16 = r5;
        r5 = r6;
        r6 = r4;
        r4 = r16;
    L_0x003f:
        r8 = r6 + r5;
        r5 = r4 + 1;
        r9 = org.telegram.customization.compression.lz4.LZ4Constants.SKIP_STRENGTH;
        r4 = r4 >>> r9;
        if (r8 <= r12) goto L_0x0057;
    L_0x0048:
        r6 = r3;
        r3 = r2;
    L_0x004a:
        r4 = r10 - r3;
        r2 = r18;
        r5 = r21;
        r2 = org.telegram.customization.compression.lz4.LZ4SafeUtils.lastLiterals(r2, r3, r4, r5, r6, r7);
        r2 = r2 - r22;
        goto L_0x001d;
    L_0x0057:
        r0 = r18;
        r9 = org.telegram.customization.compression.p160a.C2675c.m12589e(r0, r6);
        r14 = org.telegram.customization.compression.lz4.LZ4Utils.hash(r9);
        r15 = org.telegram.customization.compression.p160a.C2675c.m12577a(r13, r14);
        r9 = r6 - r15;
        org.telegram.customization.compression.p160a.C2675c.m12582a(r13, r14, r6);
        r14 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        if (r9 >= r14) goto L_0x0152;
    L_0x006e:
        r0 = r18;
        r14 = org.telegram.customization.compression.lz4.LZ4SafeUtils.readIntEquals(r0, r15, r6);
        if (r14 == 0) goto L_0x0152;
    L_0x0076:
        r0 = r18;
        r1 = r19;
        r4 = org.telegram.customization.compression.lz4.LZ4SafeUtils.commonBytesBackward(r0, r15, r6, r1, r2);
        r6 = r6 - r4;
        r5 = r15 - r4;
        r8 = r6 - r2;
        r4 = r3 + 1;
        r14 = r4 + r8;
        r14 = r14 + 8;
        r15 = r8 >>> 8;
        r14 = r14 + r15;
        if (r14 <= r7) goto L_0x0097;
    L_0x008e:
        r2 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r3 = "maxDestLen is too small";
        r2.<init>(r3);
        throw r2;
    L_0x0097:
        r14 = 15;
        if (r8 < r14) goto L_0x00d7;
    L_0x009b:
        r14 = 240; // 0xf0 float:3.36E-43 double:1.186E-321;
        r0 = r21;
        org.telegram.customization.compression.p160a.C2675c.m12587c(r0, r3, r14);
        r14 = r8 + -15;
        r0 = r21;
        r4 = org.telegram.customization.compression.lz4.LZ4SafeUtils.writeLen(r14, r0, r4);
    L_0x00aa:
        r0 = r18;
        r1 = r21;
        org.telegram.customization.compression.lz4.LZ4SafeUtils.wildArraycopy(r0, r2, r1, r4, r8);
        r2 = r4 + r8;
        r4 = r5;
        r5 = r2;
        r2 = r9;
    L_0x00b6:
        r0 = r21;
        org.telegram.customization.compression.p160a.C2675c.m12585b(r0, r5, r2);
        r5 = r5 + 2;
        r2 = r6 + 4;
        r4 = r4 + 4;
        r0 = r18;
        r4 = org.telegram.customization.compression.lz4.LZ4SafeUtils.commonBytes(r0, r4, r2, r11);
        r6 = r5 + 6;
        r8 = r4 >>> 8;
        r6 = r6 + r8;
        if (r6 <= r7) goto L_0x00df;
    L_0x00ce:
        r2 = new org.telegram.customization.compression.lz4.LZ4Exception;
        r3 = "maxDestLen is too small";
        r2.<init>(r3);
        throw r2;
    L_0x00d7:
        r14 = r8 << 4;
        r0 = r21;
        org.telegram.customization.compression.p160a.C2675c.m12587c(r0, r3, r14);
        goto L_0x00aa;
    L_0x00df:
        r2 = r2 + r4;
        r6 = 15;
        if (r4 < r6) goto L_0x00ff;
    L_0x00e4:
        r0 = r21;
        r6 = org.telegram.customization.compression.p160a.C2675c.m12584b(r0, r3);
        r6 = r6 | 15;
        r0 = r21;
        org.telegram.customization.compression.p160a.C2675c.m12587c(r0, r3, r6);
        r3 = r4 + -15;
        r0 = r21;
        r3 = org.telegram.customization.compression.lz4.LZ4SafeUtils.writeLen(r3, r0, r5);
    L_0x00f9:
        if (r2 <= r12) goto L_0x010d;
    L_0x00fb:
        r6 = r3;
        r3 = r2;
        goto L_0x004a;
    L_0x00ff:
        r0 = r21;
        r6 = org.telegram.customization.compression.p160a.C2675c.m12584b(r0, r3);
        r4 = r4 | r6;
        r0 = r21;
        org.telegram.customization.compression.p160a.C2675c.m12587c(r0, r3, r4);
        r3 = r5;
        goto L_0x00f9;
    L_0x010d:
        r4 = r2 + -2;
        r0 = r18;
        r4 = org.telegram.customization.compression.p160a.C2675c.m12589e(r0, r4);
        r4 = org.telegram.customization.compression.lz4.LZ4Utils.hash(r4);
        r5 = r2 + -2;
        org.telegram.customization.compression.p160a.C2675c.m12582a(r13, r4, r5);
        r0 = r18;
        r4 = org.telegram.customization.compression.p160a.C2675c.m12589e(r0, r2);
        r4 = org.telegram.customization.compression.lz4.LZ4Utils.hash(r4);
        r5 = org.telegram.customization.compression.p160a.C2675c.m12577a(r13, r4);
        org.telegram.customization.compression.p160a.C2675c.m12582a(r13, r4, r2);
        r4 = r2 - r5;
        r6 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        if (r4 >= r6) goto L_0x013d;
    L_0x0135:
        r0 = r18;
        r6 = org.telegram.customization.compression.lz4.LZ4SafeUtils.readIntEquals(r0, r5, r2);
        if (r6 != 0) goto L_0x0141;
    L_0x013d:
        r4 = r2 + 1;
        goto L_0x0034;
    L_0x0141:
        r6 = r3 + 1;
        r8 = 0;
        r0 = r21;
        org.telegram.customization.compression.p160a.C2675c.m12587c(r0, r3, r8);
        r16 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r2;
        r2 = r16;
        goto L_0x00b6;
    L_0x0152:
        r6 = r8;
        r16 = r4;
        r4 = r5;
        r5 = r16;
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.customization.compression.lz4.LZ4JavaSafeCompressor.compress(byte[], int, int, byte[], int, int):int");
    }
}
