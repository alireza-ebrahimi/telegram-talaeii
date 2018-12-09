package com.google.firebase.iid;

import android.content.Intent;

/* renamed from: com.google.firebase.iid.x */
final class C1951x implements Runnable {
    /* renamed from: a */
    private final /* synthetic */ Intent f5766a;
    /* renamed from: b */
    private final /* synthetic */ Intent f5767b;
    /* renamed from: c */
    private final /* synthetic */ C1926w f5768c;

    C1951x(C1926w c1926w, Intent intent, Intent intent2) {
        this.f5768c = c1926w;
        this.f5766a = intent;
        this.f5767b = intent2;
    }

    public final void run() {
        this.f5768c.mo3046b(this.f5766a);
        this.f5768c.m8785d(this.f5767b);
    }
}
