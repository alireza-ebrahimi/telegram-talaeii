package com.crashlytics.android.p064a;

import android.annotation.SuppressLint;
import android.content.Context;
import p033b.p034a.p035a.p036a.p037a.p044f.C1194c;
import p033b.p034a.p035a.p036a.p037a.p044f.C1195d;

/* renamed from: com.crashlytics.android.a.i */
class C1345i {
    /* renamed from: a */
    private final C1194c f4085a;

    C1345i(C1194c c1194c) {
        this.f4085a = c1194c;
    }

    /* renamed from: a */
    public static C1345i m6858a(Context context) {
        return new C1345i(new C1195d(context, "settings"));
    }

    @SuppressLint({"CommitPrefEdits"})
    /* renamed from: a */
    public void m6859a() {
        this.f4085a.mo1052a(this.f4085a.mo1053b().putBoolean("analytics_launched", true));
    }

    @SuppressLint({"CommitPrefEdits"})
    /* renamed from: b */
    public boolean m6860b() {
        return this.f4085a.mo1051a().getBoolean("analytics_launched", false);
    }
}
