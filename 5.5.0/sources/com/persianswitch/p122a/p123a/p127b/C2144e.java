package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.C2217q;
import com.persianswitch.p122a.C2217q.C2216a;
import com.persianswitch.p122a.C2231x;
import com.persianswitch.p122a.C2236z;
import com.persianswitch.p122a.C2236z.C2235a;
import com.persianswitch.p122a.aa;
import com.persianswitch.p122a.p123a.C2179d;
import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p126b.C2094r;
import com.persianswitch.p126b.C2096s;
import com.persianswitch.p126b.C2098t;
import com.persianswitch.p126b.C2242d;
import com.persianswitch.p126b.C2243e;
import com.persianswitch.p126b.C2244c;
import com.persianswitch.p126b.C2247i;
import com.persianswitch.p126b.C2253l;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;

/* renamed from: com.persianswitch.a.a.b.e */
public final class C2144e implements C2143j {
    /* renamed from: a */
    private final C2162s f6488a;
    /* renamed from: b */
    private final C2243e f6489b;
    /* renamed from: c */
    private final C2242d f6490c;
    /* renamed from: d */
    private C2151h f6491d;
    /* renamed from: e */
    private int f6492e = 0;

    /* renamed from: com.persianswitch.a.a.b.e$a */
    private abstract class C2137a implements C2096s {
        /* renamed from: a */
        protected final C2247i f6470a;
        /* renamed from: b */
        protected boolean f6471b;
        /* renamed from: c */
        final /* synthetic */ C2144e f6472c;

        private C2137a(C2144e c2144e) {
            this.f6472c = c2144e;
            this.f6470a = new C2247i(this.f6472c.f6489b.mo3106a());
        }

        /* renamed from: a */
        public C2098t mo3106a() {
            return this.f6470a;
        }

        /* renamed from: a */
        protected final void m9645a(boolean z) {
            if (this.f6472c.f6492e != 6) {
                if (this.f6472c.f6492e != 5) {
                    throw new IllegalStateException("state: " + this.f6472c.f6492e);
                }
                this.f6472c.m9661a(this.f6470a);
                this.f6472c.f6492e = 6;
                if (this.f6472c.f6488a != null) {
                    this.f6472c.f6488a.m9776a(!z, this.f6472c);
                }
            }
        }
    }

    /* renamed from: com.persianswitch.a.a.b.e$b */
    private final class C2138b implements C2094r {
        /* renamed from: a */
        final /* synthetic */ C2144e f6473a;
        /* renamed from: b */
        private final C2247i f6474b;
        /* renamed from: c */
        private boolean f6475c;

        private C2138b(C2144e c2144e) {
            this.f6473a = c2144e;
            this.f6474b = new C2247i(this.f6473a.f6490c.mo3101a());
        }

        /* renamed from: a */
        public C2098t mo3101a() {
            return this.f6474b;
        }

        public void a_(C2244c c2244c, long j) {
            if (this.f6475c) {
                throw new IllegalStateException("closed");
            } else if (j != 0) {
                this.f6473a.f6490c.mo3191j(j);
                this.f6473a.f6490c.mo3176b("\r\n");
                this.f6473a.f6490c.a_(c2244c, j);
                this.f6473a.f6490c.mo3176b("\r\n");
            }
        }

        public synchronized void close() {
            if (!this.f6475c) {
                this.f6475c = true;
                this.f6473a.f6490c.mo3176b("0\r\n\r\n");
                this.f6473a.m9661a(this.f6474b);
                this.f6473a.f6492e = 3;
            }
        }

        public synchronized void flush() {
            if (!this.f6475c) {
                this.f6473a.f6490c.flush();
            }
        }
    }

    /* renamed from: com.persianswitch.a.a.b.e$c */
    private class C2139c extends C2137a {
        /* renamed from: d */
        final /* synthetic */ C2144e f6476d;
        /* renamed from: e */
        private long f6477e = -1;
        /* renamed from: f */
        private boolean f6478f = true;
        /* renamed from: g */
        private final C2151h f6479g;

        C2139c(C2144e c2144e, C2151h c2151h) {
            this.f6476d = c2144e;
            super();
            this.f6479g = c2151h;
        }

        /* renamed from: b */
        private void m9647b() {
            if (this.f6477e != -1) {
                this.f6476d.f6489b.mo3196p();
            }
            try {
                this.f6477e = this.f6476d.f6489b.mo3195m();
                String trim = this.f6476d.f6489b.mo3196p().trim();
                if (this.f6477e < 0 || !(trim.isEmpty() || trim.startsWith(";"))) {
                    throw new ProtocolException("expected chunk size and optional extensions but was \"" + this.f6477e + trim + "\"");
                } else if (this.f6477e == 0) {
                    this.f6478f = false;
                    this.f6479g.m9720a(this.f6476d.m9677d());
                    m9645a(true);
                }
            } catch (NumberFormatException e) {
                throw new ProtocolException(e.getMessage());
            }
        }

        /* renamed from: a */
        public long mo3105a(C2244c c2244c, long j) {
            if (j < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            } else if (this.b) {
                throw new IllegalStateException("closed");
            } else if (!this.f6478f) {
                return -1;
            } else {
                if (this.f6477e == 0 || this.f6477e == -1) {
                    m9647b();
                    if (!this.f6478f) {
                        return -1;
                    }
                }
                long a = this.f6476d.f6489b.mo3105a(c2244c, Math.min(j, this.f6477e));
                if (a == -1) {
                    m9645a(false);
                    throw new ProtocolException("unexpected end of stream");
                }
                this.f6477e -= a;
                return a;
            }
        }

        public void close() {
            if (!this.b) {
                if (this.f6478f && !C2187l.m9901a((C2096s) this, 100, TimeUnit.MILLISECONDS)) {
                    m9645a(false);
                }
                this.b = true;
            }
        }
    }

    /* renamed from: com.persianswitch.a.a.b.e$d */
    private final class C2140d implements C2094r {
        /* renamed from: a */
        final /* synthetic */ C2144e f6480a;
        /* renamed from: b */
        private final C2247i f6481b;
        /* renamed from: c */
        private boolean f6482c;
        /* renamed from: d */
        private long f6483d;

        private C2140d(C2144e c2144e, long j) {
            this.f6480a = c2144e;
            this.f6481b = new C2247i(this.f6480a.f6490c.mo3101a());
            this.f6483d = j;
        }

        /* renamed from: a */
        public C2098t mo3101a() {
            return this.f6481b;
        }

        public void a_(C2244c c2244c, long j) {
            if (this.f6482c) {
                throw new IllegalStateException("closed");
            }
            C2187l.m9897a(c2244c.m10274b(), 0, j);
            if (j > this.f6483d) {
                throw new ProtocolException("expected " + this.f6483d + " bytes but received " + j);
            }
            this.f6480a.f6490c.a_(c2244c, j);
            this.f6483d -= j;
        }

        public void close() {
            if (!this.f6482c) {
                this.f6482c = true;
                if (this.f6483d > 0) {
                    throw new ProtocolException("unexpected end of stream");
                }
                this.f6480a.m9661a(this.f6481b);
                this.f6480a.f6492e = 3;
            }
        }

        public void flush() {
            if (!this.f6482c) {
                this.f6480a.f6490c.flush();
            }
        }
    }

    /* renamed from: com.persianswitch.a.a.b.e$e */
    private class C2141e extends C2137a {
        /* renamed from: d */
        final /* synthetic */ C2144e f6484d;
        /* renamed from: e */
        private long f6485e;

        public C2141e(C2144e c2144e, long j) {
            this.f6484d = c2144e;
            super();
            this.f6485e = j;
            if (this.f6485e == 0) {
                m9645a(true);
            }
        }

        /* renamed from: a */
        public long mo3105a(C2244c c2244c, long j) {
            if (j < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            } else if (this.b) {
                throw new IllegalStateException("closed");
            } else if (this.f6485e == 0) {
                return -1;
            } else {
                long a = this.f6484d.f6489b.mo3105a(c2244c, Math.min(this.f6485e, j));
                if (a == -1) {
                    m9645a(false);
                    throw new ProtocolException("unexpected end of stream");
                }
                this.f6485e -= a;
                if (this.f6485e == 0) {
                    m9645a(true);
                }
                return a;
            }
        }

        public void close() {
            if (!this.b) {
                if (!(this.f6485e == 0 || C2187l.m9901a((C2096s) this, 100, TimeUnit.MILLISECONDS))) {
                    m9645a(false);
                }
                this.b = true;
            }
        }
    }

    /* renamed from: com.persianswitch.a.a.b.e$f */
    private class C2142f extends C2137a {
        /* renamed from: d */
        final /* synthetic */ C2144e f6486d;
        /* renamed from: e */
        private boolean f6487e;

        private C2142f(C2144e c2144e) {
            this.f6486d = c2144e;
            super();
        }

        /* renamed from: a */
        public long mo3105a(C2244c c2244c, long j) {
            if (j < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            } else if (this.b) {
                throw new IllegalStateException("closed");
            } else if (this.f6487e) {
                return -1;
            } else {
                long a = this.f6486d.f6489b.mo3105a(c2244c, j);
                if (a != -1) {
                    return a;
                }
                this.f6487e = true;
                m9645a(true);
                return -1;
            }
        }

        public void close() {
            if (!this.b) {
                if (!this.f6487e) {
                    m9645a(false);
                }
                this.b = true;
            }
        }
    }

    public C2144e(C2162s c2162s, C2243e c2243e, C2242d c2242d) {
        this.f6488a = c2162s;
        this.f6489b = c2243e;
        this.f6490c = c2242d;
    }

    /* renamed from: a */
    private void m9661a(C2247i c2247i) {
        C2098t a = c2247i.m10336a();
        c2247i.m10335a(C2098t.f6342b);
        a.mo3203f();
        a.g_();
    }

    /* renamed from: b */
    private C2096s m9663b(C2236z c2236z) {
        if (!C2151h.m9715b(c2236z)) {
            return m9673b(0);
        }
        if ("chunked".equalsIgnoreCase(c2236z.m10215a("Transfer-Encoding"))) {
            return m9674b(this.f6491d);
        }
        long a = C2153k.m9726a(c2236z);
        return a != -1 ? m9673b(a) : m9679f();
    }

    /* renamed from: a */
    public aa mo3137a(C2236z c2236z) {
        return new C2156m(c2236z.m10220e(), C2253l.m10358a(m9663b(c2236z)));
    }

    /* renamed from: a */
    public C2235a mo3138a() {
        return m9676c();
    }

    /* renamed from: a */
    public C2094r m9668a(long j) {
        if (this.f6492e != 1) {
            throw new IllegalStateException("state: " + this.f6492e);
        }
        this.f6492e = 2;
        return new C2140d(j);
    }

    /* renamed from: a */
    public C2094r mo3139a(C2231x c2231x, long j) {
        if ("chunked".equalsIgnoreCase(c2231x.m10158a("Transfer-Encoding"))) {
            return m9678e();
        }
        if (j != -1) {
            return m9668a(j);
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    /* renamed from: a */
    public void mo3140a(C2151h c2151h) {
        this.f6491d = c2151h;
    }

    /* renamed from: a */
    public void m9671a(C2217q c2217q, String str) {
        if (this.f6492e != 0) {
            throw new IllegalStateException("state: " + this.f6492e);
        }
        this.f6490c.mo3176b(str).mo3176b("\r\n");
        int a = c2217q.m10023a();
        for (int i = 0; i < a; i++) {
            this.f6490c.mo3176b(c2217q.m10024a(i)).mo3176b(": ").mo3176b(c2217q.m10027b(i)).mo3176b("\r\n");
        }
        this.f6490c.mo3176b("\r\n");
        this.f6492e = 1;
    }

    /* renamed from: a */
    public void mo3141a(C2231x c2231x) {
        m9671a(c2231x.m10160c(), C2157n.m9742a(c2231x, this.f6488a.m9777b().mo3152a().m9926b().type()));
    }

    /* renamed from: b */
    public C2096s m9673b(long j) {
        if (this.f6492e != 4) {
            throw new IllegalStateException("state: " + this.f6492e);
        }
        this.f6492e = 5;
        return new C2141e(this, j);
    }

    /* renamed from: b */
    public C2096s m9674b(C2151h c2151h) {
        if (this.f6492e != 4) {
            throw new IllegalStateException("state: " + this.f6492e);
        }
        this.f6492e = 5;
        return new C2139c(this, c2151h);
    }

    /* renamed from: b */
    public void mo3142b() {
        this.f6490c.flush();
    }

    /* renamed from: c */
    public C2235a m9676c() {
        if (this.f6492e == 1 || this.f6492e == 3) {
            C2235a a;
            C2161r a2;
            do {
                try {
                    a2 = C2161r.m9766a(this.f6489b.mo3196p());
                    a = new C2235a().m10193a(a2.f6548a).m10188a(a2.f6549b).m10196a(a2.f6550c).m10192a(m9677d());
                } catch (Throwable e) {
                    IOException iOException = new IOException("unexpected end of stream on " + this.f6488a);
                    iOException.initCause(e);
                    throw iOException;
                }
            } while (a2.f6549b == 100);
            this.f6492e = 4;
            return a;
        }
        throw new IllegalStateException("state: " + this.f6492e);
    }

    /* renamed from: d */
    public C2217q m9677d() {
        C2216a c2216a = new C2216a();
        while (true) {
            String p = this.f6489b.mo3196p();
            if (p.length() == 0) {
                return c2216a.m10018a();
            }
            C2179d.f6617a.mo3164a(c2216a, p);
        }
    }

    /* renamed from: e */
    public C2094r m9678e() {
        if (this.f6492e != 1) {
            throw new IllegalStateException("state: " + this.f6492e);
        }
        this.f6492e = 2;
        return new C2138b();
    }

    /* renamed from: f */
    public C2096s m9679f() {
        if (this.f6492e != 4) {
            throw new IllegalStateException("state: " + this.f6492e);
        } else if (this.f6488a == null) {
            throw new IllegalStateException("streamAllocation == null");
        } else {
            this.f6492e = 5;
            this.f6488a.m9779d();
            return new C2142f();
        }
    }
}
