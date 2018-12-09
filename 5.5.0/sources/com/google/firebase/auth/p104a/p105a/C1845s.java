package com.google.firebase.auth.p104a.p105a;

import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ClientKey;

/* renamed from: com.google.firebase.auth.a.a.s */
public final class C1845s {
    /* renamed from: a */
    public static final Api<C1847u> f5505a = new Api("InternalFirebaseAuth.FIREBASE_AUTH_API", f5507c, f5506b);
    /* renamed from: b */
    private static final ClientKey<C1840k> f5506b = new ClientKey();
    /* renamed from: c */
    private static final AbstractClientBuilder<C1840k, C1847u> f5507c = new C1846t();

    /* renamed from: a */
    public static C1835h m8599a(Context context, C1847u c1847u) {
        return new C1835h(context, c1847u);
    }
}
