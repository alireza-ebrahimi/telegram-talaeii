package com.crashlytics.android.p064a;

import android.content.Context;
import java.util.UUID;
import p033b.p034a.p035a.p036a.p037a.p039b.C1113k;
import p033b.p034a.p035a.p036a.p037a.p042d.C1167b;
import p033b.p034a.p035a.p036a.p037a.p042d.C1168c;
import p033b.p034a.p035a.p036a.p037a.p045g.C1197b;

/* renamed from: com.crashlytics.android.a.z */
class C1363z extends C1167b<ad> {
    /* renamed from: g */
    private C1197b f4126g;

    C1363z(Context context, af afVar, C1113k c1113k, C1168c c1168c) {
        super(context, afVar, c1113k, c1168c, 100);
    }

    /* renamed from: a */
    protected String mo1142a() {
        return "sa" + "_" + UUID.randomUUID().toString() + "_" + this.c.mo1024a() + ".tap";
    }

    /* renamed from: a */
    void m6918a(C1197b c1197b) {
        this.f4126g = c1197b;
    }

    /* renamed from: b */
    protected int mo1143b() {
        return this.f4126g == null ? super.mo1143b() : this.f4126g.f3451e;
    }

    /* renamed from: c */
    protected int mo1144c() {
        return this.f4126g == null ? super.mo1144c() : this.f4126g.f3449c;
    }
}
