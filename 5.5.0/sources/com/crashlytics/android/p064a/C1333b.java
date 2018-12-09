package com.crashlytics.android.p064a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import java.io.File;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1111j.C1112a;
import p033b.p034a.p035a.p036a.p037a.p039b.C1120o;
import p033b.p034a.p035a.p036a.p037a.p045g.C1217q;
import p033b.p034a.p035a.p036a.p037a.p045g.C1219t;

/* renamed from: com.crashlytics.android.a.b */
public class C1333b extends C1237i<Boolean> {
    /* renamed from: a */
    boolean f4056a = false;
    /* renamed from: b */
    ab f4057b;

    /* renamed from: a */
    private void m6817a(String str) {
        C1230c.m6414h().mo1067d("Answers", "Method " + str + " is not supported when using Crashlytics through Firebase.");
    }

    /* renamed from: c */
    public static C1333b m6818c() {
        return (C1333b) C1230c.m6405a(C1333b.class);
    }

    /* renamed from: a */
    public String mo1080a() {
        return "1.4.1.19";
    }

    /* renamed from: a */
    public void m6820a(C1112a c1112a) {
        if (this.f4057b != null) {
            this.f4057b.m6796a(c1112a.m6036a(), c1112a.m6037b());
        }
    }

    /* renamed from: a */
    public void m6821a(C1351m c1351m) {
        if (c1351m == null) {
            throw new NullPointerException("event must not be null");
        } else if (this.f4056a) {
            m6817a("logCustom");
        } else if (this.f4057b != null) {
            this.f4057b.m6795a(c1351m);
        }
    }

    /* renamed from: b */
    public String mo1081b() {
        return "com.crashlytics.sdk.android:answers";
    }

    @SuppressLint({"NewApi"})
    protected boolean c_() {
        try {
            Context q = m6452q();
            PackageManager packageManager = q.getPackageManager();
            String packageName = q.getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            this.f4057b = ab.m6790a(this, q, m6451p(), Integer.toString(packageInfo.versionCode), packageInfo.versionName == null ? "0.0" : packageInfo.versionName, VERSION.SDK_INT >= 9 ? packageInfo.firstInstallTime : new File(packageManager.getApplicationInfo(packageName, 0).sourceDir).lastModified());
            this.f4057b.m6797b();
            this.f4056a = new C1120o().m6051b(q);
            return true;
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("Answers", "Error retrieving app properties", e);
            return false;
        }
    }

    /* renamed from: e */
    protected /* synthetic */ Object mo1083e() {
        return m6824f();
    }

    /* renamed from: f */
    protected Boolean m6824f() {
        try {
            C1219t b = C1217q.m6361a().m6364b();
            if (b == null) {
                C1230c.m6414h().mo1069e("Answers", "Failed to retrieve settings");
                return Boolean.valueOf(false);
            } else if (b.f3523d.f3492d) {
                C1230c.m6414h().mo1062a("Answers", "Analytics collection enabled");
                this.f4057b.m6794a(b.f3524e, m6825g());
                return Boolean.valueOf(true);
            } else {
                C1230c.m6414h().mo1062a("Answers", "Analytics collection disabled");
                this.f4057b.m6798c();
                return Boolean.valueOf(false);
            }
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("Answers", "Error dealing with settings", e);
            return Boolean.valueOf(false);
        }
    }

    /* renamed from: g */
    String m6825g() {
        return C1110i.m6017b(m6452q(), "com.crashlytics.ApiEndpoint");
    }
}
