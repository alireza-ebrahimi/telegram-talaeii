package com.persianswitch.p122a;

import com.persianswitch.p122a.C2228w.C2227a;
import com.persianswitch.p122a.p123a.C2187l;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: com.persianswitch.a.n */
public final class C2211n {
    /* renamed from: a */
    private int f6778a = 64;
    /* renamed from: b */
    private int f6779b = 5;
    /* renamed from: c */
    private Runnable f6780c;
    /* renamed from: d */
    private ExecutorService f6781d;
    /* renamed from: e */
    private final Deque<C2227a> f6782e = new ArrayDeque();
    /* renamed from: f */
    private final Deque<C2227a> f6783f = new ArrayDeque();
    /* renamed from: g */
    private final Deque<C2228w> f6784g = new ArrayDeque();

    /* renamed from: a */
    private <T> void m10001a(Deque<T> deque, T t, boolean z) {
        synchronized (this) {
            if (deque.remove(t)) {
                if (z) {
                    m10003c();
                }
                int b = m10007b();
                Runnable runnable = this.f6780c;
            } else {
                throw new AssertionError("Call wasn't in-flight!");
            }
        }
        if (b == 0 && runnable != null) {
            runnable.run();
        }
    }

    /* renamed from: b */
    private int m10002b(C2227a c2227a) {
        int i = 0;
        for (C2227a a : this.f6783f) {
            i = a.m10130a().equals(c2227a.m10130a()) ? i + 1 : i;
        }
        return i;
    }

    /* renamed from: c */
    private void m10003c() {
        if (this.f6783f.size() < this.f6778a && !this.f6782e.isEmpty()) {
            Iterator it = this.f6782e.iterator();
            while (it.hasNext()) {
                C2227a c2227a = (C2227a) it.next();
                if (m10002b(c2227a) < this.f6779b) {
                    it.remove();
                    this.f6783f.add(c2227a);
                    m10004a().execute(c2227a);
                }
                if (this.f6783f.size() >= this.f6778a) {
                    return;
                }
            }
        }
    }

    /* renamed from: a */
    public synchronized ExecutorService m10004a() {
        if (this.f6781d == null) {
            this.f6781d = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), C2187l.m9896a("OkHttp Dispatcher", false));
        }
        return this.f6781d;
    }

    /* renamed from: a */
    void m10005a(C2227a c2227a) {
        m10001a(this.f6783f, c2227a, true);
    }

    /* renamed from: a */
    synchronized void m10006a(C2228w c2228w) {
        this.f6784g.add(c2228w);
    }

    /* renamed from: b */
    public synchronized int m10007b() {
        return this.f6783f.size() + this.f6784g.size();
    }

    /* renamed from: b */
    void m10008b(C2228w c2228w) {
        m10001a(this.f6784g, c2228w, false);
    }
}
