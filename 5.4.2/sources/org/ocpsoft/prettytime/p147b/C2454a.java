package org.ocpsoft.prettytime.p147b;

import org.ocpsoft.prettytime.C2453a;
import org.ocpsoft.prettytime.C2457e;

/* renamed from: org.ocpsoft.prettytime.b.a */
public class C2454a implements C2453a {
    /* renamed from: a */
    private long f8210a;
    /* renamed from: b */
    private long f8211b;
    /* renamed from: c */
    private C2457e f8212c;

    /* renamed from: a */
    public long mo3398a() {
        return this.f8210a;
    }

    /* renamed from: a */
    public long mo3399a(int i) {
        long abs = Math.abs(mo3398a());
        return (m12036e() == 0 || Math.abs((((double) m12036e()) / ((double) mo3400b().mo3404a())) * 100.0d) <= ((double) i)) ? abs : abs + 1;
    }

    /* renamed from: a */
    public void m12030a(long j) {
        this.f8210a = j;
    }

    /* renamed from: a */
    public void m12031a(C2457e c2457e) {
        this.f8212c = c2457e;
    }

    /* renamed from: b */
    public C2457e mo3400b() {
        return this.f8212c;
    }

    /* renamed from: b */
    public void m12033b(long j) {
        this.f8211b = j;
    }

    /* renamed from: c */
    public boolean mo3401c() {
        return mo3398a() < 0;
    }

    /* renamed from: d */
    public boolean mo3402d() {
        return !mo3401c();
    }

    /* renamed from: e */
    public long m12036e() {
        return this.f8211b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        C2454a c2454a = (C2454a) obj;
        return this.f8211b != c2454a.f8211b ? false : this.f8210a != c2454a.f8210a ? false : this.f8212c == null ? c2454a.f8212c == null : this.f8212c.equals(c2454a.f8212c);
    }

    public int hashCode() {
        return (this.f8212c == null ? 0 : this.f8212c.hashCode()) + ((((((int) (this.f8211b ^ (this.f8211b >>> 32))) + 31) * 31) + ((int) (this.f8210a ^ (this.f8210a >>> 32)))) * 31);
    }

    public String toString() {
        return "DurationImpl [" + this.f8210a + " " + this.f8212c + ", delta=" + this.f8211b + "]";
    }
}
