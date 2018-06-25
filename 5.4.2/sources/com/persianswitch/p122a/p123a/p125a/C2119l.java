package com.persianswitch.p122a.p123a.p125a;

import java.util.concurrent.CountDownLatch;

/* renamed from: com.persianswitch.a.a.a.l */
public final class C2119l {
    /* renamed from: a */
    private final CountDownLatch f6430a = new CountDownLatch(1);
    /* renamed from: b */
    private long f6431b = -1;
    /* renamed from: c */
    private long f6432c = -1;

    C2119l() {
    }

    /* renamed from: a */
    void m9561a() {
        if (this.f6431b != -1) {
            throw new IllegalStateException();
        }
        this.f6431b = System.nanoTime();
    }

    /* renamed from: b */
    void m9562b() {
        if (this.f6432c != -1 || this.f6431b == -1) {
            throw new IllegalStateException();
        }
        this.f6432c = System.nanoTime();
        this.f6430a.countDown();
    }

    /* renamed from: c */
    void m9563c() {
        if (this.f6432c != -1 || this.f6431b == -1) {
            throw new IllegalStateException();
        }
        this.f6432c = this.f6431b - 1;
        this.f6430a.countDown();
    }
}
