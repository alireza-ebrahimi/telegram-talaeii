package com.persianswitch.p126b;

import java.io.Serializable;
import java.util.Arrays;

/* renamed from: com.persianswitch.b.f */
public class C2245f implements Serializable, Comparable<C2245f> {
    /* renamed from: a */
    static final char[] f6937a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /* renamed from: b */
    public static final C2245f f6938b = C2245f.m10319a(new byte[0]);
    /* renamed from: c */
    final byte[] f6939c;
    /* renamed from: d */
    transient int f6940d;
    /* renamed from: e */
    transient String f6941e;

    C2245f(byte[] bArr) {
        this.f6939c = bArr;
    }

    /* renamed from: a */
    static int m10317a(String str, int i) {
        int i2 = 0;
        int length = str.length();
        int i3 = 0;
        while (i2 < length) {
            if (i3 == i) {
                return i2;
            }
            int codePointAt = str.codePointAt(i2);
            if ((Character.isISOControl(codePointAt) && codePointAt != 10 && codePointAt != 13) || codePointAt == 65533) {
                return -1;
            }
            i3++;
            i2 += Character.charCount(codePointAt);
        }
        return str.length();
    }

    /* renamed from: a */
    public static C2245f m10318a(String str) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        }
        C2245f c2245f = new C2245f(str.getBytes(C2261u.f6979a));
        c2245f.f6941e = str;
        return c2245f;
    }

    /* renamed from: a */
    public static C2245f m10319a(byte... bArr) {
        if (bArr != null) {
            return new C2245f((byte[]) bArr.clone());
        }
        throw new IllegalArgumentException("data == null");
    }

    /* renamed from: a */
    public byte mo3207a(int i) {
        return this.f6939c[i];
    }

    /* renamed from: a */
    public int m10321a(C2245f c2245f) {
        int e = mo3216e();
        int e2 = c2245f.mo3216e();
        int min = Math.min(e, e2);
        int i = 0;
        while (i < min) {
            int a = mo3207a(i) & 255;
            int a2 = c2245f.mo3207a(i) & 255;
            if (a != a2) {
                return a < a2 ? -1 : 1;
            } else {
                i++;
            }
        }
        return e == e2 ? 0 : e >= e2 ? 1 : -1;
    }

    /* renamed from: a */
    public C2245f mo3208a(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("beginIndex < 0");
        } else if (i2 > this.f6939c.length) {
            throw new IllegalArgumentException("endIndex > length(" + this.f6939c.length + ")");
        } else {
            int i3 = i2 - i;
            if (i3 < 0) {
                throw new IllegalArgumentException("endIndex < beginIndex");
            } else if (i == 0 && i2 == this.f6939c.length) {
                return this;
            } else {
                Object obj = new byte[i3];
                System.arraycopy(this.f6939c, i, obj, 0, i3);
                return new C2245f(obj);
            }
        }
    }

    /* renamed from: a */
    public String mo3209a() {
        String str = this.f6941e;
        if (str != null) {
            return str;
        }
        str = new String(this.f6939c, C2261u.f6979a);
        this.f6941e = str;
        return str;
    }

    /* renamed from: a */
    void mo3210a(C2244c c2244c) {
        c2244c.m10277b(this.f6939c, 0, this.f6939c.length);
    }

    /* renamed from: a */
    public boolean mo3211a(int i, C2245f c2245f, int i2, int i3) {
        return c2245f.mo3212a(i2, this.f6939c, i, i3);
    }

    /* renamed from: a */
    public boolean mo3212a(int i, byte[] bArr, int i2, int i3) {
        return i >= 0 && i <= this.f6939c.length - i3 && i2 >= 0 && i2 <= bArr.length - i3 && C2261u.m10425a(this.f6939c, i, bArr, i2, i3);
    }

    /* renamed from: b */
    public String mo3213b() {
        return C2240b.m10229a(this.f6939c);
    }

    /* renamed from: c */
    public String mo3214c() {
        int i = 0;
        char[] cArr = new char[(this.f6939c.length * 2)];
        byte[] bArr = this.f6939c;
        int length = bArr.length;
        int i2 = 0;
        while (i < length) {
            byte b = bArr[i];
            int i3 = i2 + 1;
            cArr[i2] = f6937a[(b >> 4) & 15];
            i2 = i3 + 1;
            cArr[i3] = f6937a[b & 15];
            i++;
        }
        return new String(cArr);
    }

    public /* synthetic */ int compareTo(Object obj) {
        return m10321a((C2245f) obj);
    }

    /* renamed from: d */
    public C2245f mo3215d() {
        int i = 0;
        while (i < this.f6939c.length) {
            byte b = this.f6939c[i];
            if (b < (byte) 65 || b > (byte) 90) {
                i++;
            } else {
                byte[] bArr = (byte[]) this.f6939c.clone();
                int i2 = i + 1;
                bArr[i] = (byte) (b + 32);
                for (i = i2; i < bArr.length; i++) {
                    byte b2 = bArr[i];
                    if (b2 >= (byte) 65 && b2 <= (byte) 90) {
                        bArr[i] = (byte) (b2 + 32);
                    }
                }
                return new C2245f(bArr);
            }
        }
        return this;
    }

    /* renamed from: e */
    public int mo3216e() {
        return this.f6939c.length;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        boolean z = (obj instanceof C2245f) && ((C2245f) obj).mo3216e() == this.f6939c.length && ((C2245f) obj).mo3212a(0, this.f6939c, 0, this.f6939c.length);
        return z;
    }

    /* renamed from: f */
    public byte[] mo3218f() {
        return (byte[]) this.f6939c.clone();
    }

    public int hashCode() {
        int i = this.f6940d;
        if (i != 0) {
            return i;
        }
        i = Arrays.hashCode(this.f6939c);
        this.f6940d = i;
        return i;
    }

    public String toString() {
        if (this.f6939c.length == 0) {
            return "[size=0]";
        }
        String a = mo3209a();
        int a2 = C2245f.m10317a(a, 64);
        if (a2 == -1) {
            return this.f6939c.length <= 64 ? "[hex=" + mo3214c() + "]" : "[size=" + this.f6939c.length + " hex=" + mo3208a(0, 64).mo3214c() + "…]";
        } else {
            String replace = a.substring(0, a2).replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r");
            return a2 < a.length() ? "[size=" + this.f6939c.length + " text=" + replace + "…]" : "[text=" + replace + "]";
        }
    }
}
