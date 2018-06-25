package com.p054b.p055a;

import java.nio.ByteBuffer;
import org.telegram.ui.ActionBar.Theme;

/* renamed from: com.b.a.e */
public final class C1290e {
    /* renamed from: a */
    public static int m6666a(byte b) {
        return b < (byte) 0 ? b + 256 : b;
    }

    /* renamed from: a */
    public static long m6667a(ByteBuffer byteBuffer) {
        long j = (long) byteBuffer.getInt();
        return j < 0 ? j + 4294967296L : j;
    }

    /* renamed from: a */
    public static String m6668a(ByteBuffer byteBuffer, int i) {
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return C1293h.m6690a(bArr);
    }

    /* renamed from: b */
    public static int m6669b(ByteBuffer byteBuffer) {
        return (0 + (C1290e.m6670c(byteBuffer) << 8)) + C1290e.m6666a(byteBuffer.get());
    }

    /* renamed from: c */
    public static int m6670c(ByteBuffer byteBuffer) {
        return (0 + (C1290e.m6666a(byteBuffer.get()) << 8)) + C1290e.m6666a(byteBuffer.get());
    }

    /* renamed from: d */
    public static int m6671d(ByteBuffer byteBuffer) {
        return C1290e.m6666a(byteBuffer.get());
    }

    /* renamed from: e */
    public static long m6672e(ByteBuffer byteBuffer) {
        long a = (C1290e.m6667a(byteBuffer) << 32) + 0;
        if (a >= 0) {
            return a + C1290e.m6667a(byteBuffer);
        }
        throw new RuntimeException("I don't know how to deal with UInt64! long is not sufficient and I don't want to use BigInt");
    }

    /* renamed from: f */
    public static double m6673f(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[4];
        byteBuffer.get(bArr);
        return ((double) ((bArr[3] & 255) | (((((bArr[0] << 24) & Theme.ACTION_BAR_VIDEO_EDIT_COLOR) | 0) | ((bArr[1] << 16) & 16711680)) | ((bArr[2] << 8) & 65280)))) / 65536.0d;
    }

    /* renamed from: g */
    public static double m6674g(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[4];
        byteBuffer.get(bArr);
        return ((double) ((bArr[3] & 255) | (((((bArr[0] << 24) & Theme.ACTION_BAR_VIDEO_EDIT_COLOR) | 0) | ((bArr[1] << 16) & 16711680)) | ((bArr[2] << 8) & 65280)))) / 1.073741824E9d;
    }

    /* renamed from: h */
    public static float m6675h(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[2];
        byteBuffer.get(bArr);
        return ((float) ((short) ((bArr[1] & 255) | ((short) (((bArr[0] << 8) & 65280) | 0))))) / 256.0f;
    }

    /* renamed from: i */
    public static String m6676i(ByteBuffer byteBuffer) {
        int c = C1290e.m6670c(byteBuffer);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            stringBuilder.append((char) (((c >> ((2 - i) * 5)) & 31) + 96));
        }
        return stringBuilder.toString();
    }

    /* renamed from: j */
    public static String m6677j(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[4];
        byteBuffer.get(bArr);
        try {
            return new String(bArr, "ISO-8859-1");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
