package p033b.p034a.p035a.p036a.p037a.p040c;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* renamed from: b.a.a.a.a.c.h */
public class C1155h<V> extends FutureTask<V> implements C1149b<C1154l>, C1153i, C1154l {
    /* renamed from: b */
    final Object f3374b;

    public C1155h(Runnable runnable, V v) {
        super(runnable, v);
        this.f3374b = m6149a((Object) runnable);
    }

    public C1155h(Callable<V> callable) {
        super(callable);
        this.f3374b = m6149a((Object) callable);
    }

    /* renamed from: a */
    public <T extends C1149b<C1154l> & C1153i & C1154l> T mo1033a() {
        return (C1149b) this.f3374b;
    }

    /* renamed from: a */
    protected <T extends C1149b<C1154l> & C1153i & C1154l> T m6149a(Object obj) {
        return C1159j.m6170a(obj) ? (C1149b) obj : new C1159j();
    }

    /* renamed from: a */
    public void m6150a(C1154l c1154l) {
        ((C1149b) ((C1153i) mo1033a())).mo1030c(c1154l);
    }

    /* renamed from: a */
    public void mo1026a(Throwable th) {
        ((C1154l) ((C1153i) mo1033a())).mo1026a(th);
    }

    /* renamed from: b */
    public C1152e mo1027b() {
        return ((C1153i) mo1033a()).mo1027b();
    }

    /* renamed from: b */
    public void mo1028b(boolean z) {
        ((C1154l) ((C1153i) mo1033a())).mo1028b(z);
    }

    /* renamed from: c */
    public Collection<C1154l> mo1029c() {
        return ((C1149b) ((C1153i) mo1033a())).mo1029c();
    }

    /* renamed from: c */
    public /* synthetic */ void mo1030c(Object obj) {
        m6150a((C1154l) obj);
    }

    public int compareTo(Object obj) {
        return ((C1153i) mo1033a()).compareTo(obj);
    }

    /* renamed from: d */
    public boolean mo1031d() {
        return ((C1149b) ((C1153i) mo1033a())).mo1031d();
    }

    /* renamed from: f */
    public boolean mo1032f() {
        return ((C1154l) ((C1153i) mo1033a())).mo1032f();
    }
}
