package com.google.firebase.auth.internal;

import com.google.android.gms.common.logging.Logger;
import com.google.firebase.auth.C1881j;
import java.util.Collections;
import java.util.Map;

/* renamed from: com.google.firebase.auth.internal.t */
public final class C1879t {
    /* renamed from: a */
    private static final Logger f5540a = new Logger("GetTokenResultFactory", new String[0]);

    /* renamed from: a */
    public static C1881j m8633a(String str) {
        Map a;
        try {
            a = C1880u.m8635a(str);
        } catch (Throwable e) {
            f5540a.m8456e("Error parsing token claims", e, new Object[0]);
            a = Collections.EMPTY_MAP;
        }
        return new C1881j(str, a);
    }
}
