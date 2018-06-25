package com.persianswitch.p122a.p123a;

import com.persianswitch.p122a.p123a.p128c.C2168a;
import com.persianswitch.p126b.C2094r;
import com.persianswitch.p126b.C2098t;
import com.persianswitch.p126b.C2242d;
import com.persianswitch.p126b.C2244c;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;

/* renamed from: com.persianswitch.a.a.c */
public final class C2172c implements Closeable, Flushable {
    /* renamed from: a */
    static final Pattern f6589a = Pattern.compile("[a-z0-9_-]{1,120}");
    /* renamed from: b */
    static final /* synthetic */ boolean f6590b = (!C2172c.class.desiredAssertionStatus());
    /* renamed from: p */
    private static final C2094r f6591p = new C21651();
    /* renamed from: c */
    private final C2168a f6592c;
    /* renamed from: d */
    private long f6593d;
    /* renamed from: e */
    private final int f6594e;
    /* renamed from: f */
    private long f6595f;
    /* renamed from: g */
    private C2242d f6596g;
    /* renamed from: h */
    private final LinkedHashMap<String, C2167b> f6597h;
    /* renamed from: i */
    private int f6598i;
    /* renamed from: j */
    private boolean f6599j;
    /* renamed from: k */
    private boolean f6600k;
    /* renamed from: l */
    private boolean f6601l;
    /* renamed from: m */
    private long f6602m;
    /* renamed from: n */
    private final Executor f6603n;
    /* renamed from: o */
    private final Runnable f6604o;

    /* renamed from: com.persianswitch.a.a.c$1 */
    static class C21651 implements C2094r {
        C21651() {
        }

        /* renamed from: a */
        public C2098t mo3101a() {
            return C2098t.f6342b;
        }

        public void a_(C2244c c2244c, long j) {
            c2244c.mo3185g(j);
        }

        public void close() {
        }

        public void flush() {
        }
    }

    /* renamed from: com.persianswitch.a.a.c$a */
    public final class C2166a {
        /* renamed from: a */
        final /* synthetic */ C2172c f6564a;
        /* renamed from: b */
        private final C2167b f6565b;
        /* renamed from: c */
        private final boolean[] f6566c;
        /* renamed from: d */
        private boolean f6567d;

        /* renamed from: a */
        void m9787a() {
            if (this.f6565b.f6573f == this) {
                for (int i = 0; i < this.f6564a.f6594e; i++) {
                    try {
                        this.f6564a.f6592c.mo3148a(this.f6565b.f6571d[i]);
                    } catch (IOException e) {
                    }
                }
                this.f6565b.f6573f = null;
            }
        }

        /* renamed from: b */
        public void m9788b() {
            synchronized (this.f6564a) {
                if (this.f6567d) {
                    throw new IllegalStateException();
                }
                if (this.f6565b.f6573f == this) {
                    this.f6564a.m9824a(this, false);
                }
                this.f6567d = true;
            }
        }
    }

    /* renamed from: com.persianswitch.a.a.c$b */
    private final class C2167b {
        /* renamed from: a */
        private final String f6568a;
        /* renamed from: b */
        private final long[] f6569b;
        /* renamed from: c */
        private final File[] f6570c;
        /* renamed from: d */
        private final File[] f6571d;
        /* renamed from: e */
        private boolean f6572e;
        /* renamed from: f */
        private C2166a f6573f;
        /* renamed from: g */
        private long f6574g;

        /* renamed from: a */
        void m9798a(C2242d c2242d) {
            for (long k : this.f6569b) {
                c2242d.mo3188i(32).mo3192k(k);
            }
        }
    }

    /* renamed from: a */
    private synchronized void m9824a(C2166a c2166a, boolean z) {
        int i = 0;
        synchronized (this) {
            C2167b a = c2166a.f6565b;
            if (a.f6573f != c2166a) {
                throw new IllegalStateException();
            }
            if (z) {
                if (!a.f6572e) {
                    int i2 = 0;
                    while (i2 < this.f6594e) {
                        if (!c2166a.f6566c[i2]) {
                            c2166a.m9788b();
                            throw new IllegalStateException("Newly created entry didn't create value for index " + i2);
                        } else if (!this.f6592c.mo3150b(a.f6571d[i2])) {
                            c2166a.m9788b();
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            while (i < this.f6594e) {
                File file = a.f6571d[i];
                if (!z) {
                    this.f6592c.mo3148a(file);
                } else if (this.f6592c.mo3150b(file)) {
                    File file2 = a.f6570c[i];
                    this.f6592c.mo3149a(file, file2);
                    long j = a.f6569b[i];
                    long c = this.f6592c.mo3151c(file2);
                    a.f6569b[i] = c;
                    this.f6595f = (this.f6595f - j) + c;
                }
                i++;
            }
            this.f6598i++;
            a.f6573f = null;
            if ((a.f6572e | z) != 0) {
                a.f6572e = true;
                this.f6596g.mo3176b("CLEAN").mo3188i(32);
                this.f6596g.mo3176b(a.f6568a);
                a.m9798a(this.f6596g);
                this.f6596g.mo3188i(10);
                if (z) {
                    long j2 = this.f6602m;
                    this.f6602m = 1 + j2;
                    a.f6574g = j2;
                }
            } else {
                this.f6597h.remove(a.f6568a);
                this.f6596g.mo3176b("REMOVE").mo3188i(32);
                this.f6596g.mo3176b(a.f6568a);
                this.f6596g.mo3188i(10);
            }
            this.f6596g.flush();
            if (this.f6595f > this.f6593d || m9828b()) {
                this.f6603n.execute(this.f6604o);
            }
        }
    }

    /* renamed from: a */
    private boolean m9826a(C2167b c2167b) {
        if (c2167b.f6573f != null) {
            c2167b.f6573f.m9787a();
        }
        for (int i = 0; i < this.f6594e; i++) {
            this.f6592c.mo3148a(c2167b.f6570c[i]);
            this.f6595f -= c2167b.f6569b[i];
            c2167b.f6569b[i] = 0;
        }
        this.f6598i++;
        this.f6596g.mo3176b("REMOVE").mo3188i(32).mo3176b(c2167b.f6568a).mo3188i(10);
        this.f6597h.remove(c2167b.f6568a);
        if (m9828b()) {
            this.f6603n.execute(this.f6604o);
        }
        return true;
    }

    /* renamed from: b */
    private boolean m9828b() {
        return this.f6598i >= 2000 && this.f6598i >= this.f6597h.size();
    }

    /* renamed from: c */
    private synchronized void m9829c() {
        if (m9831a()) {
            throw new IllegalStateException("cache is closed");
        }
    }

    /* renamed from: d */
    private void m9830d() {
        while (this.f6595f > this.f6593d) {
            m9826a((C2167b) this.f6597h.values().iterator().next());
        }
        this.f6601l = false;
    }

    /* renamed from: a */
    public synchronized boolean m9831a() {
        return this.f6600k;
    }

    public synchronized void close() {
        if (!this.f6599j || this.f6600k) {
            this.f6600k = true;
        } else {
            for (C2167b c2167b : (C2167b[]) this.f6597h.values().toArray(new C2167b[this.f6597h.size()])) {
                if (c2167b.f6573f != null) {
                    c2167b.f6573f.m9788b();
                }
            }
            m9830d();
            this.f6596g.close();
            this.f6596g = null;
            this.f6600k = true;
        }
    }

    public synchronized void flush() {
        if (this.f6599j) {
            m9829c();
            m9830d();
            this.f6596g.flush();
        }
    }
}
