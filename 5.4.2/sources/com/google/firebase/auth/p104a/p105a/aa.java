package com.google.firebase.auth.p104a.p105a;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzaj;
import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.android.gms.internal.firebase_auth.zzav;
import com.google.android.gms.internal.firebase_auth.zzx;
import com.google.firebase.auth.PhoneAuthCredential;

/* renamed from: com.google.firebase.auth.a.a.aa */
final class aa extends C1828p {
    /* renamed from: a */
    final /* synthetic */ C1836x f5458a;

    private aa(C1836x c1836x) {
        this.f5458a = c1836x;
    }

    /* renamed from: a */
    private final void m8544a(ag agVar) {
        this.f5458a.f5484j.execute(new af(this, agVar));
    }

    /* renamed from: a */
    public final void mo2999a() {
        Preconditions.checkState(this.f5458a.f5475a == 5, "Unexpected response type " + this.f5458a.f5475a);
        this.f5458a.m8576c();
    }

    /* renamed from: a */
    public final void mo3000a(Status status) {
        if (this.f5458a.f5475a == 8) {
            this.f5458a.f5494t = true;
            this.f5458a.f5493s = false;
            m8544a(new ae(this, status));
            return;
        }
        this.f5458a.m8575b(status);
        this.f5458a.m8582a(status);
    }

    /* renamed from: a */
    public final void mo3001a(Status status, PhoneAuthCredential phoneAuthCredential) {
        if (this.f5458a.f5485k != null) {
            this.f5458a.f5494t = true;
            this.f5458a.f5485k.m8603a(status, phoneAuthCredential);
            return;
        }
        mo3000a(status);
    }

    /* renamed from: a */
    public final void mo3002a(zzao zzao) {
        boolean z = true;
        if (this.f5458a.f5475a != 1) {
            z = false;
        }
        Preconditions.checkState(z, "Unexpected response type: " + this.f5458a.f5475a);
        this.f5458a.f5486l = zzao;
        this.f5458a.m8576c();
    }

    /* renamed from: a */
    public final void mo3003a(zzao zzao, zzaj zzaj) {
        Preconditions.checkState(this.f5458a.f5475a == 2, "Unexpected response type: " + this.f5458a.f5475a);
        this.f5458a.f5486l = zzao;
        this.f5458a.f5487m = zzaj;
        this.f5458a.m8576c();
    }

    /* renamed from: a */
    public final void mo3004a(zzav zzav) {
        Preconditions.checkState(this.f5458a.f5475a == 4, "Unexpected response type " + this.f5458a.f5475a);
        this.f5458a.f5489o = zzav;
        this.f5458a.m8576c();
    }

    /* renamed from: a */
    public final void mo3005a(zzx zzx) {
        Preconditions.checkState(this.f5458a.f5475a == 3, "Unexpected response type " + this.f5458a.f5475a);
        this.f5458a.f5488n = zzx;
        this.f5458a.m8576c();
    }

    /* renamed from: a */
    public final void mo3006a(PhoneAuthCredential phoneAuthCredential) {
        Preconditions.checkState(this.f5458a.f5475a == 8, "Unexpected response type " + this.f5458a.f5475a);
        this.f5458a.f5494t = true;
        this.f5458a.f5493s = true;
        m8544a(new ac(this, phoneAuthCredential));
    }

    /* renamed from: a */
    public final void mo3007a(String str) {
        Preconditions.checkState(this.f5458a.f5475a == 7, "Unexpected response type " + this.f5458a.f5475a);
        this.f5458a.f5490p = str;
        this.f5458a.m8576c();
    }

    /* renamed from: b */
    public final void mo3008b() {
        Preconditions.checkState(this.f5458a.f5475a == 6, "Unexpected response type " + this.f5458a.f5475a);
        this.f5458a.m8576c();
    }

    /* renamed from: b */
    public final void mo3009b(String str) {
        Preconditions.checkState(this.f5458a.f5475a == 8, "Unexpected response type " + this.f5458a.f5475a);
        this.f5458a.f5491q = str;
        m8544a(new ab(this, str));
    }

    /* renamed from: c */
    public final void mo3010c() {
        Preconditions.checkState(this.f5458a.f5475a == 9, "Unexpected response type " + this.f5458a.f5475a);
        this.f5458a.m8576c();
    }

    /* renamed from: c */
    public final void mo3011c(String str) {
        Preconditions.checkState(this.f5458a.f5475a == 8, "Unexpected response type " + this.f5458a.f5475a);
        this.f5458a.f5491q = str;
        this.f5458a.f5494t = true;
        this.f5458a.f5493s = true;
        m8544a(new ad(this, str));
    }
}
