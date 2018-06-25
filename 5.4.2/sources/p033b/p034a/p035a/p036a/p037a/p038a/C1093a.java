package p033b.p034a.p035a.p036a.p037a.p038a;

import android.content.Context;

/* renamed from: b.a.a.a.a.a.a */
public abstract class C1093a<T> implements C1092c<T> {
    /* renamed from: a */
    private final C1092c<T> f3234a;

    public C1093a(C1092c<T> c1092c) {
        this.f3234a = c1092c;
    }

    /* renamed from: b */
    private void m5952b(Context context, T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        mo1019a(context, (Object) t);
    }

    /* renamed from: a */
    protected abstract T mo1018a(Context context);

    /* renamed from: a */
    public final synchronized T mo1017a(Context context, C1095d<T> c1095d) {
        T a;
        a = mo1018a(context);
        if (a == null) {
            a = this.f3234a != null ? this.f3234a.mo1017a(context, c1095d) : c1095d.mo1022b(context);
            m5952b(context, a);
        }
        return a;
    }

    /* renamed from: a */
    protected abstract void mo1019a(Context context, T t);
}
