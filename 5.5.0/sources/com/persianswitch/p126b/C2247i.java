package com.persianswitch.p126b;

import java.util.concurrent.TimeUnit;

/* renamed from: com.persianswitch.b.i */
public class C2247i extends C2098t {
    /* renamed from: a */
    private C2098t f6945a;

    public C2247i(C2098t c2098t) {
        if (c2098t == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.f6945a = c2098t;
    }

    /* renamed from: a */
    public final C2247i m10335a(C2098t c2098t) {
        if (c2098t == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.f6945a = c2098t;
        return this;
    }

    /* renamed from: a */
    public final C2098t m10336a() {
        return this.f6945a;
    }

    /* renamed from: a */
    public C2098t mo3199a(long j) {
        return this.f6945a.mo3199a(j);
    }

    /* renamed from: a */
    public C2098t mo3200a(long j, TimeUnit timeUnit) {
        return this.f6945a.mo3200a(j, timeUnit);
    }

    /* renamed from: d */
    public long mo3201d() {
        return this.f6945a.mo3201d();
    }

    public long e_() {
        return this.f6945a.e_();
    }

    /* renamed from: f */
    public C2098t mo3203f() {
        return this.f6945a.mo3203f();
    }

    public boolean f_() {
        return this.f6945a.f_();
    }

    /* renamed from: g */
    public void mo3205g() {
        this.f6945a.mo3205g();
    }

    public C2098t g_() {
        return this.f6945a.g_();
    }
}
