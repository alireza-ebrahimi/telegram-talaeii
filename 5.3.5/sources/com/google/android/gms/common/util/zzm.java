package com.google.android.gms.common.util;

import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

public final class zzm {
    private static final char[] zzgky = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] zzgkz = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String zzm(byte[] bArr) {
        int length = bArr.length;
        StringBuilder stringBuilder = new StringBuilder(length << 1);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(zzgky[(bArr[i] & PsExtractor.VIDEO_STREAM_MASK) >>> 4]);
            stringBuilder.append(zzgky[bArr[i] & 15]);
        }
        return stringBuilder.toString();
    }

    public static String zzn(byte[] bArr) {
        int i = 0;
        char[] cArr = new char[(bArr.length << 1)];
        int i2 = 0;
        while (i < bArr.length) {
            int i3 = bArr[i] & 255;
            int i4 = i2 + 1;
            cArr[i2] = zzgkz[i3 >>> 4];
            i2 = i4 + 1;
            cArr[i4] = zzgkz[i3 & 15];
            i++;
        }
        return new String(cArr);
    }
}
