package com.crashlytics.android.p065b;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p038a.C1094b;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1115m;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p.C1121a;
import p033b.p034a.p035a.p036a.p037a.p039b.C1131t;
import p033b.p034a.p035a.p036a.p037a.p043e.C1178b;
import p033b.p034a.p035a.p036a.p037a.p044f.C1195d;
import p033b.p034a.p035a.p036a.p037a.p045g.C1201f;
import p033b.p034a.p035a.p036a.p037a.p045g.C1217q;
import p033b.p034a.p035a.p036a.p037a.p045g.C1219t;

/* renamed from: com.crashlytics.android.b.c */
public class C1370c extends C1237i<Boolean> implements C1115m {
    /* renamed from: a */
    private final C1094b<String> f4146a = new C1094b();
    /* renamed from: b */
    private final C1375h f4147b = new C1375h();
    /* renamed from: c */
    private C1365j f4148c;

    /* renamed from: a */
    private C1371d m6936a(Context context) {
        C1371d a;
        Throwable th;
        Throwable e;
        Throwable th2;
        C1371d c1371d;
        InputStream inputStream = null;
        InputStream open;
        try {
            open = context.getAssets().open("crashlytics-build.properties");
            Object obj;
            if (open != null) {
                try {
                    a = C1371d.m6947a(open);
                } catch (Throwable e2) {
                    th = e2;
                    obj = inputStream;
                    th2 = th;
                    try {
                        C1230c.m6414h().mo1070e("Beta", "Error reading Beta build properties", th2);
                        if (open != null) {
                            try {
                                open.close();
                            } catch (Throwable th22) {
                                C1230c.m6414h().mo1070e("Beta", "Error closing Beta build properties asset", th22);
                            }
                        }
                        return c1371d;
                    } catch (Throwable th3) {
                        e2 = th3;
                        if (open != null) {
                            try {
                                open.close();
                            } catch (Throwable th222) {
                                C1230c.m6414h().mo1070e("Beta", "Error closing Beta build properties asset", th222);
                            }
                        }
                        throw e2;
                    }
                }
                try {
                    C1230c.m6414h().mo1062a("Beta", a.f4152d + " build properties: " + a.f4150b + " (" + a.f4149a + ") - " + a.f4151c);
                    c1371d = a;
                } catch (Throwable e22) {
                    th = e22;
                    c1371d = a;
                    th222 = th;
                    C1230c.m6414h().mo1070e("Beta", "Error reading Beta build properties", th222);
                    if (open != null) {
                        open.close();
                    }
                    return c1371d;
                }
            }
            obj = inputStream;
            if (open != null) {
                try {
                    open.close();
                } catch (Throwable th2222) {
                    C1230c.m6414h().mo1070e("Beta", "Error closing Beta build properties asset", th2222);
                }
            }
        } catch (Throwable e222) {
            open = inputStream;
            InputStream inputStream2 = inputStream;
            th2222 = e222;
            c1371d = inputStream2;
            C1230c.m6414h().mo1070e("Beta", "Error reading Beta build properties", th2222);
            if (open != null) {
                open.close();
            }
            return c1371d;
        } catch (Throwable th4) {
            e222 = th4;
            open = inputStream;
            if (open != null) {
                open.close();
            }
            throw e222;
        }
        return c1371d;
    }

    /* renamed from: a */
    private String m6937a(Context context, String str) {
        Object obj;
        try {
            obj = (String) this.f4146a.mo1017a(context, this.f4147b);
            if (TtmlNode.ANONYMOUS_REGION_ID.equals(obj)) {
                obj = null;
            }
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("Beta", "Failed to load the Beta device token", e);
            obj = null;
        }
        C1230c.m6414h().mo1062a("Beta", "Beta device token present: " + (!TextUtils.isEmpty(obj)));
        return obj;
    }

    /* renamed from: h */
    private C1201f m6938h() {
        C1219t b = C1217q.m6361a().m6364b();
        return b != null ? b.f3525f : null;
    }

    @TargetApi(14)
    /* renamed from: a */
    C1365j m6939a(int i, Application application) {
        return i >= 14 ? new C1369b(m6453r().m6425e(), m6453r().m6426f()) : new C1376i();
    }

    /* renamed from: a */
    public String mo1080a() {
        return "1.2.7.19";
    }

    /* renamed from: a */
    boolean m6941a(C1201f c1201f, C1371d c1371d) {
        return (c1201f == null || TextUtils.isEmpty(c1201f.f3479a) || c1371d == null) ? false : true;
    }

    /* renamed from: b */
    public String mo1081b() {
        return "com.crashlytics.sdk.android:beta";
    }

    /* renamed from: c */
    protected Boolean m6943c() {
        C1230c.m6414h().mo1062a("Beta", "Beta kit initializing...");
        Context q = m6452q();
        C1122p p = m6451p();
        if (TextUtils.isEmpty(m6937a(q, p.m6067i()))) {
            C1230c.m6414h().mo1062a("Beta", "A Beta device token was not found for this app");
            return Boolean.valueOf(false);
        }
        C1230c.m6414h().mo1062a("Beta", "Beta device token is present, checking for app updates.");
        C1201f h = m6938h();
        C1371d a = m6936a(q);
        if (m6941a(h, a)) {
            this.f4148c.mo1146a(q, this, p, h, a, new C1195d(this), new C1131t(), new C1178b(C1230c.m6414h()));
        }
        return Boolean.valueOf(true);
    }

    @TargetApi(14)
    protected boolean c_() {
        this.f4148c = m6939a(VERSION.SDK_INT, (Application) m6452q().getApplicationContext());
        return true;
    }

    /* renamed from: e */
    protected /* synthetic */ Object mo1083e() {
        return m6943c();
    }

    /* renamed from: f */
    public Map<C1121a, String> mo1147f() {
        CharSequence a = m6937a(m6452q(), m6451p().m6067i());
        Map<C1121a, String> hashMap = new HashMap();
        if (!TextUtils.isEmpty(a)) {
            hashMap.put(C1121a.FONT_TOKEN, a);
        }
        return hashMap;
    }

    /* renamed from: g */
    String m6946g() {
        return C1110i.m6017b(m6452q(), "com.crashlytics.ApiEndpoint");
    }
}
