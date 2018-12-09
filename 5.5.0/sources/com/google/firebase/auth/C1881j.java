package com.google.firebase.auth;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Map;

/* renamed from: com.google.firebase.auth.j */
public class C1881j {
    /* renamed from: a */
    private String f5564a;
    /* renamed from: b */
    private Map<String, Object> f5565b;

    @KeepForSdk
    public C1881j(String str, Map<String, Object> map) {
        this.f5564a = str;
        this.f5565b = map;
    }

    /* renamed from: a */
    public String m8666a() {
        Map map = (Map) this.f5565b.get("firebase");
        return map != null ? (String) map.get("sign_in_provider") : null;
    }
}
