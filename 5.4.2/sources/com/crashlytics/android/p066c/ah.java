package com.crashlytics.android.p066c;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: com.crashlytics.android.c.ah */
class ah implements ae {
    /* renamed from: a */
    private final File f4218a;
    /* renamed from: b */
    private final File[] f4219b;
    /* renamed from: c */
    private final Map<String, String> f4220c;

    public ah(File file) {
        this(file, Collections.emptyMap());
    }

    public ah(File file, Map<String, String> map) {
        this.f4218a = file;
        this.f4219b = new File[]{file};
        this.f4220c = new HashMap(map);
        if (this.f4218a.length() == 0) {
            this.f4220c.putAll(af.f4208a);
        }
    }

    /* renamed from: a */
    public String mo1154a() {
        return mo1156c().getName();
    }

    /* renamed from: b */
    public String mo1155b() {
        String a = mo1154a();
        return a.substring(0, a.lastIndexOf(46));
    }

    /* renamed from: c */
    public File mo1156c() {
        return this.f4218a;
    }

    /* renamed from: d */
    public File[] mo1157d() {
        return this.f4219b;
    }

    /* renamed from: e */
    public Map<String, String> mo1158e() {
        return Collections.unmodifiableMap(this.f4220c);
    }

    /* renamed from: f */
    public void mo1159f() {
        C1230c.m6414h().mo1062a("CrashlyticsCore", "Removing report at " + this.f4218a.getPath());
        this.f4218a.delete();
    }
}
