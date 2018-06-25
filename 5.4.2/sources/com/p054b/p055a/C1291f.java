package com.p054b.p055a;

import java.nio.ByteBuffer;
import org.telegram.ui.ActionBar.Theme;

/* renamed from: com.b.a.f */
public final class C1291f {
    /* renamed from: a */
    static final /* synthetic */ boolean f3844a = (!C1291f.class.desiredAssertionStatus());

    /* renamed from: a */
    public static void m6678a(ByteBuffer byteBuffer, double d) {
        int i = (int) (65536.0d * d);
        byteBuffer.put((byte) ((Theme.ACTION_BAR_VIDEO_EDIT_COLOR & i) >> 24));
        byteBuffer.put((byte) ((16711680 & i) >> 16));
        byteBuffer.put((byte) ((65280 & i) >> 8));
        byteBuffer.put((byte) (i & 255));
    }

    /* renamed from: a */
    public static void m6679a(ByteBuffer byteBuffer, int i) {
        int i2 = 16777215 & i;
        C1291f.m6683b(byteBuffer, i2 >> 8);
        C1291f.m6687c(byteBuffer, i2);
    }

    /* renamed from: a */
    public static void m6680a(ByteBuffer byteBuffer, long j) {
        if (f3844a || j >= 0) {
            byteBuffer.putLong(j);
            return;
        }
        throw new AssertionError("The given long is negative");
    }

    /* renamed from: a */
    public static void m6681a(ByteBuffer byteBuffer, String str) {
        int i = 0;
        if (str.getBytes().length != 3) {
            throw new IllegalArgumentException("\"" + str + "\" language string isn't exactly 3 characters long!");
        }
        int i2 = 0;
        while (i < 3) {
            i2 += (str.getBytes()[i] - 96) << ((2 - i) * 5);
            i++;
        }
        C1291f.m6683b(byteBuffer, i2);
    }

    /* renamed from: b */
    public static void m6682b(ByteBuffer byteBuffer, double d) {
        int i = (int) (1.073741824E9d * d);
        byteBuffer.put((byte) ((Theme.ACTION_BAR_VIDEO_EDIT_COLOR & i) >> 24));
        byteBuffer.put((byte) ((16711680 & i) >> 16));
        byteBuffer.put((byte) ((65280 & i) >> 8));
        byteBuffer.put((byte) (i & 255));
    }

    /* renamed from: b */
    public static void m6683b(ByteBuffer byteBuffer, int i) {
        int i2 = 65535 & i;
        C1291f.m6687c(byteBuffer, i2 >> 8);
        C1291f.m6687c(byteBuffer, i2 & 255);
    }

    /* renamed from: b */
    public static void m6684b(ByteBuffer byteBuffer, long j) {
        if (f3844a || (j >= 0 && j <= 4294967296L)) {
            byteBuffer.putInt((int) j);
            return;
        }
        throw new AssertionError("The given long is not in the range of uint32 (" + j + ")");
    }

    /* renamed from: b */
    public static void m6685b(ByteBuffer byteBuffer, String str) {
        byteBuffer.put(C1293h.m6691a(str));
        C1291f.m6687c(byteBuffer, 0);
    }

    /* renamed from: c */
    public static void m6686c(ByteBuffer byteBuffer, double d) {
        short s = (short) ((int) (256.0d * d));
        byteBuffer.put((byte) ((65280 & s) >> 8));
        byteBuffer.put((byte) (s & 255));
    }

    /* renamed from: c */
    public static void m6687c(ByteBuffer byteBuffer, int i) {
        byteBuffer.put((byte) (i & 255));
    }
}
