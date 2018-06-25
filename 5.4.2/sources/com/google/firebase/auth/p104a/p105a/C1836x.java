package com.google.firebase.auth.p104a.p105a;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.firebase_auth.zzaj;
import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.android.gms.internal.firebase_auth.zzav;
import com.google.android.gms.internal.firebase_auth.zzx;
import com.google.firebase.C1897b;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider.C1825a;
import com.google.firebase.auth.internal.C1867r;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* renamed from: com.google.firebase.auth.a.a.x */
abstract class C1836x<SuccessT, CallbackT> {
    /* renamed from: a */
    protected final int f5475a;
    /* renamed from: b */
    protected final aa f5476b = new aa();
    /* renamed from: c */
    protected C1897b f5477c;
    /* renamed from: d */
    protected FirebaseUser f5478d;
    /* renamed from: e */
    protected C1843q f5479e;
    /* renamed from: f */
    protected CallbackT f5480f;
    /* renamed from: g */
    protected C1867r f5481g;
    /* renamed from: h */
    protected C1838w<SuccessT> f5482h = this;
    /* renamed from: i */
    protected final List<C1825a> f5483i = new ArrayList();
    /* renamed from: j */
    protected Executor f5484j;
    /* renamed from: k */
    protected C1850z f5485k;
    /* renamed from: l */
    protected zzao f5486l;
    /* renamed from: m */
    protected zzaj f5487m;
    /* renamed from: n */
    protected zzx f5488n;
    /* renamed from: o */
    protected zzav f5489o;
    /* renamed from: p */
    protected String f5490p;
    /* renamed from: q */
    protected String f5491q;
    /* renamed from: r */
    protected PhoneAuthCredential f5492r;
    @VisibleForTesting
    /* renamed from: s */
    boolean f5493s;
    /* renamed from: t */
    private boolean f5494t;
    @VisibleForTesting
    /* renamed from: u */
    private SuccessT f5495u;
    @VisibleForTesting
    /* renamed from: v */
    private Status f5496v;

    public C1836x(int i) {
        this.f5475a = i;
    }

    /* renamed from: b */
    private final void m8575b(Status status) {
        if (this.f5481g != null) {
            this.f5481g.mo3039a(status);
        }
    }

    /* renamed from: c */
    private final void m8576c() {
        mo3016b();
        Preconditions.checkState(this.f5494t, "no success or failure set on method implementation");
    }

    /* renamed from: a */
    public final C1836x<SuccessT, CallbackT> m8577a(FirebaseUser firebaseUser) {
        this.f5478d = (FirebaseUser) Preconditions.checkNotNull(firebaseUser, "firebaseUser cannot be null");
        return this;
    }

    /* renamed from: a */
    public final C1836x<SuccessT, CallbackT> m8578a(C1867r c1867r) {
        this.f5481g = (C1867r) Preconditions.checkNotNull(c1867r, "external failure callback cannot be null");
        return this;
    }

    /* renamed from: a */
    public final C1836x<SuccessT, CallbackT> m8579a(C1897b c1897b) {
        this.f5477c = (C1897b) Preconditions.checkNotNull(c1897b, "firebaseApp cannot be null");
        return this;
    }

    /* renamed from: a */
    public final C1836x<SuccessT, CallbackT> m8580a(CallbackT callbackT) {
        this.f5480f = Preconditions.checkNotNull(callbackT, "external callback cannot be null");
        return this;
    }

    /* renamed from: a */
    protected abstract void mo3015a();

    /* renamed from: a */
    public final void m8582a(Status status) {
        this.f5494t = true;
        this.f5493s = false;
        this.f5496v = status;
        this.f5482h.mo3018a(null, status);
    }

    /* renamed from: b */
    public abstract void mo3016b();

    /* renamed from: b */
    public final void m8584b(SuccessT successT) {
        this.f5494t = true;
        this.f5493s = true;
        this.f5495u = successT;
        this.f5482h.mo3018a(successT, null);
    }
}
