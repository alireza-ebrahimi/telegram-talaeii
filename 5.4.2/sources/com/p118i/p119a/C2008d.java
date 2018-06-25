package com.p118i.p119a;

import android.os.Handler;
import android.os.Looper;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.i.a.d */
public class C2008d {
    /* renamed from: a */
    private Set<C2002c> f5924a = new HashSet();
    /* renamed from: b */
    private PriorityBlockingQueue<C2002c> f5925b = new PriorityBlockingQueue();
    /* renamed from: c */
    private C2000b[] f5926c;
    /* renamed from: d */
    private AtomicInteger f5927d = new AtomicInteger();
    /* renamed from: e */
    private C2007a f5928e;

    /* renamed from: com.i.a.d$a */
    class C2007a {
        /* renamed from: a */
        final /* synthetic */ C2008d f5922a;
        /* renamed from: b */
        private final Executor f5923b;

        public C2007a(final C2008d c2008d, final Handler handler) {
            this.f5922a = c2008d;
            this.f5923b = new Executor(this) {
                /* renamed from: c */
                final /* synthetic */ C2007a f5910c;

                public void execute(Runnable runnable) {
                    handler.post(runnable);
                }
            };
        }

        /* renamed from: a */
        public void m9067a(final C2002c c2002c) {
            this.f5923b.execute(new Runnable(this) {
                /* renamed from: b */
                final /* synthetic */ C2007a f5912b;

                public void run() {
                    if (c2002c.m9058e() != null) {
                        c2002c.m9058e().m9077a(c2002c.m9056c());
                    }
                    if (c2002c.m9059f() != null) {
                        c2002c.m9059f().onDownloadComplete(c2002c);
                    }
                }
            });
        }

        /* renamed from: a */
        public void m9068a(final C2002c c2002c, final int i, final String str) {
            this.f5923b.execute(new Runnable(this) {
                /* renamed from: d */
                final /* synthetic */ C2007a f5916d;

                public void run() {
                    if (c2002c.m9058e() != null) {
                        c2002c.m9058e().m9078a(c2002c.m9056c(), i, str);
                    }
                    if (c2002c.m9059f() != null) {
                        c2002c.m9059f().onDownloadFailed(c2002c, i, str);
                    }
                }
            });
        }

        /* renamed from: a */
        public void m9069a(C2002c c2002c, long j, long j2, int i) {
            final C2002c c2002c2 = c2002c;
            final long j3 = j;
            final long j4 = j2;
            final int i2 = i;
            this.f5923b.execute(new Runnable(this) {
                /* renamed from: e */
                final /* synthetic */ C2007a f5921e;

                public void run() {
                    if (c2002c2.m9058e() != null) {
                        c2002c2.m9058e().m9079a(c2002c2.m9056c(), j3, j4, i2);
                    }
                    if (c2002c2.m9059f() != null) {
                        c2002c2.m9059f().onProgress(c2002c2, j3, j4, i2);
                    }
                }
            });
        }
    }

    public C2008d() {
        m9070a(new Handler(Looper.getMainLooper()));
    }

    /* renamed from: a */
    private void m9070a(Handler handler) {
        this.f5926c = new C2000b[Runtime.getRuntime().availableProcessors()];
        this.f5928e = new C2007a(this, handler);
    }

    /* renamed from: b */
    private void m9071b() {
        for (int i = 0; i < this.f5926c.length; i++) {
            if (this.f5926c[i] != null) {
                this.f5926c[i].m9044a();
            }
        }
    }

    /* renamed from: c */
    private int m9072c() {
        return this.f5927d.incrementAndGet();
    }

    /* renamed from: a */
    int m9073a(int i) {
        synchronized (this.f5924a) {
            for (C2002c c2002c : this.f5924a) {
                if (c2002c.m9056c() == i) {
                    c2002c.m9063j();
                    return 1;
                }
            }
            return 0;
        }
    }

    /* renamed from: a */
    int m9074a(C2002c c2002c) {
        int c = m9072c();
        c2002c.m9053a(this);
        synchronized (this.f5924a) {
            this.f5924a.add(c2002c);
        }
        c2002c.m9052a(c);
        this.f5925b.add(c2002c);
        return c;
    }

    /* renamed from: a */
    public void m9075a() {
        m9071b();
        for (int i = 0; i < this.f5926c.length; i++) {
            C2000b c2000b = new C2000b(this.f5925b, this.f5928e);
            this.f5926c[i] = c2000b;
            c2000b.start();
        }
    }

    /* renamed from: b */
    void m9076b(C2002c c2002c) {
        if (this.f5924a != null) {
            synchronized (this.f5924a) {
                this.f5924a.remove(c2002c);
            }
        }
    }
}
