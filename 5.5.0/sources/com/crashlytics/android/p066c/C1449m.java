package com.crashlytics.android.p066c;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.atomic.AtomicBoolean;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: com.crashlytics.android.c.m */
class C1449m implements UncaughtExceptionHandler {
    /* renamed from: a */
    private final C1417a f4383a;
    /* renamed from: b */
    private final UncaughtExceptionHandler f4384b;
    /* renamed from: c */
    private final AtomicBoolean f4385c = new AtomicBoolean(false);

    /* renamed from: com.crashlytics.android.c.m$a */
    interface C1417a {
        /* renamed from: a */
        void mo1163a(Thread thread, Throwable th);
    }

    public C1449m(C1417a c1417a, UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.f4383a = c1417a;
        this.f4384b = uncaughtExceptionHandler;
    }

    /* renamed from: a */
    boolean m7241a() {
        return this.f4385c.get();
    }

    public void uncaughtException(Thread thread, Throwable th) {
        this.f4385c.set(true);
        try {
            this.f4383a.mo1163a(thread, th);
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("CrashlyticsCore", "An error occurred in the uncaught exception handler", e);
        } finally {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Crashlytics completed exception processing. Invoking default exception handler.");
            this.f4384b.uncaughtException(thread, th);
            this.f4385c.set(false);
        }
    }
}
