package p033b.p034a.p035a.p036a;

import p033b.p034a.p035a.p036a.p037a.p039b.C1132u;
import p033b.p034a.p035a.p036a.p037a.p040c.C1152e;
import p033b.p034a.p035a.p036a.p037a.p040c.C1158f;
import p033b.p034a.p035a.p036a.p037a.p040c.C1163m;

/* renamed from: b.a.a.a.h */
class C1236h<Result> extends C1158f<Void, Void, Result> {
    /* renamed from: a */
    final C1237i<Result> f3576a;

    public C1236h(C1237i<Result> c1237i) {
        this.f3576a = c1237i;
    }

    /* renamed from: a */
    private C1132u m6435a(String str) {
        C1132u c1132u = new C1132u(this.f3576a.mo1081b() + "." + str, "KitInitialization");
        c1132u.m6106a();
        return c1132u;
    }

    /* renamed from: a */
    protected Result m6437a(Void... voidArr) {
        C1132u a = m6435a("doInBackground");
        Result result = null;
        if (!m6128e()) {
            result = this.f3576a.mo1083e();
        }
        a.m6107b();
        return result;
    }

    /* renamed from: a */
    protected void mo1077a() {
        super.mo1077a();
        C1132u a = m6435a("onPreExecute");
        try {
            boolean c_ = this.f3576a.c_();
            a.m6107b();
            if (!c_) {
                m6125a(true);
            }
        } catch (C1163m e) {
            throw e;
        } catch (Throwable e2) {
            C1230c.m6414h().mo1070e("Fabric", "Failure onPreExecute()", e2);
            a.m6107b();
            m6125a(true);
        } catch (Throwable th) {
            a.m6107b();
            m6125a(true);
        }
    }

    /* renamed from: a */
    protected void mo1078a(Result result) {
        this.f3576a.m6445a((Object) result);
        this.f3576a.f3580h.mo1075a((Object) result);
    }

    /* renamed from: b */
    public C1152e mo1027b() {
        return C1152e.HIGH;
    }

    /* renamed from: b */
    protected void mo1079b(Result result) {
        this.f3576a.m6447b((Object) result);
        this.f3576a.f3580h.mo1074a(new C1235g(this.f3576a.mo1081b() + " Initialization was cancelled"));
    }
}
