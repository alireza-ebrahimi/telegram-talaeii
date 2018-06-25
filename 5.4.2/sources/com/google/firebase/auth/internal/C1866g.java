package com.google.firebase.auth.internal;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

/* renamed from: com.google.firebase.auth.internal.g */
public final class C1866g implements Executor {
    /* renamed from: a */
    private static C1866g f5524a = new C1866g();
    /* renamed from: b */
    private Handler f5525b = new Handler(Looper.getMainLooper());

    private C1866g() {
    }

    /* renamed from: a */
    public static C1866g m8623a() {
        return f5524a;
    }

    public final void execute(Runnable runnable) {
        this.f5525b.post(runnable);
    }
}
