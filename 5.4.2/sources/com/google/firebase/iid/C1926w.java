package com.google.firebase.iid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.C0429p;
import android.util.Log;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.google.firebase.iid.w */
public abstract class C1926w extends Service {
    /* renamed from: a */
    final ExecutorService f5663a;
    /* renamed from: b */
    private Binder f5664b;
    /* renamed from: c */
    private final Object f5665c;
    /* renamed from: d */
    private int f5666d;
    /* renamed from: e */
    private int f5667e;

    public C1926w() {
        String str = "Firebase-";
        String valueOf = String.valueOf(getClass().getSimpleName());
        this.f5663a = Executors.newSingleThreadExecutor(new NamedThreadFactory(valueOf.length() != 0 ? str.concat(valueOf) : new String(str)));
        this.f5665c = new Object();
        this.f5667e = 0;
    }

    /* renamed from: d */
    private final void m8785d(Intent intent) {
        if (intent != null) {
            C0429p.completeWakefulIntent(intent);
        }
        synchronized (this.f5665c) {
            this.f5667e--;
            if (this.f5667e == 0) {
                stopSelfResult(this.f5666d);
            }
        }
    }

    /* renamed from: a */
    protected Intent mo3045a(Intent intent) {
        return intent;
    }

    /* renamed from: b */
    public abstract void mo3046b(Intent intent);

    /* renamed from: c */
    public boolean mo3057c(Intent intent) {
        return false;
    }

    public final synchronized IBinder onBind(Intent intent) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "Service received bind request");
        }
        if (this.f5664b == null) {
            this.f5664b = new aa(this);
        }
        return this.f5664b;
    }

    public final int onStartCommand(Intent intent, int i, int i2) {
        synchronized (this.f5665c) {
            this.f5666d = i2;
            this.f5667e++;
        }
        Intent a = mo3045a(intent);
        if (a == null) {
            m8785d(intent);
            return 2;
        } else if (mo3057c(a)) {
            m8785d(intent);
            return 2;
        } else {
            this.f5663a.execute(new C1951x(this, a, intent));
            return 3;
        }
    }
}
