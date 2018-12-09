package com.crashlytics.android.p064a;

import android.app.Activity;
import android.os.Bundle;
import com.crashlytics.android.p064a.ad.C1332b;
import p033b.p034a.p035a.p036a.C1223a.C1091b;

/* renamed from: com.crashlytics.android.a.h */
class C1344h extends C1091b {
    /* renamed from: a */
    private final ab f4083a;
    /* renamed from: b */
    private final C1350l f4084b;

    public C1344h(ab abVar, C1350l c1350l) {
        this.f4083a = abVar;
        this.f4084b = c1350l;
    }

    /* renamed from: a */
    public void mo1071a(Activity activity) {
        this.f4083a.m6793a(activity, C1332b.START);
    }

    /* renamed from: a */
    public void mo1072a(Activity activity, Bundle bundle) {
    }

    /* renamed from: b */
    public void mo1073b(Activity activity) {
        this.f4083a.m6793a(activity, C1332b.RESUME);
        this.f4084b.m6873a();
    }

    /* renamed from: b */
    public void mo1129b(Activity activity, Bundle bundle) {
    }

    /* renamed from: c */
    public void mo1130c(Activity activity) {
        this.f4083a.m6793a(activity, C1332b.PAUSE);
        this.f4084b.m6876b();
    }

    /* renamed from: d */
    public void mo1131d(Activity activity) {
        this.f4083a.m6793a(activity, C1332b.STOP);
    }

    /* renamed from: e */
    public void mo1132e(Activity activity) {
    }
}
