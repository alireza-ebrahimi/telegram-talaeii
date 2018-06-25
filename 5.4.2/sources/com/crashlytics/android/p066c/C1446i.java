package com.crashlytics.android.p066c;

import android.content.Context;
import android.util.Log;
import com.crashlytics.android.p066c.p067a.C1385a;
import com.crashlytics.android.p066c.p067a.p068a.C1381d;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1107g;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1119n;
import p033b.p034a.p035a.p036a.p037a.p039b.C1120o;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p;
import p033b.p034a.p035a.p036a.p037a.p040c.C1151d;
import p033b.p034a.p035a.p036a.p037a.p040c.C1152e;
import p033b.p034a.p035a.p036a.p037a.p040c.C1154l;
import p033b.p034a.p035a.p036a.p037a.p040c.C1160g;
import p033b.p034a.p035a.p036a.p037a.p040c.C1163m;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p043e.C1178b;
import p033b.p034a.p035a.p036a.p037a.p043e.C1189g;
import p033b.p034a.p035a.p036a.p037a.p044f.C1192a;
import p033b.p034a.p035a.p036a.p037a.p044f.C1193b;
import p033b.p034a.p035a.p036a.p037a.p044f.C1195d;
import p033b.p034a.p035a.p036a.p037a.p045g.C1217q;
import p033b.p034a.p035a.p036a.p037a.p045g.C1219t;

@C1151d(a = {C1385a.class})
/* renamed from: com.crashlytics.android.c.i */
public class C1446i extends C1237i<Void> {
    /* renamed from: a */
    private final long f4365a;
    /* renamed from: b */
    private final ConcurrentHashMap<String, String> f4366b;
    /* renamed from: c */
    private C1447j f4367c;
    /* renamed from: d */
    private C1447j f4368d;
    /* renamed from: k */
    private C1444k f4369k;
    /* renamed from: l */
    private C1438h f4370l;
    /* renamed from: m */
    private String f4371m;
    /* renamed from: n */
    private String f4372n;
    /* renamed from: o */
    private String f4373o;
    /* renamed from: p */
    private float f4374p;
    /* renamed from: q */
    private boolean f4375q;
    /* renamed from: r */
    private final aa f4376r;
    /* renamed from: s */
    private C1177e f4377s;
    /* renamed from: t */
    private C1409g f4378t;
    /* renamed from: u */
    private C1385a f4379u;

    /* renamed from: com.crashlytics.android.c.i$1 */
    class C14391 extends C1160g<Void> {
        /* renamed from: a */
        final /* synthetic */ C1446i f4360a;

        C14391(C1446i c1446i) {
            this.f4360a = c1446i;
        }

        /* renamed from: a */
        public Void m7207a() {
            return this.f4360a.m7222c();
        }

        /* renamed from: b */
        public C1152e mo1027b() {
            return C1152e.IMMEDIATE;
        }

        public /* synthetic */ Object call() {
            return m7207a();
        }
    }

    /* renamed from: com.crashlytics.android.c.i$2 */
    class C14402 implements Callable<Void> {
        /* renamed from: a */
        final /* synthetic */ C1446i f4361a;

        C14402(C1446i c1446i) {
            this.f4361a = c1446i;
        }

        /* renamed from: a */
        public Void m7209a() {
            this.f4361a.f4367c.m7234a();
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Initialization marker file created.");
            return null;
        }

        public /* synthetic */ Object call() {
            return m7209a();
        }
    }

    /* renamed from: com.crashlytics.android.c.i$3 */
    class C14413 implements Callable<Boolean> {
        /* renamed from: a */
        final /* synthetic */ C1446i f4362a;

        C14413(C1446i c1446i) {
            this.f4362a = c1446i;
        }

        /* renamed from: a */
        public Boolean m7210a() {
            try {
                boolean c = this.f4362a.f4367c.m7236c();
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Initialization marker file removed: " + c);
                return Boolean.valueOf(c);
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("CrashlyticsCore", "Problem encountered deleting Crashlytics initialization marker.", e);
                return Boolean.valueOf(false);
            }
        }

        public /* synthetic */ Object call() {
            return m7210a();
        }
    }

    /* renamed from: com.crashlytics.android.c.i$4 */
    class C14424 implements Callable<Boolean> {
        /* renamed from: a */
        final /* synthetic */ C1446i f4363a;

        C14424(C1446i c1446i) {
            this.f4363a = c1446i;
        }

        /* renamed from: a */
        public Boolean m7211a() {
            return Boolean.valueOf(this.f4363a.f4367c.m7235b());
        }

        public /* synthetic */ Object call() {
            return m7211a();
        }
    }

    /* renamed from: com.crashlytics.android.c.i$a */
    private static final class C1443a implements Callable<Boolean> {
        /* renamed from: a */
        private final C1447j f4364a;

        public C1443a(C1447j c1447j) {
            this.f4364a = c1447j;
        }

        /* renamed from: a */
        public Boolean m7212a() {
            if (!this.f4364a.m7235b()) {
                return Boolean.FALSE;
            }
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Found previous crash marker.");
            this.f4364a.m7236c();
            return Boolean.TRUE;
        }

        public /* synthetic */ Object call() {
            return m7212a();
        }
    }

    /* renamed from: com.crashlytics.android.c.i$b */
    private static final class C1445b implements C1444k {
        private C1445b() {
        }

        /* renamed from: a */
        public void mo1169a() {
        }
    }

    public C1446i() {
        this(1.0f, null, null, false);
    }

    C1446i(float f, C1444k c1444k, aa aaVar, boolean z) {
        this(f, c1444k, aaVar, z, C1119n.m6044a("Crashlytics Exception Handler"));
    }

    C1446i(float f, C1444k c1444k, aa aaVar, boolean z, ExecutorService executorService) {
        this.f4371m = null;
        this.f4372n = null;
        this.f4373o = null;
        this.f4374p = f;
        if (c1444k == null) {
            c1444k = new C1445b();
        }
        this.f4369k = c1444k;
        this.f4376r = aaVar;
        this.f4375q = z;
        this.f4378t = new C1409g(executorService);
        this.f4366b = new ConcurrentHashMap();
        this.f4365a = System.currentTimeMillis();
    }

    /* renamed from: a */
    static boolean m7216a(String str, boolean z) {
        if (!z) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "Configured not to require a build ID.");
            return true;
        } else if (!C1110i.m6025d(str)) {
            return true;
        } else {
            Log.e("CrashlyticsCore", ".");
            Log.e("CrashlyticsCore", ".     |  | ");
            Log.e("CrashlyticsCore", ".     |  |");
            Log.e("CrashlyticsCore", ".     |  |");
            Log.e("CrashlyticsCore", ".   \\ |  | /");
            Log.e("CrashlyticsCore", ".    \\    /");
            Log.e("CrashlyticsCore", ".     \\  /");
            Log.e("CrashlyticsCore", ".      \\/");
            Log.e("CrashlyticsCore", ".");
            Log.e("CrashlyticsCore", "This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.");
            Log.e("CrashlyticsCore", ".");
            Log.e("CrashlyticsCore", ".      /\\");
            Log.e("CrashlyticsCore", ".     /  \\");
            Log.e("CrashlyticsCore", ".    /    \\");
            Log.e("CrashlyticsCore", ".   / |  | \\");
            Log.e("CrashlyticsCore", ".     |  |");
            Log.e("CrashlyticsCore", ".     |  |");
            Log.e("CrashlyticsCore", ".     |  |");
            Log.e("CrashlyticsCore", ".");
            return false;
        }
    }

    /* renamed from: v */
    private void m7217v() {
        Callable c14391 = new C14391(this);
        for (C1154l a : m6456u()) {
            c14391.m6171a(a);
        }
        Future submit = m6453r().m6426f().submit(c14391);
        C1230c.m6414h().mo1062a("CrashlyticsCore", "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
        try {
            submit.get(4, TimeUnit.SECONDS);
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("CrashlyticsCore", "Crashlytics was interrupted during initialization.", e);
        } catch (Throwable e2) {
            C1230c.m6414h().mo1070e("CrashlyticsCore", "Problem encountered during Crashlytics initialization.", e2);
        } catch (Throwable e22) {
            C1230c.m6414h().mo1070e("CrashlyticsCore", "Crashlytics timed out during initialization.", e22);
        }
    }

    /* renamed from: w */
    private void m7218w() {
        if (Boolean.TRUE.equals((Boolean) this.f4378t.m7107a(new C1443a(this.f4368d)))) {
            try {
                this.f4369k.mo1169a();
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("CrashlyticsCore", "Exception thrown by CrashlyticsListener while notifying of previous crash.", e);
            }
        }
    }

    /* renamed from: a */
    public String mo1080a() {
        return "2.4.1.19";
    }

    /* renamed from: a */
    boolean m7220a(Context context) {
        if (this.f4375q) {
            return false;
        }
        String a = new C1107g().m5986a(context);
        if (a == null) {
            return false;
        }
        String m = C1110i.m6034m(context);
        if (C1446i.m7216a(m, C1110i.m6014a(context, "com.crashlytics.RequireBuildId", true))) {
            try {
                C1230c.m6414h().mo1066c("CrashlyticsCore", "Initializing Crashlytics " + mo1080a());
                C1192a c1193b = new C1193b(this);
                this.f4368d = new C1447j("crash_marker", c1193b);
                this.f4367c = new C1447j("initialization_marker", c1193b);
                ab a2 = ab.m6964a(new C1195d(m6452q(), "com.crashlytics.android.core.CrashlyticsCore"), this);
                C1189g c1448l = this.f4376r != null ? new C1448l(this.f4376r) : null;
                this.f4377s = new C1178b(C1230c.m6414h());
                this.f4377s.mo1045a(c1448l);
                C1122p p = m6451p();
                C1386a a3 = C1386a.m6960a(context, p, a, m);
                ak c1461v = new C1461v(context, a3.f4194d);
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Installer package name is: " + a3.f4193c);
                this.f4370l = new C1438h(this, this.f4378t, this.f4377s, p, a2, c1193b, a3, c1461v, new C1120o().m6051b(context));
                boolean l = m7230l();
                m7218w();
                this.f4370l.m7194a(Thread.getDefaultUncaughtExceptionHandler());
                if (l && C1110i.m6035n(context)) {
                    C1230c.m6414h().mo1062a("CrashlyticsCore", "Crashlytics did not finish previous background initialization. Initializing synchronously.");
                    m7217v();
                    return false;
                }
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Exception handling initialization successful");
                return true;
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("CrashlyticsCore", "Crashlytics was not started due to an exception during initialization", e);
                this.f4370l = null;
                return false;
            }
        }
        throw new C1163m("This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.");
    }

    /* renamed from: b */
    public String mo1081b() {
        return "com.crashlytics.sdk.android.crashlytics-core";
    }

    /* renamed from: c */
    protected Void m7222c() {
        m7228j();
        C1381d m = m7231m();
        if (m != null) {
            this.f4370l.m7193a(m);
        }
        this.f4370l.m7201d();
        try {
            C1219t b = C1217q.m6361a().m6364b();
            if (b == null) {
                C1230c.m6414h().mo1067d("CrashlyticsCore", "Received null settings, skipping report submission!");
            } else if (b.f3523d.f3491c) {
                if (!this.f4370l.m7197a(b.f3521b)) {
                    C1230c.m6414h().mo1062a("CrashlyticsCore", "Could not finalize previous sessions.");
                }
                this.f4370l.m7191a(this.f4374p, b);
                m7229k();
            } else {
                C1230c.m6414h().mo1062a("CrashlyticsCore", "Collection of crash reports disabled in Crashlytics settings.");
                m7229k();
            }
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("CrashlyticsCore", "Crashlytics encountered a problem during asynchronous initialization.", e);
        } finally {
            m7229k();
        }
        return null;
    }

    protected boolean c_() {
        return m7220a(super.m6452q());
    }

    /* renamed from: e */
    protected /* synthetic */ Object mo1083e() {
        return m7222c();
    }

    /* renamed from: f */
    Map<String, String> m7224f() {
        return Collections.unmodifiableMap(this.f4366b);
    }

    /* renamed from: g */
    String m7225g() {
        return m6451p().m6059a() ? this.f4371m : null;
    }

    /* renamed from: h */
    String m7226h() {
        return m6451p().m6059a() ? this.f4372n : null;
    }

    /* renamed from: i */
    String m7227i() {
        return m6451p().m6059a() ? this.f4373o : null;
    }

    /* renamed from: j */
    void m7228j() {
        this.f4378t.m7107a(new C14402(this));
    }

    /* renamed from: k */
    void m7229k() {
        this.f4378t.m7109b(new C14413(this));
    }

    /* renamed from: l */
    boolean m7230l() {
        return ((Boolean) this.f4378t.m7107a(new C14424(this))).booleanValue();
    }

    /* renamed from: m */
    C1381d m7231m() {
        return this.f4379u != null ? this.f4379u.m6959a() : null;
    }

    /* renamed from: n */
    void m7232n() {
        this.f4368d.m7234a();
    }
}
