package com.crashlytics.android.p065b;

import android.annotation.SuppressLint;
import android.content.Context;
import java.util.concurrent.atomic.AtomicBoolean;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.p037a.p039b.C1107g;
import p033b.p034a.p035a.p036a.p037a.p039b.C1113k;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p.C1121a;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p044f.C1194c;
import p033b.p034a.p035a.p036a.p037a.p045g.C1201f;

/* renamed from: com.crashlytics.android.b.a */
abstract class C1366a implements C1365j {
    /* renamed from: a */
    private final AtomicBoolean f4131a;
    /* renamed from: b */
    private final AtomicBoolean f4132b;
    /* renamed from: c */
    private Context f4133c;
    /* renamed from: d */
    private C1370c f4134d;
    /* renamed from: e */
    private C1122p f4135e;
    /* renamed from: f */
    private C1201f f4136f;
    /* renamed from: g */
    private C1371d f4137g;
    /* renamed from: h */
    private C1194c f4138h;
    /* renamed from: i */
    private C1113k f4139i;
    /* renamed from: j */
    private C1177e f4140j;
    /* renamed from: k */
    private long f4141k;

    public C1366a() {
        this(false);
    }

    public C1366a(boolean z) {
        this.f4131a = new AtomicBoolean();
        this.f4141k = 0;
        this.f4132b = new AtomicBoolean(z);
    }

    /* renamed from: e */
    private void m6927e() {
        C1230c.m6414h().mo1062a("Beta", "Performing update check");
        new C1372e(this.f4134d, this.f4134d.m6946g(), this.f4136f.f3479a, this.f4140j, new C1374g()).m6952a(new C1107g().m5986a(this.f4133c), (String) this.f4135e.m6066h().get(C1121a.FONT_TOKEN), this.f4137g);
    }

    /* renamed from: a */
    void m6928a(long j) {
        this.f4141k = j;
    }

    /* renamed from: a */
    public void mo1146a(Context context, C1370c c1370c, C1122p c1122p, C1201f c1201f, C1371d c1371d, C1194c c1194c, C1113k c1113k, C1177e c1177e) {
        this.f4133c = context;
        this.f4134d = c1370c;
        this.f4135e = c1122p;
        this.f4136f = c1201f;
        this.f4137g = c1371d;
        this.f4138h = c1194c;
        this.f4139i = c1113k;
        this.f4140j = c1177e;
        if (m6931b()) {
            m6932c();
        }
    }

    /* renamed from: a */
    protected boolean m6930a() {
        this.f4132b.set(true);
        return this.f4131a.get();
    }

    /* renamed from: b */
    boolean m6931b() {
        this.f4131a.set(true);
        return this.f4132b.get();
    }

    @SuppressLint({"CommitPrefEdits"})
    /* renamed from: c */
    protected void m6932c() {
        synchronized (this.f4138h) {
            if (this.f4138h.mo1051a().contains("last_update_check")) {
                this.f4138h.mo1052a(this.f4138h.mo1053b().remove("last_update_check"));
            }
        }
        long a = this.f4139i.mo1024a();
        long j = ((long) this.f4136f.f3480b) * 1000;
        C1230c.m6414h().mo1062a("Beta", "Check for updates delay: " + j);
        C1230c.m6414h().mo1062a("Beta", "Check for updates last check time: " + m6933d());
        j += m6933d();
        C1230c.m6414h().mo1062a("Beta", "Check for updates current time: " + a + ", next check time: " + j);
        if (a >= j) {
            try {
                m6927e();
            } finally {
                m6928a(a);
            }
        } else {
            C1230c.m6414h().mo1062a("Beta", "Check for updates next check time was not passed");
        }
    }

    /* renamed from: d */
    long m6933d() {
        return this.f4141k;
    }
}
