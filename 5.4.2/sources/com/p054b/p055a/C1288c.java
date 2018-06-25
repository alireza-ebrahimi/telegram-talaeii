package com.p054b.p055a;

import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

/* renamed from: com.b.a.c */
public class C1288c {
    /* renamed from: a */
    private static final char[] f3842a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: a */
    public static String m6663a(byte[] bArr) {
        return C1288c.m6664a(bArr, 0);
    }

    /* renamed from: a */
    public static String m6664a(byte[] bArr, int i) {
        int i2 = 0;
        int length = bArr.length;
        char[] cArr = new char[((i > 0 ? length / i : 0) + (length << 1))];
        int i3 = 0;
        while (i3 < length) {
            int i4;
            if (i <= 0 || i3 % i != 0 || i2 <= 0) {
                i4 = i2;
            } else {
                i4 = i2 + 1;
                cArr[i2] = '-';
            }
            int i5 = i4 + 1;
            cArr[i4] = f3842a[(bArr[i3] & PsExtractor.VIDEO_STREAM_MASK) >>> 4];
            i2 = i5 + 1;
            cArr[i5] = f3842a[bArr[i3] & 15];
            i3++;
        }
        return new String(cArr);
    }
}
