package com.google.firebase.auth.p104a.p105a;

import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.internal.Preconditions;

/* renamed from: com.google.firebase.auth.a.a.u */
public final class C1847u extends C1830c implements HasOptions {
    /* renamed from: b */
    private final String f5508b;

    private C1847u(String str) {
        this.f5508b = Preconditions.checkNotEmpty(str, "A valid API key must be provided");
    }

    /* renamed from: a */
    public final /* synthetic */ C1830c mo3021a() {
        return (C1847u) clone();
    }

    /* renamed from: b */
    public final String m8601b() {
        return this.f5508b;
    }

    public final /* synthetic */ Object clone() {
        return new C1848v(this.f5508b).m8602a();
    }
}
