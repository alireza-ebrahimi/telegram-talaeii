package p033b.p034a.p035a.p036a.p037a.p045g;

import android.content.Context;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1107g;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1114l;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p;
import p033b.p034a.p035a.p036a.p037a.p039b.C1131t;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;

/* renamed from: b.a.a.a.a.g.q */
public class C1217q {
    /* renamed from: a */
    private final AtomicReference<C1219t> f3512a;
    /* renamed from: b */
    private final CountDownLatch f3513b;
    /* renamed from: c */
    private C1205s f3514c;
    /* renamed from: d */
    private boolean f3515d;

    /* renamed from: b.a.a.a.a.g.q$a */
    static class C1216a {
        /* renamed from: a */
        private static final C1217q f3511a = new C1217q();
    }

    private C1217q() {
        this.f3512a = new AtomicReference();
        this.f3513b = new CountDownLatch(1);
        this.f3515d = false;
    }

    /* renamed from: a */
    public static C1217q m6361a() {
        return C1216a.f3511a;
    }

    /* renamed from: a */
    private void m6362a(C1219t c1219t) {
        this.f3512a.set(c1219t);
        this.f3513b.countDown();
    }

    /* renamed from: a */
    public synchronized C1217q m6363a(C1237i c1237i, C1122p c1122p, C1177e c1177e, String str, String str2, String str3) {
        C1217q c1217q;
        if (this.f3515d) {
            c1217q = this;
        } else {
            if (this.f3514c == null) {
                Context q = c1237i.m6452q();
                String c = c1122p.m6061c();
                String a = new C1107g().m5986a(q);
                String i = c1122p.m6067i();
                C1131t c1131t = new C1131t();
                C1208k c1208k = new C1208k();
                C1204i c1204i = new C1204i(c1237i);
                String k = C1110i.m6032k(q);
                C1237i c1237i2 = c1237i;
                String str4 = str3;
                C1210l c1210l = new C1210l(c1237i2, str4, String.format(Locale.US, "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings", new Object[]{c}), c1177e);
                i = str2;
                String str5 = str;
                this.f3514c = new C1206j(c1237i, new C1221w(a, c1122p.m6065g(), c1122p.m6064f(), c1122p.m6063e(), c1122p.m6069k(), c1122p.m6060b(), c1122p.m6070l(), C1110i.m6006a(C1110i.m6034m(q)), i, str5, C1114l.m6039a(i).m6040a(), k), c1131t, c1208k, c1204i, c1210l);
            }
            this.f3515d = true;
            c1217q = this;
        }
        return c1217q;
    }

    /* renamed from: b */
    public C1219t m6364b() {
        try {
            this.f3513b.await();
            return (C1219t) this.f3512a.get();
        } catch (InterruptedException e) {
            C1230c.m6414h().mo1069e("Fabric", "Interrupted while waiting for settings data.");
            return null;
        }
    }

    /* renamed from: c */
    public synchronized boolean m6365c() {
        C1219t a;
        a = this.f3514c.mo1057a();
        m6362a(a);
        return a != null;
    }

    /* renamed from: d */
    public synchronized boolean m6366d() {
        C1219t a;
        a = this.f3514c.mo1058a(C1218r.SKIP_CACHE_LOOKUP);
        m6362a(a);
        if (a == null) {
            C1230c.m6414h().mo1070e("Fabric", "Failed to force reload of settings from Crashlytics.", null);
        }
        return a != null;
    }
}
