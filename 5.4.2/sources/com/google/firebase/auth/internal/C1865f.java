package com.google.firebase.auth.internal;

import com.google.android.gms.common.api.internal.BackgroundDetector.BackgroundStateChangeListener;

/* renamed from: com.google.firebase.auth.internal.f */
final class C1865f implements BackgroundStateChangeListener {
    /* renamed from: a */
    private final /* synthetic */ C1864e f5523a;

    C1865f(C1864e c1864e) {
        this.f5523a = c1864e;
    }

    public final void onBackgroundStateChanged(boolean z) {
        if (z) {
            this.f5523a.f5522d = true;
            this.f5523a.m8620a();
            return;
        }
        this.f5523a.f5522d = false;
        if (this.f5523a.m8619b()) {
            this.f5523a.f5521c.m8629a();
        }
    }
}
