package p033b.p034a.p035a.p036a.p037a.p040c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: b.a.a.a.a.c.j */
public class C1159j implements C1149b<C1154l>, C1153i, C1154l {
    /* renamed from: a */
    private final List<C1154l> f3379a = new ArrayList();
    /* renamed from: b */
    private final AtomicBoolean f3380b = new AtomicBoolean(false);
    /* renamed from: c */
    private final AtomicReference<Throwable> f3381c = new AtomicReference(null);

    /* renamed from: a */
    public static boolean m6170a(Object obj) {
        try {
            return (((C1149b) obj) == null || ((C1154l) obj) == null || ((C1153i) obj) == null) ? false : true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    /* renamed from: a */
    public synchronized void m6171a(C1154l c1154l) {
        this.f3379a.add(c1154l);
    }

    /* renamed from: a */
    public void mo1026a(Throwable th) {
        this.f3381c.set(th);
    }

    /* renamed from: b */
    public C1152e mo1027b() {
        return C1152e.NORMAL;
    }

    /* renamed from: b */
    public synchronized void mo1028b(boolean z) {
        this.f3380b.set(z);
    }

    /* renamed from: c */
    public synchronized Collection<C1154l> mo1029c() {
        return Collections.unmodifiableCollection(this.f3379a);
    }

    /* renamed from: c */
    public /* synthetic */ void mo1030c(Object obj) {
        m6171a((C1154l) obj);
    }

    public int compareTo(Object obj) {
        return C1152e.m6143a(this, obj);
    }

    /* renamed from: d */
    public boolean mo1031d() {
        for (C1154l f : mo1029c()) {
            if (!f.mo1032f()) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: f */
    public boolean mo1032f() {
        return this.f3380b.get();
    }
}
