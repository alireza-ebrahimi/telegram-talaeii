package com.crashlytics.android.p066c;

import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: com.crashlytics.android.c.e */
final class C1400e implements Flushable {
    /* renamed from: a */
    private final byte[] f4244a;
    /* renamed from: b */
    private final int f4245b;
    /* renamed from: c */
    private int f4246c = 0;
    /* renamed from: d */
    private final OutputStream f4247d;

    /* renamed from: com.crashlytics.android.c.e$a */
    static class C1399a extends IOException {
        C1399a() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }
    }

    private C1400e(OutputStream outputStream, byte[] bArr) {
        this.f4247d = outputStream;
        this.f4244a = bArr;
        this.f4245b = bArr.length;
    }

    /* renamed from: a */
    public static C1400e m7050a(OutputStream outputStream) {
        return C1400e.m7051a(outputStream, 4096);
    }

    /* renamed from: a */
    public static C1400e m7051a(OutputStream outputStream, int i) {
        return new C1400e(outputStream, new byte[i]);
    }

    /* renamed from: a */
    private void m7052a() {
        if (this.f4247d == null) {
            throw new C1399a();
        }
        this.f4247d.write(this.f4244a, 0, this.f4246c);
        this.f4246c = 0;
    }

    /* renamed from: b */
    public static int m7053b(float f) {
        return 4;
    }

    /* renamed from: b */
    public static int m7054b(int i, float f) {
        return C1400e.m7069j(i) + C1400e.m7053b(f);
    }

    /* renamed from: b */
    public static int m7055b(int i, long j) {
        return C1400e.m7069j(i) + C1400e.m7058b(j);
    }

    /* renamed from: b */
    public static int m7056b(int i, C1395b c1395b) {
        return C1400e.m7069j(i) + C1400e.m7059b(c1395b);
    }

    /* renamed from: b */
    public static int m7057b(int i, boolean z) {
        return C1400e.m7069j(i) + C1400e.m7060b(z);
    }

    /* renamed from: b */
    public static int m7058b(long j) {
        return C1400e.m7062d(j);
    }

    /* renamed from: b */
    public static int m7059b(C1395b c1395b) {
        return C1400e.m7070l(c1395b.m7041a()) + c1395b.m7041a();
    }

    /* renamed from: b */
    public static int m7060b(boolean z) {
        return 1;
    }

    /* renamed from: d */
    public static int m7061d(int i, int i2) {
        return C1400e.m7069j(i) + C1400e.m7065f(i2);
    }

    /* renamed from: d */
    public static int m7062d(long j) {
        return (-128 & j) == 0 ? 1 : (-16384 & j) == 0 ? 2 : (-2097152 & j) == 0 ? 3 : (-268435456 & j) == 0 ? 4 : (-34359738368L & j) == 0 ? 5 : (-4398046511104L & j) == 0 ? 6 : (-562949953421312L & j) == 0 ? 7 : (-72057594037927936L & j) == 0 ? 8 : (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    /* renamed from: e */
    public static int m7063e(int i) {
        return i >= 0 ? C1400e.m7070l(i) : 10;
    }

    /* renamed from: e */
    public static int m7064e(int i, int i2) {
        return C1400e.m7069j(i) + C1400e.m7067g(i2);
    }

    /* renamed from: f */
    public static int m7065f(int i) {
        return C1400e.m7070l(i);
    }

    /* renamed from: f */
    public static int m7066f(int i, int i2) {
        return C1400e.m7069j(i) + C1400e.m7068h(i2);
    }

    /* renamed from: g */
    public static int m7067g(int i) {
        return C1400e.m7063e(i);
    }

    /* renamed from: h */
    public static int m7068h(int i) {
        return C1400e.m7070l(C1400e.m7071n(i));
    }

    /* renamed from: j */
    public static int m7069j(int i) {
        return C1400e.m7070l(an.m7038a(i, 0));
    }

    /* renamed from: l */
    public static int m7070l(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (-268435456 & i) == 0 ? 4 : 5;
    }

    /* renamed from: n */
    public static int m7071n(int i) {
        return (i << 1) ^ (i >> 31);
    }

    /* renamed from: a */
    public void m7072a(byte b) {
        if (this.f4246c == this.f4245b) {
            m7052a();
        }
        byte[] bArr = this.f4244a;
        int i = this.f4246c;
        this.f4246c = i + 1;
        bArr[i] = b;
    }

    /* renamed from: a */
    public void m7073a(float f) {
        m7096m(Float.floatToRawIntBits(f));
    }

    /* renamed from: a */
    public void m7074a(int i) {
        if (i >= 0) {
            m7095k(i);
        } else {
            m7090c((long) i);
        }
    }

    /* renamed from: a */
    public void m7075a(int i, float f) {
        m7093g(i, 5);
        m7073a(f);
    }

    /* renamed from: a */
    public void m7076a(int i, int i2) {
        m7093g(i, 0);
        m7086b(i2);
    }

    /* renamed from: a */
    public void m7077a(int i, long j) {
        m7093g(i, 0);
        m7080a(j);
    }

    /* renamed from: a */
    public void m7078a(int i, C1395b c1395b) {
        m7093g(i, 2);
        m7081a(c1395b);
    }

    /* renamed from: a */
    public void m7079a(int i, boolean z) {
        m7093g(i, 0);
        m7083a(z);
    }

    /* renamed from: a */
    public void m7080a(long j) {
        m7090c(j);
    }

    /* renamed from: a */
    public void m7081a(C1395b c1395b) {
        m7095k(c1395b.m7041a());
        m7091c(c1395b);
    }

    /* renamed from: a */
    public void m7082a(C1395b c1395b, int i, int i2) {
        if (this.f4245b - this.f4246c >= i2) {
            c1395b.m7042a(this.f4244a, i, this.f4246c, i2);
            this.f4246c += i2;
            return;
        }
        int i3 = this.f4245b - this.f4246c;
        c1395b.m7042a(this.f4244a, i, this.f4246c, i3);
        int i4 = i + i3;
        i3 = i2 - i3;
        this.f4246c = this.f4245b;
        m7052a();
        if (i3 <= this.f4245b) {
            c1395b.m7042a(this.f4244a, i4, 0, i3);
            this.f4246c = i3;
            return;
        }
        InputStream b = c1395b.m7043b();
        if (((long) i4) != b.skip((long) i4)) {
            throw new IllegalStateException("Skip failed.");
        }
        while (i3 > 0) {
            i4 = Math.min(i3, this.f4245b);
            int read = b.read(this.f4244a, 0, i4);
            if (read != i4) {
                throw new IllegalStateException("Read failed.");
            }
            this.f4247d.write(this.f4244a, 0, read);
            i3 -= read;
        }
    }

    /* renamed from: a */
    public void m7083a(boolean z) {
        m7094i(z ? 1 : 0);
    }

    /* renamed from: a */
    public void m7084a(byte[] bArr) {
        m7085a(bArr, 0, bArr.length);
    }

    /* renamed from: a */
    public void m7085a(byte[] bArr, int i, int i2) {
        if (this.f4245b - this.f4246c >= i2) {
            System.arraycopy(bArr, i, this.f4244a, this.f4246c, i2);
            this.f4246c += i2;
            return;
        }
        int i3 = this.f4245b - this.f4246c;
        System.arraycopy(bArr, i, this.f4244a, this.f4246c, i3);
        int i4 = i + i3;
        i3 = i2 - i3;
        this.f4246c = this.f4245b;
        m7052a();
        if (i3 <= this.f4245b) {
            System.arraycopy(bArr, i4, this.f4244a, 0, i3);
            this.f4246c = i3;
            return;
        }
        this.f4247d.write(bArr, i4, i3);
    }

    /* renamed from: b */
    public void m7086b(int i) {
        m7095k(i);
    }

    /* renamed from: b */
    public void m7087b(int i, int i2) {
        m7093g(i, 0);
        m7088c(i2);
    }

    /* renamed from: c */
    public void m7088c(int i) {
        m7074a(i);
    }

    /* renamed from: c */
    public void m7089c(int i, int i2) {
        m7093g(i, 0);
        m7092d(i2);
    }

    /* renamed from: c */
    public void m7090c(long j) {
        while ((-128 & j) != 0) {
            m7094i((((int) j) & 127) | 128);
            j >>>= 7;
        }
        m7094i((int) j);
    }

    /* renamed from: c */
    public void m7091c(C1395b c1395b) {
        m7082a(c1395b, 0, c1395b.m7041a());
    }

    /* renamed from: d */
    public void m7092d(int i) {
        m7095k(C1400e.m7071n(i));
    }

    public void flush() {
        if (this.f4247d != null) {
            m7052a();
        }
    }

    /* renamed from: g */
    public void m7093g(int i, int i2) {
        m7095k(an.m7038a(i, i2));
    }

    /* renamed from: i */
    public void m7094i(int i) {
        m7072a((byte) i);
    }

    /* renamed from: k */
    public void m7095k(int i) {
        while ((i & -128) != 0) {
            m7094i((i & 127) | 128);
            i >>>= 7;
        }
        m7094i(i);
    }

    /* renamed from: m */
    public void m7096m(int i) {
        m7094i(i & 255);
        m7094i((i >> 8) & 255);
        m7094i((i >> 16) & 255);
        m7094i((i >> 24) & 255);
    }
}
