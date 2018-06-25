package com.google.firebase.auth.internal;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.C1923e;

/* renamed from: com.google.firebase.auth.internal.q */
final class C1877q implements OnFailureListener {
    /* renamed from: a */
    private final /* synthetic */ C1876p f5538a;

    C1877q(C1876p c1876p) {
        this.f5538a = c1876p;
    }

    public final void onFailure(Exception exception) {
        if (exception instanceof C1923e) {
            C1875o.f5528c.m8461v("Failure to refresh token; scheduling refresh after failure", new Object[0]);
            this.f5538a.f5536a.m8630b();
        }
    }
}
