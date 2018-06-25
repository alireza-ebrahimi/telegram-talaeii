package p033b.p034a.p035a.p036a.p037a.p045g;

import android.text.TextUtils;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1096a;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p043e.C1179c;
import p033b.p034a.p035a.p036a.p037a.p043e.C1187d;
import p033b.p034a.p035a.p036a.p037a.p043e.C1187d.C1185c;

/* renamed from: b.a.a.a.a.g.l */
class C1210l extends C1096a implements C1209x {
    public C1210l(C1237i c1237i, String str, String str2, C1177e c1177e) {
        this(c1237i, str, str2, c1177e, C1179c.GET);
    }

    C1210l(C1237i c1237i, String str, String str2, C1177e c1177e, C1179c c1179c) {
        super(c1237i, str, str2, c1177e, c1179c);
    }

    /* renamed from: a */
    private C1187d m6352a(C1187d c1187d, C1221w c1221w) {
        m6354a(c1187d, "X-CRASHLYTICS-API-KEY", c1221w.f3530a);
        m6354a(c1187d, "X-CRASHLYTICS-API-CLIENT-TYPE", "android");
        m6354a(c1187d, "X-CRASHLYTICS-API-CLIENT-VERSION", this.a.mo1080a());
        m6354a(c1187d, "Accept", "application/json");
        m6354a(c1187d, "X-CRASHLYTICS-DEVICE-MODEL", c1221w.f3531b);
        m6354a(c1187d, "X-CRASHLYTICS-OS-BUILD-VERSION", c1221w.f3532c);
        m6354a(c1187d, "X-CRASHLYTICS-OS-DISPLAY-VERSION", c1221w.f3533d);
        m6354a(c1187d, "X-CRASHLYTICS-INSTALLATION-ID", c1221w.f3535f);
        if (TextUtils.isEmpty(c1221w.f3534e)) {
            m6354a(c1187d, "X-CRASHLYTICS-ANDROID-ID", c1221w.f3536g);
        } else {
            m6354a(c1187d, "X-CRASHLYTICS-ADVERTISING-TOKEN", c1221w.f3534e);
        }
        return c1187d;
    }

    /* renamed from: a */
    private JSONObject m6353a(String str) {
        try {
            return new JSONObject(str);
        } catch (Throwable e) {
            C1230c.m6414h().mo1063a("Fabric", "Failed to parse settings JSON from " + m5961a(), e);
            C1230c.m6414h().mo1062a("Fabric", "Settings response " + str);
            return null;
        }
    }

    /* renamed from: a */
    private void m6354a(C1187d c1187d, String str, String str2) {
        if (str2 != null) {
            c1187d.m6263a(str, str2);
        }
    }

    /* renamed from: b */
    private Map<String, String> m6355b(C1221w c1221w) {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("build_version", c1221w.f3539j);
        hashMap.put("display_version", c1221w.f3538i);
        hashMap.put(C1797b.SOURCE, Integer.toString(c1221w.f3540k));
        if (c1221w.f3541l != null) {
            hashMap.put("icon_hash", c1221w.f3541l);
        }
        String str = c1221w.f3537h;
        if (!C1110i.m6025d(str)) {
            hashMap.put("instance", str);
        }
        return hashMap;
    }

    /* renamed from: a */
    JSONObject m6356a(C1187d c1187d) {
        int b = c1187d.m6273b();
        C1230c.m6414h().mo1062a("Fabric", "Settings result was: " + b);
        if (m6358a(b)) {
            return m6353a(c1187d.m6284e());
        }
        C1230c.m6414h().mo1069e("Fabric", "Failed to retrieve settings from " + m5961a());
        return null;
    }

    /* renamed from: a */
    public JSONObject mo1060a(C1221w c1221w) {
        Throwable e;
        Throwable th;
        JSONObject jSONObject = null;
        C1187d a;
        try {
            Map b = m6355b(c1221w);
            try {
                a = m6352a(m5960a(b), c1221w);
                C1230c.m6414h().mo1062a("Fabric", "Requesting settings from " + m5961a());
                C1230c.m6414h().mo1062a("Fabric", "Settings query params were: " + b);
                jSONObject = m6356a(a);
                if (a != null) {
                    C1230c.m6414h().mo1062a("Fabric", "Settings request ID: " + a.m6275b("X-REQUEST-ID"));
                }
            } catch (C1185c e2) {
                e = e2;
                try {
                    C1230c.m6414h().mo1070e("Fabric", "Settings request failed.", e);
                    if (a != null) {
                        C1230c.m6414h().mo1062a("Fabric", "Settings request ID: " + a.m6275b("X-REQUEST-ID"));
                    }
                    return jSONObject;
                } catch (Throwable th2) {
                    th = th2;
                    if (a != null) {
                        C1230c.m6414h().mo1062a("Fabric", "Settings request ID: " + a.m6275b("X-REQUEST-ID"));
                    }
                    throw th;
                }
            }
        } catch (C1185c e3) {
            e = e3;
            a = jSONObject;
            C1230c.m6414h().mo1070e("Fabric", "Settings request failed.", e);
            if (a != null) {
                C1230c.m6414h().mo1062a("Fabric", "Settings request ID: " + a.m6275b("X-REQUEST-ID"));
            }
            return jSONObject;
        } catch (Throwable e4) {
            a = jSONObject;
            th = e4;
            if (a != null) {
                C1230c.m6414h().mo1062a("Fabric", "Settings request ID: " + a.m6275b("X-REQUEST-ID"));
            }
            throw th;
        }
        return jSONObject;
    }

    /* renamed from: a */
    boolean m6358a(int i) {
        return i == Callback.DEFAULT_DRAG_ANIMATION_DURATION || i == 201 || i == 202 || i == 203;
    }
}
