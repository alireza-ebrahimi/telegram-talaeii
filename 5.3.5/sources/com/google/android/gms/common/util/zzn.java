package com.google.android.gms.common.util;

public final class zzn {
    public static String zza(byte[] bArr, int i, int i2, boolean z) {
        if (bArr == null || bArr.length == 0 || i2 <= 0 || i2 > bArr.length) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder((((i2 + 16) - 1) / 16) * 57);
        int i3 = 0;
        int i4 = i2;
        int i5 = 0;
        while (i4 > 0) {
            if (i5 == 0) {
                if (i2 < 65536) {
                    stringBuilder.append(String.format("%04X:", new Object[]{Integer.valueOf(i3)}));
                } else {
                    stringBuilder.append(String.format("%08X:", new Object[]{Integer.valueOf(i3)}));
                }
            } else if (i5 == 8) {
                stringBuilder.append(" -");
            }
            stringBuilder.append(String.format(" %02X", new Object[]{Integer.valueOf(bArr[i3] & 255)}));
            int i6 = i4 - 1;
            i4 = i5 + 1;
            if (i4 == 16 || i6 == 0) {
                stringBuilder.append('\n');
                i4 = 0;
            }
            i3++;
            i5 = i4;
            i4 = i6;
        }
        return stringBuilder.toString();
    }
}
