package com.persianswitch.p126b;

import java.util.Arrays;

/* renamed from: com.persianswitch.b.q */
final class C2259q extends C2245f {
    /* renamed from: f */
    final transient byte[][] f6977f;
    /* renamed from: g */
    final transient int[] f6978g;

    C2259q(C2244c c2244c, int i) {
        int i2 = 0;
        super(null);
        C2261u.m10423a(c2244c.f6936b, 0, (long) i);
        C2257o c2257o = c2244c.f6935a;
        int i3 = 0;
        int i4 = 0;
        while (i4 < i) {
            if (c2257o.f6970c == c2257o.f6969b) {
                throw new AssertionError("s.limit == s.pos");
            }
            i4 += c2257o.f6970c - c2257o.f6969b;
            i3++;
            c2257o = c2257o.f6973f;
        }
        this.f6977f = new byte[i3][];
        this.f6978g = new int[(i3 * 2)];
        C2257o c2257o2 = c2244c.f6935a;
        i4 = 0;
        while (i2 < i) {
            this.f6977f[i4] = c2257o2.f6968a;
            int i5 = (c2257o2.f6970c - c2257o2.f6969b) + i2;
            if (i5 > i) {
                i5 = i;
            }
            this.f6978g[i4] = i5;
            this.f6978g[this.f6977f.length + i4] = c2257o2.f6969b;
            c2257o2.f6971d = true;
            i4++;
            c2257o2 = c2257o2.f6973f;
            i2 = i5;
        }
    }

    /* renamed from: b */
    private int m10405b(int i) {
        int binarySearch = Arrays.binarySearch(this.f6978g, 0, this.f6977f.length, i + 1);
        return binarySearch >= 0 ? binarySearch : binarySearch ^ -1;
    }

    /* renamed from: g */
    private C2245f m10406g() {
        return new C2245f(mo3218f());
    }

    /* renamed from: a */
    public byte mo3207a(int i) {
        C2261u.m10423a((long) this.f6978g[this.f6977f.length - 1], (long) i, 1);
        int b = m10405b(i);
        return this.f6977f[b][(i - (b == 0 ? 0 : this.f6978g[b - 1])) + this.f6978g[this.f6977f.length + b]];
    }

    /* renamed from: a */
    public C2245f mo3208a(int i, int i2) {
        return m10406g().mo3208a(i, i2);
    }

    /* renamed from: a */
    public String mo3209a() {
        return m10406g().mo3209a();
    }

    /* renamed from: a */
    void mo3210a(C2244c c2244c) {
        int i = 0;
        int length = this.f6977f.length;
        int i2 = 0;
        while (i < length) {
            int i3 = this.f6978g[length + i];
            int i4 = this.f6978g[i];
            C2257o c2257o = new C2257o(this.f6977f[i], i3, (i3 + i4) - i2);
            if (c2244c.f6935a == null) {
                c2257o.f6974g = c2257o;
                c2257o.f6973f = c2257o;
                c2244c.f6935a = c2257o;
            } else {
                c2244c.f6935a.f6974g.m10400a(c2257o);
            }
            i++;
            i2 = i4;
        }
        c2244c.f6936b = ((long) i2) + c2244c.f6936b;
    }

    /* renamed from: a */
    public boolean mo3211a(int i, C2245f c2245f, int i2, int i3) {
        if (i < 0 || i > mo3216e() - i3) {
            return false;
        }
        int b = m10405b(i);
        while (i3 > 0) {
            int i4 = b == 0 ? 0 : this.f6978g[b - 1];
            int min = Math.min(i3, ((this.f6978g[b] - i4) + i4) - i);
            if (!c2245f.mo3212a(i2, this.f6977f[b], (i - i4) + this.f6978g[this.f6977f.length + b], min)) {
                return false;
            }
            i += min;
            i2 += min;
            i3 -= min;
            b++;
        }
        return true;
    }

    /* renamed from: a */
    public boolean mo3212a(int i, byte[] bArr, int i2, int i3) {
        if (i < 0 || i > mo3216e() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int b = m10405b(i);
        while (i3 > 0) {
            int i4 = b == 0 ? 0 : this.f6978g[b - 1];
            int min = Math.min(i3, ((this.f6978g[b] - i4) + i4) - i);
            if (!C2261u.m10425a(this.f6977f[b], (i - i4) + this.f6978g[this.f6977f.length + b], bArr, i2, min)) {
                return false;
            }
            i += min;
            i2 += min;
            i3 -= min;
            b++;
        }
        return true;
    }

    /* renamed from: b */
    public String mo3213b() {
        return m10406g().mo3213b();
    }

    /* renamed from: c */
    public String mo3214c() {
        return m10406g().mo3214c();
    }

    /* renamed from: d */
    public C2245f mo3215d() {
        return m10406g().mo3215d();
    }

    /* renamed from: e */
    public int mo3216e() {
        return this.f6978g[this.f6977f.length - 1];
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        boolean z = (obj instanceof C2245f) && ((C2245f) obj).mo3216e() == mo3216e() && mo3211a(0, (C2245f) obj, 0, mo3216e());
        return z;
    }

    /* renamed from: f */
    public byte[] mo3218f() {
        int i = 0;
        Object obj = new byte[this.f6978g[this.f6977f.length - 1]];
        int length = this.f6977f.length;
        int i2 = 0;
        while (i < length) {
            int i3 = this.f6978g[length + i];
            int i4 = this.f6978g[i];
            System.arraycopy(this.f6977f[i], i3, obj, i2, i4 - i2);
            i++;
            i2 = i4;
        }
        return obj;
    }

    public int hashCode() {
        int i = this.d;
        if (i == 0) {
            i = 1;
            int length = this.f6977f.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                byte[] bArr = this.f6977f[i2];
                int i4 = this.f6978g[length + i2];
                int i5 = this.f6978g[i2];
                i3 = (i5 - i3) + i4;
                int i6 = i4;
                i4 = i;
                for (i = i6; i < i3; i++) {
                    i4 = (i4 * 31) + bArr[i];
                }
                i2++;
                i3 = i5;
                i = i4;
            }
            this.d = i;
        }
        return i;
    }

    public String toString() {
        return m10406g().toString();
    }
}
