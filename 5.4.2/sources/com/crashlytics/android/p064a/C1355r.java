package com.crashlytics.android.p064a;

import android.content.Context;
import com.google.firebase.analytics.FirebaseAnalytics.C1796a;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: com.crashlytics.android.a.r */
class C1355r {
    /* renamed from: a */
    private final Context f4113a;
    /* renamed from: b */
    private final C1357t f4114b;
    /* renamed from: c */
    private C1347q f4115c;

    public C1355r(Context context) {
        this(context, new C1357t());
    }

    public C1355r(Context context, C1357t c1357t) {
        this.f4113a = context;
        this.f4114b = c1357t;
    }

    /* renamed from: a */
    public C1347q m6893a() {
        if (this.f4115c == null) {
            this.f4115c = C1348k.m6865a(this.f4113a);
        }
        return this.f4115c;
    }

    /* renamed from: a */
    public void m6894a(ad adVar) {
        C1347q a = m6893a();
        if (a == null) {
            C1230c.m6414h().mo1062a("Answers", "Firebase analytics logging was enabled, but not available...");
            return;
        }
        C1356s a2 = this.f4114b.m6909a(adVar);
        if (a2 == null) {
            C1230c.m6414h().mo1062a("Answers", "Fabric event was not mappable to Firebase event: " + adVar);
            return;
        }
        a.mo1133a(a2.m6895a(), a2.m6896b());
        if ("levelEnd".equals(adVar.f4036g)) {
            a.mo1133a(C1796a.POST_SCORE, a2.m6896b());
        }
    }
}
