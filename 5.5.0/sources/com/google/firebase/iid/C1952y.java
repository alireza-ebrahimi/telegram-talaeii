package com.google.firebase.iid;

import android.content.BroadcastReceiver.PendingResult;
import android.content.Intent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* renamed from: com.google.firebase.iid.y */
final class C1952y {
    /* renamed from: a */
    final Intent f5769a;
    /* renamed from: b */
    private final PendingResult f5770b;
    /* renamed from: c */
    private boolean f5771c = false;
    /* renamed from: d */
    private final ScheduledFuture<?> f5772d;

    C1952y(Intent intent, PendingResult pendingResult, ScheduledExecutorService scheduledExecutorService) {
        this.f5769a = intent;
        this.f5770b = pendingResult;
        this.f5772d = scheduledExecutorService.schedule(new C1953z(this, intent), 9500, TimeUnit.MILLISECONDS);
    }

    /* renamed from: a */
    final synchronized void m8897a() {
        if (!this.f5771c) {
            this.f5770b.finish();
            this.f5772d.cancel(false);
            this.f5771c = true;
        }
    }
}
