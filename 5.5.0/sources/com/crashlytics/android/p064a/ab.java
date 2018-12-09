package com.crashlytics.android.p064a;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import com.crashlytics.android.p064a.C1350l.C1329a;
import com.crashlytics.android.p064a.ad.C1332b;
import java.util.concurrent.ScheduledExecutorService;
import p033b.p034a.p035a.p036a.C1223a;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1119n;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p;
import p033b.p034a.p035a.p036a.p037a.p043e.C1177e;
import p033b.p034a.p035a.p036a.p037a.p043e.C1178b;
import p033b.p034a.p035a.p036a.p037a.p044f.C1193b;
import p033b.p034a.p035a.p036a.p037a.p045g.C1197b;

/* renamed from: com.crashlytics.android.a.ab */
class ab implements C1329a {
    /* renamed from: a */
    final C1342f f4009a;
    /* renamed from: b */
    final C1223a f4010b;
    /* renamed from: c */
    final C1350l f4011c;
    /* renamed from: d */
    final C1345i f4012d;
    /* renamed from: e */
    private final long f4013e;

    ab(C1342f c1342f, C1223a c1223a, C1350l c1350l, C1345i c1345i, long j) {
        this.f4009a = c1342f;
        this.f4010b = c1223a;
        this.f4011c = c1350l;
        this.f4012d = c1345i;
        this.f4013e = j;
    }

    /* renamed from: a */
    public static ab m6790a(C1237i c1237i, Context context, C1122p c1122p, String str, String str2, long j) {
        ag agVar = new ag(context, c1122p, str, str2);
        C1343g c1343g = new C1343g(context, new C1193b(c1237i));
        C1177e c1178b = new C1178b(C1230c.m6414h());
        C1223a c1223a = new C1223a(context);
        ScheduledExecutorService b = C1119n.m6047b("Answers Events Handler");
        C1350l c1350l = new C1350l(b);
        return new ab(new C1342f(c1237i, context, c1343g, agVar, c1178b, b, new C1355r(context)), c1223a, c1350l, C1345i.m6858a(context), j);
    }

    /* renamed from: a */
    public void mo1126a() {
        C1230c.m6414h().mo1062a("Answers", "Flush events when app is backgrounded");
        this.f4009a.m6848c();
    }

    /* renamed from: a */
    public void m6792a(long j) {
        C1230c.m6414h().mo1062a("Answers", "Logged install");
        this.f4009a.m6847b(ad.m6808a(j));
    }

    /* renamed from: a */
    public void m6793a(Activity activity, C1332b c1332b) {
        C1230c.m6414h().mo1062a("Answers", "Logged lifecycle event: " + c1332b.name());
        this.f4009a.m6843a(ad.m6809a(c1332b, activity));
    }

    /* renamed from: a */
    public void m6794a(C1197b c1197b, String str) {
        this.f4011c.m6875a(c1197b.f3456j);
        this.f4009a.m6842a(c1197b, str);
    }

    /* renamed from: a */
    public void m6795a(C1351m c1351m) {
        C1230c.m6414h().mo1062a("Answers", "Logged custom event: " + c1351m);
        this.f4009a.m6843a(ad.m6810a(c1351m));
    }

    /* renamed from: a */
    public void m6796a(String str, String str2) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("onCrash called from main thread!!!");
        }
        C1230c.m6414h().mo1062a("Answers", "Logged crash");
        this.f4009a.m6849c(ad.m6812a(str, str2));
    }

    /* renamed from: b */
    public void m6797b() {
        this.f4009a.m6846b();
        this.f4010b.m6370a(new C1344h(this, this.f4011c));
        this.f4011c.m6874a((C1329a) this);
        if (m6799d()) {
            m6792a(this.f4013e);
            this.f4012d.m6859a();
        }
    }

    /* renamed from: c */
    public void m6798c() {
        this.f4010b.m6369a();
        this.f4009a.m6841a();
    }

    /* renamed from: d */
    boolean m6799d() {
        return !this.f4012d.m6860b();
    }
}
