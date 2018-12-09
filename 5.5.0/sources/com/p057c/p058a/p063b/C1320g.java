package com.p057c.p058a.p063b;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import java.nio.ByteBuffer;

/* renamed from: com.c.a.b.g */
public class C1320g {
    /* renamed from: j */
    public static final C1320g f3985j = new C1320g(1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    /* renamed from: k */
    public static final C1320g f3986k = new C1320g(0.0d, 1.0d, -1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    /* renamed from: l */
    public static final C1320g f3987l = new C1320g(-1.0d, 0.0d, 0.0d, -1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    /* renamed from: m */
    public static final C1320g f3988m = new C1320g(0.0d, -1.0d, 1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    /* renamed from: a */
    double f3989a;
    /* renamed from: b */
    double f3990b;
    /* renamed from: c */
    double f3991c;
    /* renamed from: d */
    double f3992d;
    /* renamed from: e */
    double f3993e;
    /* renamed from: f */
    double f3994f;
    /* renamed from: g */
    double f3995g;
    /* renamed from: h */
    double f3996h;
    /* renamed from: i */
    double f3997i;

    public C1320g(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        this.f3989a = d5;
        this.f3990b = d6;
        this.f3991c = d7;
        this.f3992d = d;
        this.f3993e = d2;
        this.f3994f = d3;
        this.f3995g = d4;
        this.f3996h = d8;
        this.f3997i = d9;
    }

    /* renamed from: a */
    public static C1320g m6762a(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        return new C1320g(d, d2, d4, d5, d3, d6, d9, d7, d8);
    }

    /* renamed from: a */
    public static C1320g m6763a(ByteBuffer byteBuffer) {
        return C1320g.m6762a(C1290e.m6673f(byteBuffer), C1290e.m6673f(byteBuffer), C1290e.m6674g(byteBuffer), C1290e.m6673f(byteBuffer), C1290e.m6673f(byteBuffer), C1290e.m6674g(byteBuffer), C1290e.m6673f(byteBuffer), C1290e.m6673f(byteBuffer), C1290e.m6674g(byteBuffer));
    }

    /* renamed from: b */
    public void m6764b(ByteBuffer byteBuffer) {
        C1291f.m6678a(byteBuffer, this.f3992d);
        C1291f.m6678a(byteBuffer, this.f3993e);
        C1291f.m6682b(byteBuffer, this.f3989a);
        C1291f.m6678a(byteBuffer, this.f3994f);
        C1291f.m6678a(byteBuffer, this.f3995g);
        C1291f.m6682b(byteBuffer, this.f3990b);
        C1291f.m6678a(byteBuffer, this.f3996h);
        C1291f.m6678a(byteBuffer, this.f3997i);
        C1291f.m6682b(byteBuffer, this.f3991c);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C1320g c1320g = (C1320g) obj;
        return Double.compare(c1320g.f3992d, this.f3992d) != 0 ? false : Double.compare(c1320g.f3993e, this.f3993e) != 0 ? false : Double.compare(c1320g.f3994f, this.f3994f) != 0 ? false : Double.compare(c1320g.f3995g, this.f3995g) != 0 ? false : Double.compare(c1320g.f3996h, this.f3996h) != 0 ? false : Double.compare(c1320g.f3997i, this.f3997i) != 0 ? false : Double.compare(c1320g.f3989a, this.f3989a) != 0 ? false : Double.compare(c1320g.f3990b, this.f3990b) != 0 ? false : Double.compare(c1320g.f3991c, this.f3991c) == 0;
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.f3989a);
        int i = (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
        long doubleToLongBits2 = Double.doubleToLongBits(this.f3990b);
        i = (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
        doubleToLongBits2 = Double.doubleToLongBits(this.f3991c);
        i = (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
        doubleToLongBits2 = Double.doubleToLongBits(this.f3992d);
        i = (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
        doubleToLongBits2 = Double.doubleToLongBits(this.f3993e);
        i = (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
        doubleToLongBits2 = Double.doubleToLongBits(this.f3994f);
        i = (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
        doubleToLongBits2 = Double.doubleToLongBits(this.f3995g);
        i = (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
        doubleToLongBits2 = Double.doubleToLongBits(this.f3996h);
        i = (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
        doubleToLongBits2 = Double.doubleToLongBits(this.f3997i);
        return (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
    }

    public String toString() {
        return equals(f3985j) ? "Rotate 0°" : equals(f3986k) ? "Rotate 90°" : equals(f3987l) ? "Rotate 180°" : equals(f3988m) ? "Rotate 270°" : "Matrix{u=" + this.f3989a + ", v=" + this.f3990b + ", w=" + this.f3991c + ", a=" + this.f3992d + ", b=" + this.f3993e + ", c=" + this.f3994f + ", d=" + this.f3995g + ", tx=" + this.f3996h + ", ty=" + this.f3997i + '}';
    }
}
