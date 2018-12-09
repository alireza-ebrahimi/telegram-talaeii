package com.google.firebase.auth;

import android.support.annotation.Keep;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.C1897b;
import com.google.firebase.auth.internal.C1823b;
import com.google.firebase.components.C1816e;
import com.google.firebase.components.C1902a;
import com.google.firebase.components.C1905f;
import java.util.Arrays;
import java.util.List;

@Keep
@KeepForSdk
public class FirebaseAuthRegistrar implements C1816e {
    @Keep
    public List<C1902a<?>> getComponents() {
        C1902a[] c1902aArr = new C1902a[1];
        c1902aArr[0] = C1902a.m8706a(FirebaseAuth.class, C1823b.class).m8703a(C1905f.m8718a(C1897b.class)).m8702a(C1889s.f5570a).m8701a().m8704b();
        return Arrays.asList(c1902aArr);
    }
}
