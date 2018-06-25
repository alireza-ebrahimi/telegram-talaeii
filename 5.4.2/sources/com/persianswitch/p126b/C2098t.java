package com.persianswitch.p126b;

import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

/* renamed from: com.persianswitch.b.t */
public class C2098t {
    /* renamed from: b */
    public static final C2098t f6342b = new C22601();
    /* renamed from: a */
    private boolean f6343a;
    /* renamed from: c */
    private long f6344c;
    /* renamed from: d */
    private long f6345d;

    /* renamed from: com.persianswitch.b.t$1 */
    static class C22601 extends C2098t {
        C22601() {
        }

        /* renamed from: a */
        public C2098t mo3199a(long j) {
            return this;
        }

        /* renamed from: a */
        public C2098t mo3200a(long j, TimeUnit timeUnit) {
            return this;
        }

        /* renamed from: g */
        public void mo3205g() {
        }
    }

    /* renamed from: a */
    public C2098t mo3199a(long j) {
        this.f6343a = true;
        this.f6344c = j;
        return this;
    }

    /* renamed from: a */
    public C2098t mo3200a(long j, TimeUnit timeUnit) {
        if (j < 0) {
            throw new IllegalArgumentException("timeout < 0: " + j);
        } else if (timeUnit == null) {
            throw new IllegalArgumentException("unit == null");
        } else {
            this.f6345d = timeUnit.toNanos(j);
            return this;
        }
    }

    /* renamed from: d */
    public long mo3201d() {
        if (this.f6343a) {
            return this.f6344c;
        }
        throw new IllegalStateException("No deadline");
    }

    public long e_() {
        return this.f6345d;
    }

    /* renamed from: f */
    public C2098t mo3203f() {
        this.f6343a = false;
        return this;
    }

    public boolean f_() {
        return this.f6343a;
    }

    /* renamed from: g */
    public void mo3205g() {
        if (Thread.interrupted()) {
            throw new InterruptedIOException("thread interrupted");
        } else if (this.f6343a && this.f6344c - System.nanoTime() <= 0) {
            throw new InterruptedIOException("deadline reached");
        }
    }

    public C2098t g_() {
        this.f6345d = 0;
        return this;
    }
}
