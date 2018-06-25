package com.crashlytics.android.p064a;

import android.content.Context;
import com.crashlytics.android.p064a.ad.C1331a;
import java.util.concurrent.ScheduledExecutorService;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p042d.C1169d;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p045g.C1197b;

/* renamed from: com.crashlytics.android.a.f */
class C1342f implements C1169d {
    /* renamed from: a */
    final ScheduledExecutorService f4073a;
    /* renamed from: b */
    ac f4074b = new C1352n();
    /* renamed from: c */
    private final C1237i f4075c;
    /* renamed from: d */
    private final Context f4076d;
    /* renamed from: e */
    private final C1343g f4077e;
    /* renamed from: f */
    private final ag f4078f;
    /* renamed from: g */
    private final C1177e f4079g;
    /* renamed from: h */
    private final C1355r f4080h;

    /* renamed from: com.crashlytics.android.a.f$2 */
    class C13372 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C1342f f4066a;

        C13372(C1342f c1342f) {
            this.f4066a = c1342f;
        }

        public void run() {
            try {
                ac acVar = this.f4066a.f4074b;
                this.f4066a.f4074b = new C1352n();
                acVar.mo1138b();
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("Answers", "Failed to disable events", e);
            }
        }
    }

    /* renamed from: com.crashlytics.android.a.f$3 */
    class C13383 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C1342f f4067a;

        C13383(C1342f c1342f) {
            this.f4067a = c1342f;
        }

        public void run() {
            try {
                this.f4067a.f4074b.mo1135a();
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("Answers", "Failed to send events files", e);
            }
        }
    }

    /* renamed from: com.crashlytics.android.a.f$4 */
    class C13394 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C1342f f4068a;

        C13394(C1342f c1342f) {
            this.f4068a = c1342f;
        }

        public void run() {
            try {
                ae a = this.f4068a.f4078f.m6816a();
                C1363z a2 = this.f4068a.f4077e.m6850a();
                a2.m6188a(this.f4068a);
                this.f4068a.f4074b = new C1353o(this.f4068a.f4075c, this.f4068a.f4076d, this.f4068a.f4073a, a2, this.f4068a.f4079g, a, this.f4068a.f4080h);
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("Answers", "Failed to enable events", e);
            }
        }
    }

    /* renamed from: com.crashlytics.android.a.f$5 */
    class C13405 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C1342f f4069a;

        C13405(C1342f c1342f) {
            this.f4069a = c1342f;
        }

        public void run() {
            try {
                this.f4069a.f4074b.mo1139c();
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("Answers", "Failed to flush events", e);
            }
        }
    }

    public C1342f(C1237i c1237i, Context context, C1343g c1343g, ag agVar, C1177e c1177e, ScheduledExecutorService scheduledExecutorService, C1355r c1355r) {
        this.f4075c = c1237i;
        this.f4076d = context;
        this.f4077e = c1343g;
        this.f4078f = agVar;
        this.f4079g = c1177e;
        this.f4073a = scheduledExecutorService;
        this.f4080h = c1355r;
    }

    /* renamed from: a */
    private void m6834a(Runnable runnable) {
        try {
            this.f4073a.submit(runnable).get();
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("Answers", "Failed to run events task", e);
        }
    }

    /* renamed from: b */
    private void m6836b(Runnable runnable) {
        try {
            this.f4073a.submit(runnable);
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("Answers", "Failed to submit events task", e);
        }
    }

    /* renamed from: a */
    public void m6841a() {
        m6836b(new C13372(this));
    }

    /* renamed from: a */
    public void m6842a(final C1197b c1197b, final String str) {
        m6836b(new Runnable(this) {
            /* renamed from: c */
            final /* synthetic */ C1342f f4065c;

            public void run() {
                try {
                    this.f4065c.f4074b.mo1136a(c1197b, str);
                } catch (Throwable e) {
                    C1230c.m6414h().mo1070e("Answers", "Failed to set analytics settings data", e);
                }
            }
        });
    }

    /* renamed from: a */
    public void m6843a(C1331a c1331a) {
        m6844a(c1331a, false, false);
    }

    /* renamed from: a */
    void m6844a(final C1331a c1331a, boolean z, final boolean z2) {
        Runnable c13416 = new Runnable(this) {
            /* renamed from: c */
            final /* synthetic */ C1342f f4072c;

            public void run() {
                try {
                    this.f4072c.f4074b.mo1137a(c1331a);
                    if (z2) {
                        this.f4072c.f4074b.mo1139c();
                    }
                } catch (Throwable e) {
                    C1230c.m6414h().mo1070e("Answers", "Failed to process event", e);
                }
            }
        };
        if (z) {
            m6834a(c13416);
        } else {
            m6836b(c13416);
        }
    }

    /* renamed from: a */
    public void mo1128a(String str) {
        m6836b(new C13383(this));
    }

    /* renamed from: b */
    public void m6846b() {
        m6836b(new C13394(this));
    }

    /* renamed from: b */
    public void m6847b(C1331a c1331a) {
        m6844a(c1331a, false, true);
    }

    /* renamed from: c */
    public void m6848c() {
        m6836b(new C13405(this));
    }

    /* renamed from: c */
    public void m6849c(C1331a c1331a) {
        m6844a(c1331a, true, false);
    }
}
