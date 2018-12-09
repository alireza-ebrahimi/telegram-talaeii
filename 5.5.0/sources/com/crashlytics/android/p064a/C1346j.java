package com.crashlytics.android.p064a;

import java.io.File;
import java.util.List;
import p033b.p034a.p035a.p036a.p037a.p040c.p041a.C1145b;
import p033b.p034a.p035a.p036a.p037a.p040c.p041a.C1146c;
import p033b.p034a.p035a.p036a.p037a.p040c.p041a.C1147e;
import p033b.p034a.p035a.p036a.p037a.p042d.C1171f;

/* renamed from: com.crashlytics.android.a.j */
class C1346j implements C1171f {
    /* renamed from: a */
    private final aa f4086a;
    /* renamed from: b */
    private final C1360x f4087b;

    C1346j(aa aaVar, C1360x c1360x) {
        this.f4086a = aaVar;
        this.f4087b = c1360x;
    }

    /* renamed from: a */
    public static C1346j m6861a(aa aaVar) {
        return new C1346j(aaVar, new C1360x(new C1147e(new C1359w(new C1146c(1000, 8), 0.1d), new C1145b(5))));
    }

    /* renamed from: a */
    public boolean mo1125a(List<File> list) {
        long nanoTime = System.nanoTime();
        if (!this.f4087b.m6914a(nanoTime)) {
            return false;
        }
        if (this.f4086a.mo1125a(list)) {
            this.f4087b.m6913a();
            return true;
        }
        this.f4087b.m6915b(nanoTime);
        return false;
    }
}
