package com.google.firebase.iid;

import android.content.Intent;
import android.util.Log;

/* renamed from: com.google.firebase.iid.z */
final class C1953z implements Runnable {
    /* renamed from: a */
    private final /* synthetic */ Intent f5773a;
    /* renamed from: b */
    private final /* synthetic */ C1952y f5774b;

    C1953z(C1952y c1952y, Intent intent) {
        this.f5774b = c1952y;
        this.f5773a = intent;
    }

    public final void run() {
        String action = this.f5773a.getAction();
        Log.w("EnhancedIntentService", new StringBuilder(String.valueOf(action).length() + 61).append("Service took too long to process intent: ").append(action).append(" App may get closed.").toString());
        this.f5774b.m8897a();
    }
}
