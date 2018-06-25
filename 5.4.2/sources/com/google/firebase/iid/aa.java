package com.google.firebase.iid;

import android.os.Binder;
import android.os.Process;
import android.util.Log;

public final class aa extends Binder {
    /* renamed from: a */
    private final C1926w f5669a;

    aa(C1926w c1926w) {
        this.f5669a = c1926w;
    }

    /* renamed from: a */
    public final void m8794a(C1952y c1952y) {
        if (Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Binding only allowed within app");
        }
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "service received new intent via bind strategy");
        }
        if (this.f5669a.mo3057c(c1952y.f5769a)) {
            c1952y.m8897a();
            return;
        }
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "intent being queued for bg execution");
        }
        this.f5669a.f5663a.execute(new ab(this, c1952y));
    }
}
