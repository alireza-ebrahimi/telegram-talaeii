package com.crashlytics.android.p066c;

import android.annotation.SuppressLint;
import p033b.p034a.p035a.p036a.p037a.p044f.C1194c;
import p033b.p034a.p035a.p036a.p037a.p044f.C1195d;

@SuppressLint({"CommitPrefEdits"})
/* renamed from: com.crashlytics.android.c.ab */
class ab {
    /* renamed from: a */
    private final C1194c f4197a;

    public ab(C1194c c1194c) {
        this.f4197a = c1194c;
    }

    /* renamed from: a */
    public static ab m6964a(C1194c c1194c, C1446i c1446i) {
        if (!c1194c.mo1051a().getBoolean("preferences_migration_complete", false)) {
            C1194c c1195d = new C1195d(c1446i);
            boolean z = !c1194c.mo1051a().contains("always_send_reports_opt_in") && c1195d.mo1051a().contains("always_send_reports_opt_in");
            if (z) {
                c1194c.mo1052a(c1194c.mo1053b().putBoolean("always_send_reports_opt_in", c1195d.mo1051a().getBoolean("always_send_reports_opt_in", false)));
            }
            c1194c.mo1052a(c1194c.mo1053b().putBoolean("preferences_migration_complete", true));
        }
        return new ab(c1194c);
    }

    /* renamed from: a */
    void m6965a(boolean z) {
        this.f4197a.mo1052a(this.f4197a.mo1053b().putBoolean("always_send_reports_opt_in", z));
    }

    /* renamed from: a */
    boolean m6966a() {
        return this.f4197a.mo1051a().getBoolean("always_send_reports_opt_in", false);
    }
}
