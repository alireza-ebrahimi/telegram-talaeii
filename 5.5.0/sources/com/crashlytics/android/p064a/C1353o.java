package com.crashlytics.android.p064a;

import android.content.Context;
import com.crashlytics.android.p064a.ad.C1331a;
import com.crashlytics.android.p064a.ad.C1332b;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1107g;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p042d.C1171f;
import p033b.p034a.p035a.p036a.p037a.p042d.C1174i;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p045g.C1197b;

/* renamed from: com.crashlytics.android.a.o */
class C1353o implements ac {
    /* renamed from: a */
    final ae f4097a;
    /* renamed from: b */
    C1171f f4098b;
    /* renamed from: c */
    C1107g f4099c = new C1107g();
    /* renamed from: d */
    C1354p f4100d = new C1358u();
    /* renamed from: e */
    boolean f4101e = true;
    /* renamed from: f */
    boolean f4102f = true;
    /* renamed from: g */
    volatile int f4103g = -1;
    /* renamed from: h */
    boolean f4104h = false;
    /* renamed from: i */
    boolean f4105i = false;
    /* renamed from: j */
    private final C1237i f4106j;
    /* renamed from: k */
    private final C1177e f4107k;
    /* renamed from: l */
    private final Context f4108l;
    /* renamed from: m */
    private final C1363z f4109m;
    /* renamed from: n */
    private final ScheduledExecutorService f4110n;
    /* renamed from: o */
    private final AtomicReference<ScheduledFuture<?>> f4111o = new AtomicReference();
    /* renamed from: p */
    private final C1355r f4112p;

    public C1353o(C1237i c1237i, Context context, ScheduledExecutorService scheduledExecutorService, C1363z c1363z, C1177e c1177e, ae aeVar, C1355r c1355r) {
        this.f4106j = c1237i;
        this.f4108l = context;
        this.f4110n = scheduledExecutorService;
        this.f4109m = c1363z;
        this.f4107k = c1177e;
        this.f4097a = aeVar;
        this.f4112p = c1355r;
    }

    /* renamed from: a */
    public void mo1135a() {
        if (this.f4098b == null) {
            C1110i.m6008a(this.f4108l, "skipping files send because we don't yet know the target endpoint");
            return;
        }
        C1110i.m6008a(this.f4108l, "Sending all files");
        List e = this.f4109m.m6194e();
        int i = 0;
        while (e.size() > 0) {
            int size;
            C1110i.m6008a(this.f4108l, String.format(Locale.US, "attempt to send batch of %d files", new Object[]{Integer.valueOf(e.size())}));
            boolean a = this.f4098b.mo1125a(e);
            if (a) {
                size = e.size() + i;
                try {
                    this.f4109m.m6190a(e);
                    i = size;
                } catch (Exception e2) {
                    Throwable e3 = e2;
                }
            }
            if (!a) {
                break;
            }
            try {
                e = this.f4109m.m6194e();
            } catch (Throwable e4) {
                Throwable th = e4;
                size = i;
                e3 = th;
            }
        }
        if (i == 0) {
            this.f4109m.m6196g();
        }
        C1110i.m6009a(this.f4108l, "Failed to send batch of analytics files to server: " + e3.getMessage(), e3);
        i = size;
        if (i == 0) {
            this.f4109m.m6196g();
        }
    }

    /* renamed from: a */
    void m6885a(long j, long j2) {
        if ((this.f4111o.get() == null ? 1 : null) != null) {
            Runnable c1174i = new C1174i(this.f4108l, this);
            C1110i.m6008a(this.f4108l, "Scheduling time based file roll over every " + j2 + " seconds");
            try {
                this.f4111o.set(this.f4110n.scheduleAtFixedRate(c1174i, j, j2, TimeUnit.SECONDS));
            } catch (Throwable e) {
                C1110i.m6009a(this.f4108l, "Failed to schedule time based file roll over", e);
            }
        }
    }

    /* renamed from: a */
    public void mo1136a(C1197b c1197b, String str) {
        this.f4098b = C1346j.m6861a(new aa(this.f4106j, str, c1197b.f3447a, this.f4107k, this.f4099c.m5986a(this.f4108l)));
        this.f4109m.m6918a(c1197b);
        this.f4104h = c1197b.f3452f;
        this.f4105i = c1197b.f3453g;
        C1230c.m6414h().mo1062a("Answers", "Firebase analytics forwarding " + (this.f4104h ? "enabled" : "disabled"));
        C1230c.m6414h().mo1062a("Answers", "Firebase analytics including purchase events " + (this.f4105i ? "enabled" : "disabled"));
        this.f4101e = c1197b.f3454h;
        C1230c.m6414h().mo1062a("Answers", "Custom event tracking " + (this.f4101e ? "enabled" : "disabled"));
        this.f4102f = c1197b.f3455i;
        C1230c.m6414h().mo1062a("Answers", "Predefined event tracking " + (this.f4102f ? "enabled" : "disabled"));
        if (c1197b.f3457k > 1) {
            C1230c.m6414h().mo1062a("Answers", "Event sampling enabled");
            this.f4100d = new C1362y(c1197b.f3457k);
        }
        this.f4103g = c1197b.f3448b;
        m6885a(0, (long) this.f4103g);
    }

    /* renamed from: a */
    public void mo1137a(C1331a c1331a) {
        ad a = c1331a.m6806a(this.f4097a);
        if (!this.f4101e && C1332b.CUSTOM.equals(a.f4032c)) {
            C1230c.m6414h().mo1062a("Answers", "Custom events tracking disabled - skipping event: " + a);
        } else if (!this.f4102f && C1332b.PREDEFINED.equals(a.f4032c)) {
            C1230c.m6414h().mo1062a("Answers", "Predefined events tracking disabled - skipping event: " + a);
        } else if (this.f4100d.mo1141a(a)) {
            C1230c.m6414h().mo1062a("Answers", "Skipping filtered event: " + a);
        } else {
            try {
                this.f4109m.m6189a((Object) a);
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("Answers", "Failed to write event: " + a, e);
            }
            m6891e();
            Object obj = (C1332b.CUSTOM.equals(a.f4032c) || C1332b.PREDEFINED.equals(a.f4032c)) ? 1 : null;
            boolean equals = "purchase".equals(a.f4036g);
            if (this.f4104h && obj != null) {
                if (!equals || this.f4105i) {
                    try {
                        this.f4112p.m6894a(a);
                    } catch (Throwable e2) {
                        C1230c.m6414h().mo1070e("Answers", "Failed to map event to Firebase: " + a, e2);
                    }
                }
            }
        }
    }

    /* renamed from: b */
    public void mo1138b() {
        this.f4109m.m6195f();
    }

    /* renamed from: c */
    public boolean mo1139c() {
        try {
            return this.f4109m.m6193d();
        } catch (Throwable e) {
            C1110i.m6009a(this.f4108l, "Failed to roll file over.", e);
            return false;
        }
    }

    /* renamed from: d */
    public void mo1140d() {
        if (this.f4111o.get() != null) {
            C1110i.m6008a(this.f4108l, "Cancelling time-based rollover because no events are currently being generated.");
            ((ScheduledFuture) this.f4111o.get()).cancel(false);
            this.f4111o.set(null);
        }
    }

    /* renamed from: e */
    public void m6891e() {
        if ((this.f4103g != -1 ? 1 : null) != null) {
            m6885a((long) this.f4103g, (long) this.f4103g);
        }
    }
}
