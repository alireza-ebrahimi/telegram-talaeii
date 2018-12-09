package com.google.firebase.iid;

import android.util.Log;

final class ab implements Runnable {
    /* renamed from: a */
    private final /* synthetic */ C1952y f5670a;
    /* renamed from: b */
    private final /* synthetic */ aa f5671b;

    ab(aa aaVar, C1952y c1952y) {
        this.f5671b = aaVar;
        this.f5670a = c1952y;
    }

    public final void run() {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "bg processing of the intent starting now");
        }
        this.f5671b.f5669a.mo3046b(this.f5670a.f5769a);
        this.f5670a.m8897a();
    }
}
