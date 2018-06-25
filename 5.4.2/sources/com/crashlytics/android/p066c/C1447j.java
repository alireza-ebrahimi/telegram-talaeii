package com.crashlytics.android.p066c;

import java.io.File;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.p037a.p044f.C1192a;

/* renamed from: com.crashlytics.android.c.j */
class C1447j {
    /* renamed from: a */
    private final String f4380a;
    /* renamed from: b */
    private final C1192a f4381b;

    public C1447j(String str, C1192a c1192a) {
        this.f4380a = str;
        this.f4381b = c1192a;
    }

    /* renamed from: d */
    private File m7233d() {
        return new File(this.f4381b.mo1050a(), this.f4380a);
    }

    /* renamed from: a */
    public boolean m7234a() {
        boolean z = false;
        try {
            z = m7233d().createNewFile();
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("CrashlyticsCore", "Error creating marker: " + this.f4380a, e);
        }
        return z;
    }

    /* renamed from: b */
    public boolean m7235b() {
        return m7233d().exists();
    }

    /* renamed from: c */
    public boolean m7236c() {
        return m7233d().delete();
    }
}
