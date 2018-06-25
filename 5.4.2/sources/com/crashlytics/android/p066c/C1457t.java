package com.crashlytics.android.p066c;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: com.crashlytics.android.c.t */
class C1457t implements ae {
    /* renamed from: a */
    private final File[] f4400a;
    /* renamed from: b */
    private final Map<String, String> f4401b = new HashMap(af.f4208a);
    /* renamed from: c */
    private final String f4402c;

    public C1457t(String str, File[] fileArr) {
        this.f4400a = fileArr;
        this.f4402c = str;
    }

    /* renamed from: a */
    public String mo1154a() {
        return this.f4400a[0].getName();
    }

    /* renamed from: b */
    public String mo1155b() {
        return this.f4402c;
    }

    /* renamed from: c */
    public File mo1156c() {
        return this.f4400a[0];
    }

    /* renamed from: d */
    public File[] mo1157d() {
        return this.f4400a;
    }

    /* renamed from: e */
    public Map<String, String> mo1158e() {
        return Collections.unmodifiableMap(this.f4401b);
    }

    /* renamed from: f */
    public void mo1159f() {
        for (File file : this.f4400a) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Removing invalid report file at " + file.getPath());
            file.delete();
        }
    }
}
