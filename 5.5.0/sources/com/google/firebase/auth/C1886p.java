package com.google.firebase.auth;

import com.google.firebase.auth.FirebaseAuth.C1821b;
import com.google.firebase.auth.internal.C1860a;
import com.google.firebase.p107c.C1899b;

/* renamed from: com.google.firebase.auth.p */
final class C1886p implements Runnable {
    /* renamed from: a */
    private final /* synthetic */ C1899b f5566a;
    /* renamed from: b */
    private final /* synthetic */ FirebaseAuth f5567b;

    C1886p(FirebaseAuth firebaseAuth, C1899b c1899b) {
        this.f5567b = firebaseAuth;
        this.f5566a = c1899b;
    }

    public final void run() {
        this.f5567b.f5438a.m8694a(this.f5566a);
        for (C1860a a : this.f5567b.f5440c) {
            a.m8606a(this.f5566a);
        }
        for (C1821b a2 : this.f5567b.f5439b) {
            a2.m8492a(this.f5567b);
        }
    }
}
