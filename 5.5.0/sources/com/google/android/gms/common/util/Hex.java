package com.google.android.gms.common.util;

import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

public class Hex {
    private static final char[] zzaaa = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] zzzz = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String bytesToColonDelimitedStringLowercase(byte[] bArr) {
        int i = 0;
        if (bArr.length == 0) {
            return new String();
        }
        int i2;
        char[] cArr = new char[((bArr.length * 3) - 1)];
        int i3 = 0;
        while (i < bArr.length - 1) {
            i2 = bArr[i] & 255;
            int i4 = i3 + 1;
            cArr[i3] = zzaaa[i2 >>> 4];
            int i5 = i4 + 1;
            cArr[i4] = zzaaa[i2 & 15];
            i3 = i5 + 1;
            cArr[i5] = ':';
            i++;
        }
        i = bArr[bArr.length - 1] & 255;
        i2 = i3 + 1;
        cArr[i3] = zzaaa[i >>> 4];
        cArr[i2] = zzaaa[i & 15];
        return new String(cArr);
    }

    public static String bytesToColonDelimitedStringUppercase(byte[] bArr) {
        int i = 0;
        if (bArr.length == 0) {
            return new String();
        }
        int i2;
        char[] cArr = new char[((bArr.length * 3) - 1)];
        int i3 = 0;
        while (i < bArr.length - 1) {
            i2 = bArr[i] & 255;
            int i4 = i3 + 1;
            cArr[i3] = zzzz[i2 >>> 4];
            int i5 = i4 + 1;
            cArr[i4] = zzzz[i2 & 15];
            i3 = i5 + 1;
            cArr[i5] = ':';
            i++;
        }
        i = bArr[bArr.length - 1] & 255;
        i2 = i3 + 1;
        cArr[i3] = zzzz[i >>> 4];
        cArr[i2] = zzzz[i & 15];
        return new String(cArr);
    }

    public static String bytesToStringLowercase(byte[] bArr) {
        int i = 0;
        char[] cArr = new char[(bArr.length << 1)];
        int i2 = 0;
        while (i < bArr.length) {
            int i3 = bArr[i] & 255;
            int i4 = i2 + 1;
            cArr[i2] = zzaaa[i3 >>> 4];
            i2 = i4 + 1;
            cArr[i4] = zzaaa[i3 & 15];
            i++;
        }
        return new String(cArr);
    }

    public static String bytesToStringUppercase(byte[] bArr) {
        return bytesToStringUppercase(bArr, false);
    }

    public static String bytesToStringUppercase(byte[] bArr, boolean z) {
        int length = bArr.length;
        StringBuilder stringBuilder = new StringBuilder(length << 1);
        int i = 0;
        while (i < length && (!z || i != length - 1 || (bArr[i] & 255) != 0)) {
            stringBuilder.append(zzzz[(bArr[i] & PsExtractor.VIDEO_STREAM_MASK) >>> 4]);
            stringBuilder.append(zzzz[bArr[i] & 15]);
            i++;
        }
        return stringBuilder.toString();
    }

    public static byte[] colonDelimitedStringToBytes(String str) {
        return stringToBytes(str.replace(":", TtmlNode.ANONYMOUS_REGION_ID));
    }

    public static byte[] stringToBytes(String str) {
        int length = str.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Hex string has odd number of characters");
        }
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) Integer.parseInt(str.substring(i, i + 2), 16);
        }
        return bArr;
    }
}
