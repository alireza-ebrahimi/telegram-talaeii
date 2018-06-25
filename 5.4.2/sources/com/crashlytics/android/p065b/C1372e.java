package com.crashlytics.android.p065b;

import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1096a;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p043e.C1179c;
import p033b.p034a.p035a.p036a.p037a.p043e.C1187d;

/* renamed from: com.crashlytics.android.b.e */
class C1372e extends C1096a {
    /* renamed from: b */
    private final C1374g f4153b;

    public C1372e(C1237i c1237i, String str, String str2, C1177e c1177e, C1374g c1374g) {
        super(c1237i, str, str2, c1177e, C1179c.GET);
        this.f4153b = c1374g;
    }

    /* renamed from: a */
    private C1187d m6949a(C1187d c1187d, String str, String str2) {
        return c1187d.m6263a("Accept", "application/json").m6263a("User-Agent", "Crashlytics Android SDK/" + this.a.mo1080a()).m6263a("X-CRASHLYTICS-DEVELOPER-TOKEN", "470fa2b4ae81cd56ecbcda9735803434cec591fa").m6263a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").m6263a("X-CRASHLYTICS-API-CLIENT-VERSION", this.a.mo1080a()).m6263a("X-CRASHLYTICS-API-KEY", str).m6263a("X-CRASHLYTICS-BETA-TOKEN", C1372e.m6950a(str2));
    }

    /* renamed from: a */
    static String m6950a(String str) {
        return "3:" + str;
    }

    /* renamed from: a */
    private Map<String, String> m6951a(C1371d c1371d) {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("build_version", c1371d.f4149a);
        hashMap.put("display_version", c1371d.f4150b);
        hashMap.put("instance", c1371d.f4151c);
        hashMap.put(C1797b.SOURCE, "3");
        return hashMap;
    }

    /* renamed from: a */
    public C1373f m6952a(String str, String str2, C1371d c1371d) {
        C1187d a;
        Throwable e;
        Throwable th;
        C1373f c1373f = null;
        try {
            Map a2 = m6951a(c1371d);
            try {
                a = m6949a(m5960a(a2), str, str2);
                C1230c.m6414h().mo1062a("Beta", "Checking for updates from " + m5961a());
                C1230c.m6414h().mo1062a("Beta", "Checking for updates query params are: " + a2);
                if (a.m6279c()) {
                    C1230c.m6414h().mo1062a("Beta", "Checking for updates was successful");
                    c1373f = this.f4153b.m6953a(new JSONObject(a.m6284e()));
                    if (a != null) {
                        C1230c.m6414h().mo1062a("Fabric", "Checking for updates request ID: " + a.m6275b("X-REQUEST-ID"));
                    }
                } else {
                    C1230c.m6414h().mo1069e("Beta", "Checking for updates failed. Response code: " + a.m6273b());
                    if (a != null) {
                        C1230c.m6414h().mo1062a("Fabric", "Checking for updates request ID: " + a.m6275b("X-REQUEST-ID"));
                    }
                }
            } catch (Exception e2) {
                e = e2;
                try {
                    C1230c.m6414h().mo1070e("Beta", "Error while checking for updates from " + m5961a(), e);
                    if (a != null) {
                        C1230c.m6414h().mo1062a("Fabric", "Checking for updates request ID: " + a.m6275b("X-REQUEST-ID"));
                    }
                    return c1373f;
                } catch (Throwable th2) {
                    th = th2;
                    if (a != null) {
                        C1230c.m6414h().mo1062a("Fabric", "Checking for updates request ID: " + a.m6275b("X-REQUEST-ID"));
                    }
                    throw th;
                }
            }
        } catch (Exception e3) {
            e = e3;
            a = null;
            C1230c.m6414h().mo1070e("Beta", "Error while checking for updates from " + m5961a(), e);
            if (a != null) {
                C1230c.m6414h().mo1062a("Fabric", "Checking for updates request ID: " + a.m6275b("X-REQUEST-ID"));
            }
            return c1373f;
        } catch (Throwable e4) {
            a = null;
            th = e4;
            if (a != null) {
                C1230c.m6414h().mo1062a("Fabric", "Checking for updates request ID: " + a.m6275b("X-REQUEST-ID"));
            }
            throw th;
        }
        return c1373f;
    }
}
