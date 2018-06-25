package com.google.firebase.auth.p104a.p105a;

import android.content.Context;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.tasks.Task;
import com.google.firebase.C1897b;
import com.google.firebase.C1922d;
import com.google.firebase.auth.C1881j;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.internal.C1867r;
import com.google.firebase.auth.internal.C1868h;
import java.util.Collections;

/* renamed from: com.google.firebase.auth.a.a.h */
public final class C1835h extends C1826a {
    /* renamed from: a */
    private final Context f5473a;
    /* renamed from: b */
    private final C1847u f5474b;

    C1835h(Context context, C1847u c1847u) {
        this.f5473a = context;
        this.f5474b = c1847u;
    }

    /* renamed from: a */
    private final GoogleApi<C1847u> m8568a(boolean z) {
        C1847u c1847u = (C1847u) this.f5474b.clone();
        c1847u.f5468a = z;
        return new C1833e(this.f5473a, C1845s.f5505a, c1847u, new C1922d());
    }

    /* renamed from: a */
    private static <ResultT, CallbackT> C1839j<ResultT, CallbackT> m8569a(C1836x<ResultT, CallbackT> c1836x, String str) {
        return new C1839j(c1836x, str);
    }

    /* renamed from: a */
    public final Task<C1881j> m8570a(C1897b c1897b, FirebaseUser firebaseUser, String str, C1868h c1868h) {
        return m8529a(C1835h.m8569a(new C1837i(str).m8579a(c1897b).m8577a(firebaseUser).m8580a((Object) c1868h).m8578a((C1867r) c1868h), "getAccessToken"));
    }

    /* renamed from: a */
    final C1829b mo3014a() {
        int remoteVersion = DynamiteModule.getRemoteVersion(this.f5473a, "com.google.android.gms.firebase_auth");
        GoogleApi a = m8568a(false);
        int localVersion = DynamiteModule.getLocalVersion(this.f5473a, "com.google.firebase.auth");
        return new C1829b(a, localVersion != 0 ? m8568a(true) : null, new C1832d(remoteVersion, localVersion, Collections.emptyMap(), true));
    }
}
