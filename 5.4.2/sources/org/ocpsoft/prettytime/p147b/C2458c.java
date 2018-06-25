package org.ocpsoft.prettytime.p147b;

import org.ocpsoft.prettytime.C2457e;

/* renamed from: org.ocpsoft.prettytime.b.c */
public abstract class C2458c implements C2457e {
    /* renamed from: a */
    private long f8216a = 0;
    /* renamed from: b */
    private long f8217b = 1;

    /* renamed from: a */
    public long mo3404a() {
        return this.f8217b;
    }

    /* renamed from: a */
    public void m12045a(long j) {
        this.f8216a = j;
    }

    /* renamed from: b */
    public long mo3405b() {
        return this.f8216a;
    }

    /* renamed from: b */
    public void m12047b(long j) {
        this.f8217b = j;
    }

    /* renamed from: c */
    protected abstract String mo3406c();

    /* renamed from: d */
    protected String m12049d() {
        return "org.ocpsoft.prettytime.i18n.Resources";
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
        C2458c c2458c = (C2458c) obj;
        return this.f8216a != c2458c.f8216a ? false : this.f8217b == c2458c.f8217b;
    }

    public int hashCode() {
        return ((((int) (this.f8216a ^ (this.f8216a >>> 32))) + 31) * 31) + ((int) (this.f8217b ^ (this.f8217b >>> 32)));
    }

    public String toString() {
        return mo3406c();
    }
}
