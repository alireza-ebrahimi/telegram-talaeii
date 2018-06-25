package com.google.firebase.auth;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.firebase.auth.internal.C1861c;
import com.google.firebase.auth.internal.C1867r;

/* renamed from: com.google.firebase.auth.r */
final class C1888r implements C1861c, C1867r {
    /* renamed from: a */
    private final /* synthetic */ FirebaseAuth f5569a;

    C1888r(FirebaseAuth firebaseAuth) {
        this.f5569a = firebaseAuth;
    }

    /* renamed from: a */
    public final void mo3039a(Status status) {
        int statusCode = status.getStatusCode();
        if (statusCode == 17011 || statusCode == 17021 || statusCode == 17005) {
            this.f5569a.m8506c();
        }
    }

    /* renamed from: a */
    public final void mo3040a(zzao zzao, FirebaseUser firebaseUser) {
        this.f5569a.m8504a(firebaseUser, zzao, true);
    }
}
