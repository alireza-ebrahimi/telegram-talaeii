package com.p077f.p078a.p086b;

import com.p077f.p078a.p086b.p092e.C1580a;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.f.a.b.f */
class C1589f {
    /* renamed from: a */
    final C1584e f4861a;
    /* renamed from: b */
    private Executor f4862b;
    /* renamed from: c */
    private Executor f4863c;
    /* renamed from: d */
    private Executor f4864d;
    /* renamed from: e */
    private final Map<Integer, String> f4865e = Collections.synchronizedMap(new HashMap());
    /* renamed from: f */
    private final Map<String, ReentrantLock> f4866f = new WeakHashMap();
    /* renamed from: g */
    private final AtomicBoolean f4867g = new AtomicBoolean(false);
    /* renamed from: h */
    private final AtomicBoolean f4868h = new AtomicBoolean(false);
    /* renamed from: i */
    private final AtomicBoolean f4869i = new AtomicBoolean(false);
    /* renamed from: j */
    private final Object f4870j = new Object();

    C1589f(C1584e c1584e) {
        this.f4861a = c1584e;
        this.f4862b = c1584e.f4845g;
        this.f4863c = c1584e.f4846h;
        this.f4864d = C1558a.m7683a();
    }

    /* renamed from: e */
    private void m7887e() {
        if (!this.f4861a.f4847i && ((ExecutorService) this.f4862b).isShutdown()) {
            this.f4862b = m7888f();
        }
        if (!this.f4861a.f4848j && ((ExecutorService) this.f4863c).isShutdown()) {
            this.f4863c = m7888f();
        }
    }

    /* renamed from: f */
    private Executor m7888f() {
        return C1558a.m7684a(this.f4861a.f4849k, this.f4861a.f4850l, this.f4861a.f4851m);
    }

    /* renamed from: a */
    String m7889a(C1580a c1580a) {
        return (String) this.f4865e.get(Integer.valueOf(c1580a.mo1235f()));
    }

    /* renamed from: a */
    AtomicBoolean m7890a() {
        return this.f4867g;
    }

    /* renamed from: a */
    ReentrantLock m7891a(String str) {
        ReentrantLock reentrantLock = (ReentrantLock) this.f4866f.get(str);
        if (reentrantLock != null) {
            return reentrantLock;
        }
        reentrantLock = new ReentrantLock();
        this.f4866f.put(str, reentrantLock);
        return reentrantLock;
    }

    /* renamed from: a */
    void m7892a(C1580a c1580a, String str) {
        this.f4865e.put(Integer.valueOf(c1580a.mo1235f()), str);
    }

    /* renamed from: a */
    void m7893a(final C1597h c1597h) {
        this.f4864d.execute(new Runnable(this) {
            /* renamed from: b */
            final /* synthetic */ C1589f f4860b;

            public void run() {
                File a = this.f4860b.f4861a.f4853o.mo1213a(c1597h.m7923a());
                Object obj = (a == null || !a.exists()) ? null : 1;
                this.f4860b.m7887e();
                if (obj != null) {
                    this.f4860b.f4863c.execute(c1597h);
                } else {
                    this.f4860b.f4862b.execute(c1597h);
                }
            }
        });
    }

    /* renamed from: a */
    void m7894a(C1598i c1598i) {
        m7887e();
        this.f4863c.execute(c1598i);
    }

    /* renamed from: a */
    void m7895a(Runnable runnable) {
        this.f4864d.execute(runnable);
    }

    /* renamed from: b */
    Object m7896b() {
        return this.f4870j;
    }

    /* renamed from: b */
    void m7897b(C1580a c1580a) {
        this.f4865e.remove(Integer.valueOf(c1580a.mo1235f()));
    }

    /* renamed from: c */
    boolean m7898c() {
        return this.f4868h.get();
    }

    /* renamed from: d */
    boolean m7899d() {
        return this.f4869i.get();
    }
}
