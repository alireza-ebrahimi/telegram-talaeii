package com.google.firebase.iid;

import android.content.BroadcastReceiver.PendingResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public final class ac implements ServiceConnection {
    /* renamed from: a */
    private final Context f5672a;
    /* renamed from: b */
    private final Intent f5673b;
    /* renamed from: c */
    private final ScheduledExecutorService f5674c;
    /* renamed from: d */
    private final Queue<C1952y> f5675d;
    /* renamed from: e */
    private aa f5676e;
    /* renamed from: f */
    private boolean f5677f;

    public ac(Context context, String str) {
        this(context, str, new ScheduledThreadPoolExecutor(0));
    }

    private ac(Context context, String str, ScheduledExecutorService scheduledExecutorService) {
        this.f5675d = new ArrayDeque();
        this.f5677f = false;
        this.f5672a = context.getApplicationContext();
        this.f5673b = new Intent(str).setPackage(this.f5672a.getPackageName());
        this.f5674c = scheduledExecutorService;
    }

    /* renamed from: a */
    private final synchronized void m8795a() {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "flush queue called");
        }
        while (!this.f5675d.isEmpty()) {
            if (Log.isLoggable("EnhancedIntentService", 3)) {
                Log.d("EnhancedIntentService", "found intent to be delivered");
            }
            if (this.f5676e == null || !this.f5676e.isBinderAlive()) {
                if (Log.isLoggable("EnhancedIntentService", 3)) {
                    Log.d("EnhancedIntentService", "binder is dead. start connection? " + (!this.f5677f));
                }
                if (!this.f5677f) {
                    this.f5677f = true;
                    try {
                        if (!ConnectionTracker.getInstance().bindService(this.f5672a, this.f5673b, this, 65)) {
                            Log.e("EnhancedIntentService", "binding to the service failed");
                            while (!this.f5675d.isEmpty()) {
                                ((C1952y) this.f5675d.poll()).m8897a();
                            }
                        }
                    } catch (Throwable e) {
                        Log.e("EnhancedIntentService", "Exception while binding the service", e);
                    }
                }
            } else {
                if (Log.isLoggable("EnhancedIntentService", 3)) {
                    Log.d("EnhancedIntentService", "binder is alive, sending the intent.");
                }
                this.f5676e.m8794a((C1952y) this.f5675d.poll());
            }
        }
    }

    /* renamed from: a */
    public final synchronized void m8796a(Intent intent, PendingResult pendingResult) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "new intent queued in the bind-strategy delivery");
        }
        this.f5675d.add(new C1952y(intent, pendingResult, this.f5674c));
        m8795a();
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this) {
            this.f5677f = false;
            this.f5676e = (aa) iBinder;
            if (Log.isLoggable("EnhancedIntentService", 3)) {
                String valueOf = String.valueOf(componentName);
                Log.d("EnhancedIntentService", new StringBuilder(String.valueOf(valueOf).length() + 20).append("onServiceConnected: ").append(valueOf).toString());
            }
            m8795a();
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            String valueOf = String.valueOf(componentName);
            Log.d("EnhancedIntentService", new StringBuilder(String.valueOf(valueOf).length() + 23).append("onServiceDisconnected: ").append(valueOf).toString());
        }
        m8795a();
    }
}
