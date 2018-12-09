package com.persianswitch.p122a.p123a.p125a;

import java.util.Arrays;

/* renamed from: com.persianswitch.a.a.a.n */
public final class C2122n {
    /* renamed from: a */
    private int f6434a;
    /* renamed from: b */
    private int f6435b;
    /* renamed from: c */
    private int f6436c;
    /* renamed from: d */
    private final int[] f6437d = new int[10];

    /* renamed from: a */
    C2122n m9572a(int i, int i2, int i3) {
        if (i < this.f6437d.length) {
            int i4 = 1 << i;
            this.f6434a |= i4;
            if ((i2 & 1) != 0) {
                this.f6435b |= i4;
            } else {
                this.f6435b &= i4 ^ -1;
            }
            if ((i2 & 2) != 0) {
                this.f6436c = i4 | this.f6436c;
            } else {
                this.f6436c = (i4 ^ -1) & this.f6436c;
            }
            this.f6437d[i] = i3;
        }
        return this;
    }

    /* renamed from: a */
    void m9573a() {
        this.f6436c = 0;
        this.f6435b = 0;
        this.f6434a = 0;
        Arrays.fill(this.f6437d, 0);
    }

    /* renamed from: a */
    void m9574a(C2122n c2122n) {
        for (int i = 0; i < 10; i++) {
            if (c2122n.m9575a(i)) {
                m9572a(i, c2122n.m9579c(i), c2122n.m9577b(i));
            }
        }
    }

    /* renamed from: a */
    boolean m9575a(int i) {
        return ((1 << i) & this.f6434a) != 0;
    }

    /* renamed from: b */
    int m9576b() {
        return Integer.bitCount(this.f6434a);
    }

    /* renamed from: b */
    int m9577b(int i) {
        return this.f6437d[i];
    }

    /* renamed from: c */
    int m9578c() {
        return (2 & this.f6434a) != 0 ? this.f6437d[1] : -1;
    }

    /* renamed from: c */
    int m9579c(int i) {
        int i2 = 0;
        if (m9584h(i)) {
            i2 = 2;
        }
        return m9583g(i) ? i2 | 1 : i2;
    }

    /* renamed from: d */
    int m9580d(int i) {
        return (16 & this.f6434a) != 0 ? this.f6437d[4] : i;
    }

    /* renamed from: e */
    int m9581e(int i) {
        return (32 & this.f6434a) != 0 ? this.f6437d[5] : i;
    }

    /* renamed from: f */
    int m9582f(int i) {
        return (128 & this.f6434a) != 0 ? this.f6437d[7] : i;
    }

    /* renamed from: g */
    boolean m9583g(int i) {
        return ((1 << i) & this.f6435b) != 0;
    }

    /* renamed from: h */
    boolean m9584h(int i) {
        return ((1 << i) & this.f6436c) != 0;
    }
}
