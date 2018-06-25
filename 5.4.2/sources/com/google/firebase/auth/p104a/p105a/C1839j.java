package com.google.firebase.auth.p104a.p105a;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.PhoneAuthCredential;

/* renamed from: com.google.firebase.auth.a.a.j */
final class C1839j<ResultT, CallbackT> extends C1834f<C1840k, ResultT> implements C1838w<ResultT> {
    /* renamed from: a */
    private final String f5498a;
    /* renamed from: b */
    private C1836x<ResultT, CallbackT> f5499b;
    /* renamed from: c */
    private TaskCompletionSource<ResultT> f5500c;

    public C1839j(C1836x<ResultT, CallbackT> c1836x, String str) {
        this.f5499b = c1836x;
        this.f5498a = str;
    }

    /* renamed from: a */
    final String mo3017a() {
        return this.f5498a;
    }

    /* renamed from: a */
    public final void mo3018a(ResultT resultT, Status status) {
        Preconditions.checkNotNull(this.f5500c, "doExecute must be called before onComplete");
        if (status == null) {
            this.f5500c.setResult(resultT);
        } else if (this.f5499b.f5492r != null) {
            this.f5500c.setException(C1842m.m8592a(status, (PhoneAuthCredential) this.f5499b.f5492r.clone()));
            this.f5499b.f5492r = null;
        } else {
            this.f5500c.setException(C1842m.m8593a(status));
        }
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        C1840k c1840k = (C1840k) anyClient;
        this.f5500c = taskCompletionSource;
        C1836x c1836x = this.f5499b;
        c1836x.f5479e = c1840k.mo3019a();
        c1836x.mo3015a();
    }
}
