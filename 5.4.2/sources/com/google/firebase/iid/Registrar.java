package com.google.firebase.iid;

import android.support.annotation.Keep;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.C1897b;
import com.google.firebase.components.C1816e;
import com.google.firebase.components.C1902a;
import com.google.firebase.components.C1905f;
import com.google.firebase.iid.p110a.C1927a;
import java.util.Arrays;
import java.util.List;

@Keep
@KeepForSdk
public final class Registrar implements C1816e {

    /* renamed from: com.google.firebase.iid.Registrar$a */
    private static class C1928a implements C1927a {
        /* renamed from: a */
        private final FirebaseInstanceId f5668a;

        public C1928a(FirebaseInstanceId firebaseInstanceId) {
            this.f5668a = firebaseInstanceId;
        }
    }

    @Keep
    public final List<C1902a<?>> getComponents() {
        C1902a b = C1902a.m8705a(FirebaseInstanceId.class).m8703a(C1905f.m8718a(C1897b.class)).m8702a(C1939j.f5730a).m8701a().m8704b();
        C1902a b2 = C1902a.m8705a(C1927a.class).m8703a(C1905f.m8718a(FirebaseInstanceId.class)).m8702a(C1940k.f5731a).m8704b();
        return Arrays.asList(new C1902a[]{b, b2});
    }
}
