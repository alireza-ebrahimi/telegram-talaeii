package com.crashlytics.android.p064a;

import android.content.Context;
import java.util.Map;
import java.util.UUID;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p.C1121a;

/* renamed from: com.crashlytics.android.a.ag */
class ag {
    /* renamed from: a */
    private final Context f4052a;
    /* renamed from: b */
    private final C1122p f4053b;
    /* renamed from: c */
    private final String f4054c;
    /* renamed from: d */
    private final String f4055d;

    public ag(Context context, C1122p c1122p, String str, String str2) {
        this.f4052a = context;
        this.f4053b = c1122p;
        this.f4054c = str;
        this.f4055d = str2;
    }

    /* renamed from: a */
    public ae m6816a() {
        Map h = this.f4053b.m6066h();
        return new ae(this.f4053b.m6061c(), UUID.randomUUID().toString(), this.f4053b.m6060b(), (String) h.get(C1121a.ANDROID_ID), (String) h.get(C1121a.ANDROID_ADVERTISING_ID), this.f4053b.m6068j(), (String) h.get(C1121a.FONT_TOKEN), C1110i.m6034m(this.f4052a), this.f4053b.m6062d(), this.f4053b.m6065g(), this.f4054c, this.f4055d);
    }
}
