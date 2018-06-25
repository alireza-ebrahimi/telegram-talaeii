package com.crashlytics.android.p064a;

import java.util.Map;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: com.crashlytics.android.a.d */
public abstract class C1326d<T extends C1326d> {
    /* renamed from: b */
    final C1335e f4004b = new C1335e(20, 100, C1230c.m6415i());
    /* renamed from: c */
    final C1334c f4005c = new C1334c(this.f4004b);

    /* renamed from: a */
    public T m6783a(String str, Number number) {
        this.f4005c.m6826a(str, number);
        return this;
    }

    /* renamed from: a */
    public T m6784a(String str, String str2) {
        this.f4005c.m6828a(str, str2);
        return this;
    }

    /* renamed from: b */
    Map<String, Object> m6785b() {
        return this.f4005c.f4059b;
    }
}
