package org.telegram.customization.compression.p160a;

import java.nio.ByteOrder;

/* renamed from: org.telegram.customization.compression.a.c */
public enum C2675c {
    ;

    /* renamed from: a */
    public static int m12577a(int[] iArr, int i) {
        return iArr[i];
    }

    /* renamed from: a */
    public static int m12578a(short[] sArr, int i) {
        return sArr[i] & 65535;
    }

    /* renamed from: a */
    public static void m12579a(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("lengths must be >= 0");
        }
    }

    /* renamed from: a */
    public static void m12580a(byte[] bArr, int i) {
        if (i < 0 || i >= bArr.length) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
    }

    /* renamed from: a */
    public static void m12581a(byte[] bArr, int i, int i2) {
        C2675c.m12579a(i2);
        if (i2 > 0) {
            C2675c.m12580a(bArr, i);
            C2675c.m12580a(bArr, (i + i2) - 1);
        }
    }

    /* renamed from: a */
    public static void m12582a(int[] iArr, int i, int i2) {
        iArr[i] = i2;
    }

    /* renamed from: a */
    public static void m12583a(short[] sArr, int i, int i2) {
        sArr[i] = (short) i2;
    }

    /* renamed from: b */
    public static byte m12584b(byte[] bArr, int i) {
        return bArr[i];
    }

    /* renamed from: b */
    public static void m12585b(byte[] bArr, int i, int i2) {
        int i3 = i + 1;
        bArr[i] = (byte) i2;
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i2 >>> 8);
    }

    /* renamed from: c */
    public static int m12586c(byte[] bArr, int i) {
        return ((((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16)) | ((bArr[i + 2] & 255) << 8)) | (bArr[i + 3] & 255);
    }

    /* renamed from: c */
    public static void m12587c(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) i2;
    }

    /* renamed from: d */
    public static int m12588d(byte[] bArr, int i) {
        return (((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i + 2] & 255) << 16)) | ((bArr[i + 3] & 255) << 24);
    }

    /* renamed from: e */
    public static int m12589e(byte[] bArr, int i) {
        return C2676d.f8921a == ByteOrder.BIG_ENDIAN ? C2675c.m12586c(bArr, i) : C2675c.m12588d(bArr, i);
    }

    /* renamed from: f */
    public static int m12590f(byte[] bArr, int i) {
        return (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8);
    }
}
