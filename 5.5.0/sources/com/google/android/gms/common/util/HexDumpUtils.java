package com.google.android.gms.common.util;

import java.io.ByteArrayOutputStream;
import java.util.StringTokenizer;
import org.telegram.messenger.exoplayer2.C3446C;

public final class HexDumpUtils {
    public static byte[] bytesFromString(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, " \t\n");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (stringTokenizer.hasMoreTokens()) {
            try {
                byteArrayOutputStream.write(Integer.parseInt(stringTokenizer.nextToken(), 16) & 255);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static String dump(byte[] bArr, int i, int i2, boolean z) {
        if (bArr == null || bArr.length == 0 || i < 0 || i2 <= 0 || i + i2 > bArr.length) {
            return null;
        }
        int i3 = 57;
        if (z) {
            i3 = 75;
        }
        StringBuilder stringBuilder = new StringBuilder(i3 * (((i2 + 16) - 1) / 16));
        int i4 = i;
        int i5 = i2;
        i3 = 0;
        int i6 = 0;
        while (i5 > 0) {
            if (i6 == 0) {
                if (i2 < C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) {
                    stringBuilder.append(String.format("%04X:", new Object[]{Integer.valueOf(i4)}));
                    i3 = i4;
                } else {
                    stringBuilder.append(String.format("%08X:", new Object[]{Integer.valueOf(i4)}));
                    i3 = i4;
                }
            } else if (i6 == 8) {
                stringBuilder.append(" -");
            }
            stringBuilder.append(String.format(" %02X", new Object[]{Integer.valueOf(bArr[i4] & 255)}));
            int i7 = i5 - 1;
            i6++;
            if (z && (i6 == 16 || i7 == 0)) {
                int i8 = 16 - i6;
                if (i8 > 0) {
                    for (i5 = 0; i5 < i8; i5++) {
                        stringBuilder.append("   ");
                    }
                }
                if (i8 >= 8) {
                    stringBuilder.append("  ");
                }
                stringBuilder.append("  ");
                for (i8 = 0; i8 < i6; i8++) {
                    char c = (char) bArr[i3 + i8];
                    if (c < ' ' || c > '~') {
                        c = '.';
                    }
                    stringBuilder.append(c);
                }
            }
            if (i6 == 16 || i7 == 0) {
                stringBuilder.append('\n');
                i5 = 0;
            } else {
                i5 = i6;
            }
            i4++;
            i6 = i5;
            i5 = i7;
        }
        return stringBuilder.toString();
    }
}
