package com.google.firebase.auth;

import com.google.firebase.auth.FirebaseAuth.C1820a;

/* renamed from: com.google.firebase.auth.q */
final class C1887q implements Runnable {
    /* renamed from: a */
    private final /* synthetic */ FirebaseAuth f5568a;

    C1887q(FirebaseAuth firebaseAuth) {
        this.f5568a = firebaseAuth;
    }

    public final void run() {
        for (C1820a a : this.f5568a.f5441d) {
            a.m8491a(this.f5568a);
        }
    }
}
