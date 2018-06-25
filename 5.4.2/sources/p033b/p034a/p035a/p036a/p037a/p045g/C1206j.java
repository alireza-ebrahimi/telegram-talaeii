package p033b.p034a.p035a.p036a.p037a.p045g;

import android.annotation.SuppressLint;
import android.content.SharedPreferences.Editor;
import org.json.JSONObject;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1113k;
import p033b.p034a.p035a.p036a.p037a.p044f.C1194c;
import p033b.p034a.p035a.p036a.p037a.p044f.C1195d;

/* renamed from: b.a.a.a.a.g.j */
class C1206j implements C1205s {
    /* renamed from: a */
    private final C1221w f3482a;
    /* renamed from: b */
    private final C1207v f3483b;
    /* renamed from: c */
    private final C1113k f3484c;
    /* renamed from: d */
    private final C1202g f3485d;
    /* renamed from: e */
    private final C1209x f3486e;
    /* renamed from: f */
    private final C1237i f3487f;
    /* renamed from: g */
    private final C1194c f3488g = new C1195d(this.f3487f);

    public C1206j(C1237i c1237i, C1221w c1221w, C1113k c1113k, C1207v c1207v, C1202g c1202g, C1209x c1209x) {
        this.f3487f = c1237i;
        this.f3482a = c1221w;
        this.f3484c = c1113k;
        this.f3483b = c1207v;
        this.f3485d = c1202g;
        this.f3486e = c1209x;
    }

    /* renamed from: a */
    private void m6333a(JSONObject jSONObject, String str) {
        C1230c.m6414h().mo1062a("Fabric", str + jSONObject.toString());
    }

    /* renamed from: b */
    private C1219t m6334b(C1218r c1218r) {
        Throwable th;
        C1219t c1219t = null;
        try {
            if (C1218r.SKIP_CACHE_LOOKUP.equals(c1218r)) {
                return null;
            }
            JSONObject a = this.f3485d.mo1055a();
            if (a != null) {
                C1219t a2 = this.f3483b.mo1059a(this.f3484c, a);
                if (a2 != null) {
                    m6333a(a, "Loaded cached settings: ");
                    long a3 = this.f3484c.mo1024a();
                    if (C1218r.IGNORE_CACHE_EXPIRATION.equals(c1218r) || !a2.m6367a(a3)) {
                        try {
                            C1230c.m6414h().mo1062a("Fabric", "Returning cached settings.");
                            return a2;
                        } catch (Throwable e) {
                            Throwable th2 = e;
                            c1219t = a2;
                            th = th2;
                            C1230c.m6414h().mo1070e("Fabric", "Failed to get cached settings", th);
                            return c1219t;
                        }
                    }
                    C1230c.m6414h().mo1062a("Fabric", "Cached settings have expired.");
                    return null;
                }
                C1230c.m6414h().mo1070e("Fabric", "Failed to transform cached settings data.", null);
                return null;
            }
            C1230c.m6414h().mo1062a("Fabric", "No cached settings data found.");
            return null;
        } catch (Exception e2) {
            th = e2;
            C1230c.m6414h().mo1070e("Fabric", "Failed to get cached settings", th);
            return c1219t;
        }
    }

    /* renamed from: a */
    public C1219t mo1057a() {
        return mo1058a(C1218r.USE_CACHE);
    }

    /* renamed from: a */
    public C1219t mo1058a(C1218r c1218r) {
        Throwable th;
        C1219t c1219t;
        Throwable th2;
        C1219t c1219t2 = null;
        try {
            if (!(C1230c.m6415i() || m6340d())) {
                c1219t2 = m6334b(c1218r);
            }
            if (c1219t2 == null) {
                try {
                    JSONObject a = this.f3486e.mo1060a(this.f3482a);
                    if (a != null) {
                        c1219t2 = this.f3483b.mo1059a(this.f3484c, a);
                        this.f3485d.mo1056a(c1219t2.f3526g, a);
                        m6333a(a, "Loaded settings: ");
                        m6337a(m6338b());
                    }
                } catch (Throwable e) {
                    th = e;
                    c1219t = c1219t2;
                    th2 = th;
                    C1230c.m6414h().mo1070e("Fabric", "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", th2);
                    return c1219t;
                }
            }
            c1219t = c1219t2;
            if (c1219t == null) {
                try {
                    c1219t = m6334b(C1218r.IGNORE_CACHE_EXPIRATION);
                } catch (Exception e2) {
                    th2 = e2;
                    C1230c.m6414h().mo1070e("Fabric", "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", th2);
                    return c1219t;
                }
            }
        } catch (Throwable e3) {
            th = e3;
            c1219t = null;
            th2 = th;
            C1230c.m6414h().mo1070e("Fabric", "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", th2);
            return c1219t;
        }
        return c1219t;
    }

    @SuppressLint({"CommitPrefEdits"})
    /* renamed from: a */
    boolean m6337a(String str) {
        Editor b = this.f3488g.mo1053b();
        b.putString("existing_instance_identifier", str);
        return this.f3488g.mo1052a(b);
    }

    /* renamed from: b */
    String m6338b() {
        return C1110i.m6006a(C1110i.m6034m(this.f3487f.m6452q()));
    }

    /* renamed from: c */
    String m6339c() {
        return this.f3488g.mo1051a().getString("existing_instance_identifier", TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: d */
    boolean m6340d() {
        return !m6339c().equals(m6338b());
    }
}
