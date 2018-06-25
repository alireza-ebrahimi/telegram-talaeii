package com.persianswitch.sdk.base.utils.pdate;

import java.util.Locale;

public class Roozh {
    /* renamed from: a */
    private int f7123a;
    /* renamed from: b */
    private int f7124b;
    /* renamed from: c */
    private int f7125c;
    /* renamed from: d */
    private int f7126d;
    /* renamed from: e */
    private int f7127e;
    /* renamed from: f */
    private int f7128f;
    /* renamed from: g */
    private int f7129g;
    /* renamed from: h */
    private int f7130h;
    /* renamed from: i */
    private int f7131i;
    /* renamed from: j */
    private int f7132j;
    /* renamed from: k */
    private int f7133k;

    /* renamed from: a */
    private int m10788a(int i, int i2, int i3, int i4) {
        int i5 = (((((((i + 4800) + ((i2 - 14) / 12)) * 1461) / 4) + ((((i2 - 2) - (((i2 - 14) / 12) * 12)) * 367) / 12)) - (((((i + 4900) + ((i2 - 14) / 12)) / 100) * 3) / 4)) + i3) - 32075;
        return i4 == 0 ? (i5 - (((((100100 + i) + ((i2 - 8) / 6)) / 100) * 3) / 4)) + 752 : i5;
    }

    /* renamed from: a */
    private void m10789a(int i) {
        m10790a(i, 0);
        this.f7126d = this.f7129g - 621;
        m10791b(this.f7126d);
        int a = i - m10788a(this.f7129g, 3, this.f7133k, 0);
        if (a < 0) {
            this.f7126d--;
            a += 179;
            if (this.f7132j == 1) {
                a++;
            }
        } else if (a <= 185) {
            this.f7127e = (a / 31) + 1;
            this.f7128f = (a % 31) + 1;
            return;
        } else {
            a -= 186;
        }
        this.f7127e = (a / 30) + 7;
        this.f7128f = (a % 30) + 1;
    }

    /* renamed from: a */
    private void m10790a(int i, int i2) {
        int i3 = (i * 4) + 139361631;
        if (i2 == 0) {
            i3 = (i3 + ((((((i * 4) + 183187720) / 146097) * 3) / 4) * 4)) - 3908;
        }
        int i4 = (((i3 % 1461) / 4) * 5) + 308;
        this.f7131i = ((i4 % 153) / 5) + 1;
        this.f7130h = ((i4 / 153) % 12) + 1;
        this.f7129g = ((i3 / 1461) - 100100) + ((8 - this.f7130h) / 6);
    }

    /* renamed from: b */
    private void m10791b(int i) {
        this.f7133k = 0;
        this.f7132j = 0;
        int[] iArr = new int[]{-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
        this.f7129g = i + 621;
        int i2 = iArr[0];
        int i3 = 1;
        int i4 = -14;
        while (i3 <= 19) {
            int i5 = iArr[i3];
            int i6 = i5 - i2;
            if (i < i5) {
                i2 = i - i2;
                i3 = (((i2 / 33) * 8) + i4) + (((i2 % 33) + 3) / 4);
                if (i6 % 33 == 4 && i6 - i2 == 4) {
                    i3++;
                }
                this.f7133k = (i3 + 20) - (((this.f7129g / 4) - ((((this.f7129g / 100) + 1) * 3) / 4)) - 150);
                this.f7132j = ((((i6 - i2 < 6 ? (i2 - i6) + (((i6 + 4) / 33) * 33) : i2) + 1) % 33) - 1) % 4;
                if (this.f7132j == -1) {
                    this.f7132j = 4;
                    return;
                }
                return;
            }
            i3++;
            i4 = (((i6 / 33) * 8) + i4) + ((i6 % 33) / 4);
            i2 = i5;
        }
    }

    /* renamed from: a */
    public int m10792a() {
        return this.f7123a;
    }

    /* renamed from: a */
    public void m10793a(int i, int i2, int i3) {
        m10789a(m10788a(i, i2, i3, 0));
        this.f7125c = this.f7126d;
        this.f7124b = this.f7127e;
        this.f7123a = this.f7128f;
    }

    /* renamed from: b */
    public int m10794b() {
        return this.f7124b;
    }

    /* renamed from: c */
    public int m10795c() {
        return this.f7125c;
    }

    public String toString() {
        return String.format(Locale.US, "%04d-%02d-%02d", new Object[]{Integer.valueOf(m10795c()), Integer.valueOf(m10794b()), Integer.valueOf(m10792a())});
    }
}
