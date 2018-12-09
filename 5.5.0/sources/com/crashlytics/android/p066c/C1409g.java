package com.crashlytics.android.p066c;

import android.os.Looper;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: com.crashlytics.android.c.g */
class C1409g {
    /* renamed from: a */
    private final ExecutorService f4260a;

    public C1409g(ExecutorService executorService) {
        this.f4260a = executorService;
    }

    /* renamed from: a */
    <T> T m7107a(Callable<T> callable) {
        try {
            return Looper.getMainLooper() == Looper.myLooper() ? this.f4260a.submit(callable).get(4, TimeUnit.SECONDS) : this.f4260a.submit(callable).get();
        } catch (RejectedExecutionException e) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
            return null;
        } catch (Throwable e2) {
            C1230c.m6414h().mo1070e("CrashlyticsCore", "Failed to execute task.", e2);
            return null;
        }
    }

    /* renamed from: a */
    Future<?> m7108a(final Runnable runnable) {
        try {
            return this.f4260a.submit(new Runnable(this) {
                /* renamed from: b */
                final /* synthetic */ C1409g f4257b;

                public void run() {
                    try {
                        runnable.run();
                    } catch (Throwable e) {
                        C1230c.m6414h().mo1070e("CrashlyticsCore", "Failed to execute task.", e);
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
            return null;
        }
    }

    /* renamed from: b */
    <T> Future<T> m7109b(final Callable<T> callable) {
        try {
            return this.f4260a.submit(new Callable<T>(this) {
                /* renamed from: b */
                final /* synthetic */ C1409g f4259b;

                public T call() {
                    try {
                        return callable.call();
                    } catch (Throwable e) {
                        C1230c.m6414h().mo1070e("CrashlyticsCore", "Failed to execute task.", e);
                        return null;
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
            return null;
        }
    }
}
