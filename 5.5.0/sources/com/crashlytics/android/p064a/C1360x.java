package com.crashlytics.android.p064a;

import org.telegram.messenger.exoplayer2.C3446C;
import p033b.p034a.p035a.p036a.p037a.p040c.p041a.C1147e;

/* renamed from: com.crashlytics.android.a.x */
class C1360x {
    /* renamed from: a */
    long f4122a;
    /* renamed from: b */
    private C1147e f4123b;

    public C1360x(C1147e c1147e) {
        if (c1147e == null) {
            throw new NullPointerException("retryState must not be null");
        }
        this.f4123b = c1147e;
    }

    /* renamed from: a */
    public void m6913a() {
        this.f4122a = 0;
        this.f4123b = this.f4123b.m6113c();
    }

    /* renamed from: a */
    public boolean m6914a(long j) {
        return j - this.f4122a >= C3446C.MICROS_PER_SECOND * this.f4123b.m6111a();
    }

    /* renamed from: b */
    public void m6915b(long j) {
        this.f4122a = j;
        this.f4123b = this.f4123b.m6112b();
    }
}
