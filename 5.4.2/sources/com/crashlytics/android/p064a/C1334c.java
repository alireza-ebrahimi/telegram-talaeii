package com.crashlytics.android.p064a;

import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* renamed from: com.crashlytics.android.a.c */
class C1334c {
    /* renamed from: a */
    final C1335e f4058a;
    /* renamed from: b */
    final Map<String, Object> f4059b = new ConcurrentHashMap();

    public C1334c(C1335e c1335e) {
        this.f4058a = c1335e;
    }

    /* renamed from: a */
    void m6826a(String str, Number number) {
        if (!this.f4058a.m6831a((Object) str, "key") && !this.f4058a.m6831a((Object) number, C1797b.VALUE)) {
            m6827a(this.f4058a.m6830a(str), (Object) number);
        }
    }

    /* renamed from: a */
    void m6827a(String str, Object obj) {
        if (!this.f4058a.m6832a(this.f4059b, str)) {
            this.f4059b.put(str, obj);
        }
    }

    /* renamed from: a */
    void m6828a(String str, String str2) {
        if (!this.f4058a.m6831a((Object) str, "key") && !this.f4058a.m6831a((Object) str2, C1797b.VALUE)) {
            m6827a(this.f4058a.m6830a(str), this.f4058a.m6830a(str2));
        }
    }

    public String toString() {
        return new JSONObject(this.f4059b).toString();
    }
}
