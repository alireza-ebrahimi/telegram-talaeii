package com.google.firebase.auth.p104a.p105a;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.firebase.auth.C1881j;
import com.google.firebase.auth.internal.C1861c;
import com.google.firebase.auth.internal.C1879t;

@VisibleForTesting
/* renamed from: com.google.firebase.auth.a.a.i */
final class C1837i extends C1836x<C1881j, C1861c> {
    /* renamed from: t */
    private final String f5497t;

    public C1837i(String str) {
        super(1);
        this.f5497t = Preconditions.checkNotEmpty(str, "refresh token cannot be null");
    }

    /* renamed from: a */
    public final void mo3015a() {
        this.e.mo3020a(this.f5497t, this.b);
    }

    /* renamed from: b */
    public final void mo3016b() {
        if (TextUtils.isEmpty(this.l.zzap())) {
            this.l.zzr(this.f5497t);
        }
        ((C1861c) this.f).mo3040a(this.l, this.d);
        m8584b(C1879t.m8633a(this.l.zzaw()));
    }
}
