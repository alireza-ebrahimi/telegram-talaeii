package com.persianswitch.p126b;

import java.nio.charset.Charset;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.ui.ActionBar.Theme;

/* renamed from: com.persianswitch.b.u */
final class C2261u {
    /* renamed from: a */
    public static final Charset f6979a = Charset.forName(C3446C.UTF8_NAME);

    /* renamed from: a */
    public static int m10421a(int i) {
        return ((((Theme.ACTION_BAR_VIDEO_EDIT_COLOR & i) >>> 24) | ((16711680 & i) >>> 8)) | ((65280 & i) << 8)) | ((i & 255) << 24);
    }

    /* renamed from: a */
    public static short m10422a(short s) {
        int i = 65535 & s;
        return (short) (((i & 255) << 8) | ((65280 & i) >>> 8));
    }

    /* renamed from: a */
    public static void m10423a(long j, long j2, long j3) {
        if ((j2 | j3) < 0 || j2 > j || j - j2 < j3) {
            throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", new Object[]{Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3)}));
        }
    }

    /* renamed from: a */
    public static void m10424a(Throwable th) {
        C2261u.m10426b(th);
    }

    /* renamed from: a */
    public static boolean m10425a(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            if (bArr[i4 + i] != bArr2[i4 + i2]) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: b */
    private static <T extends Throwable> void m10426b(Throwable th) {
        throw th;
    }
}
