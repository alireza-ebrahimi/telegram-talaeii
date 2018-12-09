package p033b.p034a.p035a.p036a.p037a.p040c;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import p033b.p034a.p035a.p036a.p037a.p040c.C1148a.C1142d;

/* renamed from: b.a.a.a.a.c.f */
public abstract class C1158f<Params, Progress, Result> extends C1148a<Params, Progress, Result> implements C1149b<C1154l>, C1153i, C1154l {
    /* renamed from: a */
    private final C1159j f3378a = new C1159j();

    /* renamed from: b.a.a.a.a.c.f$a */
    private static class C1157a<Result> implements Executor {
        /* renamed from: a */
        private final Executor f3376a;
        /* renamed from: b */
        private final C1158f f3377b;

        public C1157a(Executor executor, C1158f c1158f) {
            this.f3376a = executor;
            this.f3377b = c1158f;
        }

        public void execute(Runnable runnable) {
            this.f3376a.execute(new C1155h<Result>(this, runnable, null) {
                /* renamed from: a */
                final /* synthetic */ C1157a f3375a;

                /* renamed from: a */
                public <T extends C1149b<C1154l> & C1153i & C1154l> T mo1033a() {
                    return this.f3375a.f3377b;
                }
            });
        }
    }

    /* renamed from: a */
    public void m6160a(C1154l c1154l) {
        if (h_() != C1142d.PENDING) {
            throw new IllegalStateException("Must not add Dependency after task is running");
        }
        ((C1149b) ((C1153i) m6169g())).mo1030c(c1154l);
    }

    /* renamed from: a */
    public void mo1026a(Throwable th) {
        ((C1154l) ((C1153i) m6169g())).mo1026a(th);
    }

    /* renamed from: a */
    public final void m6162a(ExecutorService executorService, Params... paramsArr) {
        super.m6121a(new C1157a(executorService, this), (Object[]) paramsArr);
    }

    /* renamed from: b */
    public C1152e mo1027b() {
        return ((C1153i) m6169g()).mo1027b();
    }

    /* renamed from: b */
    public void mo1028b(boolean z) {
        ((C1154l) ((C1153i) m6169g())).mo1028b(z);
    }

    /* renamed from: c */
    public Collection<C1154l> mo1029c() {
        return ((C1149b) ((C1153i) m6169g())).mo1029c();
    }

    /* renamed from: c */
    public /* synthetic */ void mo1030c(Object obj) {
        m6160a((C1154l) obj);
    }

    public int compareTo(Object obj) {
        return C1152e.m6143a(this, obj);
    }

    /* renamed from: d */
    public boolean mo1031d() {
        return ((C1149b) ((C1153i) m6169g())).mo1031d();
    }

    /* renamed from: f */
    public boolean mo1032f() {
        return ((C1154l) ((C1153i) m6169g())).mo1032f();
    }

    /* renamed from: g */
    public <T extends C1149b<C1154l> & C1153i & C1154l> T m6169g() {
        return this.f3378a;
    }
}
