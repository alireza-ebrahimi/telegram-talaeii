package com.crashlytics.android.p066c;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: com.crashlytics.android.c.b */
final class C1395b {
    /* renamed from: a */
    public static final C1395b f4234a = new C1395b(new byte[0]);
    /* renamed from: b */
    private final byte[] f4235b;
    /* renamed from: c */
    private volatile int f4236c = 0;

    private C1395b(byte[] bArr) {
        this.f4235b = bArr;
    }

    /* renamed from: a */
    public static C1395b m7039a(String str) {
        try {
            return new C1395b(str.getBytes(C3446C.UTF8_NAME));
        } catch (Throwable e) {
            throw new RuntimeException("UTF-8 not supported.", e);
        }
    }

    /* renamed from: a */
    public static C1395b m7040a(byte[] bArr, int i, int i2) {
        Object obj = new byte[i2];
        System.arraycopy(bArr, i, obj, 0, i2);
        return new C1395b(obj);
    }

    /* renamed from: a */
    public int m7041a() {
        return this.f4235b.length;
    }

    /* renamed from: a */
    public void m7042a(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.f4235b, i, bArr, i2, i3);
    }

    /* renamed from: b */
    public InputStream m7043b() {
        return new ByteArrayInputStream(this.f4235b);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C1395b)) {
            return false;
        }
        C1395b c1395b = (C1395b) obj;
        int length = this.f4235b.length;
        if (length != c1395b.f4235b.length) {
            return false;
        }
        byte[] bArr = this.f4235b;
        byte[] bArr2 = c1395b.f4235b;
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = this.f4236c;
        if (i == 0) {
            byte[] bArr = this.f4235b;
            int length = this.f4235b.length;
            int i2 = 0;
            i = length;
            while (i2 < length) {
                int i3 = bArr[i2] + (i * 31);
                i2++;
                i = i3;
            }
            if (i == 0) {
                i = 1;
            }
            this.f4236c = i;
        }
        return i;
    }
}
