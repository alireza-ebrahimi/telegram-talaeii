package p033b.p034a.p035a.p036a;

import android.content.Context;
import java.io.File;
import java.util.Collection;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p;
import p033b.p034a.p035a.p036a.p037a.p040c.C1151d;
import p033b.p034a.p035a.p036a.p037a.p040c.C1154l;

/* renamed from: b.a.a.a.i */
public abstract class C1237i<Result> implements Comparable<C1237i> {
    /* renamed from: e */
    C1230c f3577e;
    /* renamed from: f */
    C1236h<Result> f3578f = new C1236h(this);
    /* renamed from: g */
    Context f3579g;
    /* renamed from: h */
    C1227f<Result> f3580h;
    /* renamed from: i */
    C1122p f3581i;
    /* renamed from: j */
    final C1151d f3582j = ((C1151d) getClass().getAnnotation(C1151d.class));

    /* renamed from: a */
    public int m6442a(C1237i c1237i) {
        return m6448b(c1237i) ? 1 : c1237i.m6448b(this) ? -1 : (!m6455t() || c1237i.m6455t()) ? (m6455t() || !c1237i.m6455t()) ? 0 : -1 : 1;
    }

    /* renamed from: a */
    public abstract String mo1080a();

    /* renamed from: a */
    void m6444a(Context context, C1230c c1230c, C1227f<Result> c1227f, C1122p c1122p) {
        this.f3577e = c1230c;
        this.f3579g = new C1231d(context, mo1081b(), m6454s());
        this.f3580h = c1227f;
        this.f3581i = c1122p;
    }

    /* renamed from: a */
    protected void m6445a(Result result) {
    }

    /* renamed from: b */
    public abstract String mo1081b();

    /* renamed from: b */
    protected void m6447b(Result result) {
    }

    /* renamed from: b */
    boolean m6448b(C1237i c1237i) {
        if (!m6455t()) {
            return false;
        }
        for (Class isAssignableFrom : this.f3582j.m6142a()) {
            if (isAssignableFrom.isAssignableFrom(c1237i.getClass())) {
                return true;
            }
        }
        return false;
    }

    protected boolean c_() {
        return true;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return m6442a((C1237i) obj);
    }

    /* renamed from: e */
    protected abstract Result mo1083e();

    /* renamed from: o */
    final void m6450o() {
        this.f3578f.m6162a(this.f3577e.m6426f(), (Void) null);
    }

    /* renamed from: p */
    protected C1122p m6451p() {
        return this.f3581i;
    }

    /* renamed from: q */
    public Context m6452q() {
        return this.f3579g;
    }

    /* renamed from: r */
    public C1230c m6453r() {
        return this.f3577e;
    }

    /* renamed from: s */
    public String m6454s() {
        return ".Fabric" + File.separator + mo1081b();
    }

    /* renamed from: t */
    boolean m6455t() {
        return this.f3582j != null;
    }

    /* renamed from: u */
    protected Collection<C1154l> m6456u() {
        return this.f3578f.mo1029c();
    }
}
