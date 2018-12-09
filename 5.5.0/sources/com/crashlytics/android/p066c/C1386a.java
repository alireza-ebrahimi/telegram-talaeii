package com.crashlytics.android.p066c;

import android.content.Context;
import android.content.pm.PackageInfo;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p;

/* renamed from: com.crashlytics.android.c.a */
class C1386a {
    /* renamed from: a */
    public final String f4191a;
    /* renamed from: b */
    public final String f4192b;
    /* renamed from: c */
    public final String f4193c;
    /* renamed from: d */
    public final String f4194d;
    /* renamed from: e */
    public final String f4195e;
    /* renamed from: f */
    public final String f4196f;

    C1386a(String str, String str2, String str3, String str4, String str5, String str6) {
        this.f4191a = str;
        this.f4192b = str2;
        this.f4193c = str3;
        this.f4194d = str4;
        this.f4195e = str5;
        this.f4196f = str6;
    }

    /* renamed from: a */
    public static C1386a m6960a(Context context, C1122p c1122p, String str, String str2) {
        String packageName = context.getPackageName();
        String i = c1122p.m6067i();
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        return new C1386a(str, str2, i, packageName, Integer.toString(packageInfo.versionCode), packageInfo.versionName == null ? "0.0" : packageInfo.versionName);
    }
}
