package p033b.p034a.p035a.p036a;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import p033b.p034a.p035a.p036a.p037a.p039b.C1107g;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1114l;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p043e.C1178b;
import p033b.p034a.p035a.p036a.p037a.p045g.C1199d;
import p033b.p034a.p035a.p036a.p037a.p045g.C1200e;
import p033b.p034a.p035a.p036a.p037a.p045g.C1203h;
import p033b.p034a.p035a.p036a.p037a.p045g.C1212n;
import p033b.p034a.p035a.p036a.p037a.p045g.C1217q;
import p033b.p034a.p035a.p036a.p037a.p045g.C1219t;
import p033b.p034a.p035a.p036a.p037a.p045g.C1222y;

/* renamed from: b.a.a.a.m */
class C1240m extends C1237i<Boolean> {
    /* renamed from: a */
    private final C1177e f3586a = new C1178b();
    /* renamed from: b */
    private PackageManager f3587b;
    /* renamed from: c */
    private String f3588c;
    /* renamed from: d */
    private PackageInfo f3589d;
    /* renamed from: k */
    private String f3590k;
    /* renamed from: l */
    private String f3591l;
    /* renamed from: m */
    private String f3592m;
    /* renamed from: n */
    private String f3593n;
    /* renamed from: o */
    private String f3594o;
    /* renamed from: p */
    private final Future<Map<String, C1239k>> f3595p;
    /* renamed from: q */
    private final Collection<C1237i> f3596q;

    public C1240m(Future<Map<String, C1239k>> future, Collection<C1237i> collection) {
        this.f3595p = future;
        this.f3596q = collection;
    }

    /* renamed from: a */
    private C1199d m6461a(C1212n c1212n, Collection<C1239k> collection) {
        return new C1199d(new C1107g().m5986a(m6452q()), m6451p().m6061c(), this.f3591l, this.f3590k, C1110i.m6006a(C1110i.m6034m(r0)), this.f3593n, C1114l.m6039a(this.f3592m).m6040a(), this.f3594o, "0", c1212n, collection);
    }

    /* renamed from: a */
    private boolean m6462a(C1200e c1200e, C1212n c1212n, Collection<C1239k> collection) {
        return new C1222y(this, m6472f(), c1200e.f3474c, this.f3586a).mo1054a(m6461a(c1212n, (Collection) collection));
    }

    /* renamed from: a */
    private boolean m6463a(String str, C1200e c1200e, Collection<C1239k> collection) {
        if ("new".equals(c1200e.f3473b)) {
            if (m6464b(str, c1200e, collection)) {
                return C1217q.m6361a().m6366d();
            }
            C1230c.m6414h().mo1070e("Fabric", "Failed to create app with Crashlytics service.", null);
            return false;
        } else if ("configured".equals(c1200e.f3473b)) {
            return C1217q.m6361a().m6366d();
        } else {
            if (!c1200e.f3477f) {
                return true;
            }
            C1230c.m6414h().mo1062a("Fabric", "Server says an update is required - forcing a full App update.");
            m6465c(str, c1200e, collection);
            return true;
        }
    }

    /* renamed from: b */
    private boolean m6464b(String str, C1200e c1200e, Collection<C1239k> collection) {
        return new C1203h(this, m6472f(), c1200e.f3474c, this.f3586a).mo1054a(m6461a(C1212n.m6359a(m6452q(), str), (Collection) collection));
    }

    /* renamed from: c */
    private boolean m6465c(String str, C1200e c1200e, Collection<C1239k> collection) {
        return m6462a(c1200e, C1212n.m6359a(m6452q(), str), (Collection) collection);
    }

    /* renamed from: g */
    private C1219t m6466g() {
        try {
            C1217q.m6361a().m6363a(this, this.i, this.f3586a, this.f3590k, this.f3591l, m6472f()).m6365c();
            return C1217q.m6361a().m6364b();
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("Fabric", "Error dealing with settings", e);
            return null;
        }
    }

    /* renamed from: a */
    public String mo1080a() {
        return "1.4.1.19";
    }

    /* renamed from: a */
    Map<String, C1239k> m6468a(Map<String, C1239k> map, Collection<C1237i> collection) {
        for (C1237i c1237i : collection) {
            if (!map.containsKey(c1237i.mo1081b())) {
                map.put(c1237i.mo1081b(), new C1239k(c1237i.mo1081b(), c1237i.mo1080a(), "binary"));
            }
        }
        return map;
    }

    /* renamed from: b */
    public String mo1081b() {
        return "io.fabric.sdk.android:fabric";
    }

    /* renamed from: c */
    protected Boolean m6470c() {
        boolean a;
        String k = C1110i.m6032k(m6452q());
        C1219t g = m6466g();
        if (g != null) {
            try {
                a = m6463a(k, g.f3520a, m6468a(this.f3595p != null ? (Map) this.f3595p.get() : new HashMap(), this.f3596q).values());
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("Fabric", "Error performing auto configuration.", e);
            }
            return Boolean.valueOf(a);
        }
        a = false;
        return Boolean.valueOf(a);
    }

    protected boolean c_() {
        try {
            this.f3592m = m6451p().m6067i();
            this.f3587b = m6452q().getPackageManager();
            this.f3588c = m6452q().getPackageName();
            this.f3589d = this.f3587b.getPackageInfo(this.f3588c, 0);
            this.f3590k = Integer.toString(this.f3589d.versionCode);
            this.f3591l = this.f3589d.versionName == null ? "0.0" : this.f3589d.versionName;
            this.f3593n = this.f3587b.getApplicationLabel(m6452q().getApplicationInfo()).toString();
            this.f3594o = Integer.toString(m6452q().getApplicationInfo().targetSdkVersion);
            return true;
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("Fabric", "Failed init", e);
            return false;
        }
    }

    /* renamed from: e */
    protected /* synthetic */ Object mo1083e() {
        return m6470c();
    }

    /* renamed from: f */
    String m6472f() {
        return C1110i.m6017b(m6452q(), "com.crashlytics.ApiEndpoint");
    }
}
