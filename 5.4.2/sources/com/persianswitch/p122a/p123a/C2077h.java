package com.persianswitch.p122a.p123a;

/* renamed from: com.persianswitch.a.a.h */
public abstract class C2077h implements Runnable {
    /* renamed from: b */
    protected final String f6263b;

    public C2077h(String str, Object... objArr) {
        this.f6263b = C2187l.m9892a(str, objArr);
    }

    /* renamed from: b */
    protected abstract void mo3089b();

    public final void run() {
        String name = Thread.currentThread().getName();
        Thread.currentThread().setName(this.f6263b);
        try {
            mo3089b();
        } finally {
            Thread.currentThread().setName(name);
        }
    }
}
