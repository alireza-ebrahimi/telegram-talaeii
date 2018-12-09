package com.google.firebase.iid;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.concurrent.GuardedBy;

public final class ap {
    @GuardedBy("MessengerIpcClient.class")
    /* renamed from: a */
    private static ap f5702a;
    /* renamed from: b */
    private final Context f5703b;
    /* renamed from: c */
    private final ScheduledExecutorService f5704c;
    @GuardedBy("this")
    /* renamed from: d */
    private ar f5705d = new ar();
    @GuardedBy("this")
    /* renamed from: e */
    private int f5706e = 1;

    private ap(Context context, ScheduledExecutorService scheduledExecutorService) {
        this.f5704c = scheduledExecutorService;
        this.f5703b = context.getApplicationContext();
    }

    /* renamed from: a */
    private final synchronized int m8826a() {
        int i;
        i = this.f5706e;
        this.f5706e = i + 1;
        return i;
    }

    /* renamed from: a */
    private final synchronized <T> Task<T> m8828a(C1934f<T> c1934f) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(c1934f);
            Log.d("MessengerIpcClient", new StringBuilder(String.valueOf(valueOf).length() + 9).append("Queueing ").append(valueOf).toString());
        }
        if (!this.f5705d.m8838a((C1934f) c1934f)) {
            this.f5705d = new ar();
            this.f5705d.m8838a((C1934f) c1934f);
        }
        return c1934f.f5721b.getTask();
    }

    /* renamed from: a */
    public static synchronized ap m8829a(Context context) {
        ap apVar;
        synchronized (ap.class) {
            if (f5702a == null) {
                f5702a = new ap(context, Executors.newSingleThreadScheduledExecutor());
            }
            apVar = f5702a;
        }
        return apVar;
    }

    /* renamed from: a */
    public final Task<Void> m8831a(int i, Bundle bundle) {
        return m8828a(new C1935e(m8826a(), 2, bundle));
    }

    /* renamed from: b */
    public final Task<Bundle> m8832b(int i, Bundle bundle) {
        return m8828a(new C1937h(m8826a(), 1, bundle));
    }
}
