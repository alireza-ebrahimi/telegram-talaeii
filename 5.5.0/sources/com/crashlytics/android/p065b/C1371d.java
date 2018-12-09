package com.crashlytics.android.p065b;

import java.io.InputStream;
import java.util.Properties;

/* renamed from: com.crashlytics.android.b.d */
class C1371d {
    /* renamed from: a */
    public final String f4149a;
    /* renamed from: b */
    public final String f4150b;
    /* renamed from: c */
    public final String f4151c;
    /* renamed from: d */
    public final String f4152d;

    C1371d(String str, String str2, String str3, String str4) {
        this.f4149a = str;
        this.f4150b = str2;
        this.f4151c = str3;
        this.f4152d = str4;
    }

    /* renamed from: a */
    public static C1371d m6947a(InputStream inputStream) {
        Properties properties = new Properties();
        properties.load(inputStream);
        return C1371d.m6948a(properties);
    }

    /* renamed from: a */
    public static C1371d m6948a(Properties properties) {
        return new C1371d(properties.getProperty("version_code"), properties.getProperty("version_name"), properties.getProperty("build_id"), properties.getProperty("package_name"));
    }
}
