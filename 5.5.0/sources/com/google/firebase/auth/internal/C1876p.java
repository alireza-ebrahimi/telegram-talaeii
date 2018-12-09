package com.google.firebase.auth.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.C1897b;
import com.google.firebase.auth.FirebaseAuth;

/* renamed from: com.google.firebase.auth.internal.p */
final class C1876p implements Runnable {
    /* renamed from: a */
    final /* synthetic */ C1875o f5536a;
    /* renamed from: b */
    private final String f5537b;

    C1876p(C1875o c1875o, String str) {
        this.f5536a = c1875o;
        this.f5537b = Preconditions.checkNotEmpty(str);
    }

    public final void run() {
        C1897b a = C1897b.m8679a(this.f5537b);
        FirebaseAuth instance = FirebaseAuth.getInstance(a);
        C1878s.m8632a(a.m8690a());
        if (instance.m8503a() != null && ((Boolean) C1878s.f5539a.get()).booleanValue()) {
            Task a2 = instance.m8502a(true);
            C1875o.f5528c.m8461v("Token refreshing started", new Object[0]);
            a2.addOnFailureListener(new C1877q(this));
        }
    }
}
