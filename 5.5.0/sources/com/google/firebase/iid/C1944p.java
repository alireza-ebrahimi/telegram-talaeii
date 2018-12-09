package com.google.firebase.iid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* renamed from: com.google.firebase.iid.p */
final class C1944p extends Handler {
    /* renamed from: a */
    private final /* synthetic */ C1943o f5743a;

    C1944p(C1943o c1943o, Looper looper) {
        this.f5743a = c1943o;
        super(looper);
    }

    public final void handleMessage(Message message) {
        this.f5743a.m8864a(message);
    }
}
