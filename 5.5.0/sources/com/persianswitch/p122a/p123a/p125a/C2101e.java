package com.persianswitch.p122a.p123a.p125a;

import com.persianswitch.p126b.C2094r;
import com.persianswitch.p126b.C2096s;
import com.persianswitch.p126b.C2098t;
import com.persianswitch.p126b.C2099a;
import com.persianswitch.p126b.C2243e;
import com.persianswitch.p126b.C2244c;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: com.persianswitch.a.a.a.e */
public final class C2101e {
    /* renamed from: d */
    static final /* synthetic */ boolean f6351d = (!C2101e.class.desiredAssertionStatus());
    /* renamed from: a */
    long f6352a = 0;
    /* renamed from: b */
    long f6353b;
    /* renamed from: c */
    final C2095a f6354c;
    /* renamed from: e */
    private final int f6355e;
    /* renamed from: f */
    private final C2092d f6356f;
    /* renamed from: g */
    private final List<C2102f> f6357g;
    /* renamed from: h */
    private List<C2102f> f6358h;
    /* renamed from: i */
    private final C2097b f6359i;
    /* renamed from: j */
    private final C2100c f6360j = new C2100c(this);
    /* renamed from: k */
    private final C2100c f6361k = new C2100c(this);
    /* renamed from: l */
    private C2073a f6362l = null;

    /* renamed from: com.persianswitch.a.a.a.e$a */
    final class C2095a implements C2094r {
        /* renamed from: a */
        static final /* synthetic */ boolean f6330a = (!C2101e.class.desiredAssertionStatus());
        /* renamed from: b */
        final /* synthetic */ C2101e f6331b;
        /* renamed from: c */
        private final C2244c f6332c = new C2244c();
        /* renamed from: d */
        private boolean f6333d;
        /* renamed from: e */
        private boolean f6334e;

        C2095a(C2101e c2101e) {
            this.f6331b = c2101e;
        }

        /* renamed from: a */
        private void m9400a(boolean z) {
            synchronized (this.f6331b) {
                this.f6331b.f6361k.m9430c();
                while (this.f6331b.f6353b <= 0 && !this.f6334e && !this.f6333d && this.f6331b.f6362l == null) {
                    try {
                        this.f6331b.m9445l();
                    } catch (Throwable th) {
                        this.f6331b.f6361k.m9433b();
                    }
                }
                this.f6331b.f6361k.m9433b();
                this.f6331b.m9444k();
                long min = Math.min(this.f6331b.f6353b, this.f6332c.m10274b());
                C2101e c2101e = this.f6331b;
                c2101e.f6353b -= min;
            }
            this.f6331b.f6361k.m9430c();
            try {
                C2092d a = this.f6331b.f6356f;
                int b = this.f6331b.f6355e;
                boolean z2 = z && min == this.f6332c.m10274b();
                a.m9390a(b, z2, this.f6332c, min);
            } finally {
                this.f6331b.f6361k.m9433b();
            }
        }

        /* renamed from: a */
        public C2098t mo3101a() {
            return this.f6331b.f6361k;
        }

        public void a_(C2244c c2244c, long j) {
            if (f6330a || !Thread.holdsLock(this.f6331b)) {
                this.f6332c.a_(c2244c, j);
                while (this.f6332c.m10274b() >= 16384) {
                    m9400a(false);
                }
                return;
            }
            throw new AssertionError();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() {
            /*
            r6 = this;
            r4 = 0;
            r2 = 1;
            r0 = f6330a;
            if (r0 != 0) goto L_0x0015;
        L_0x0007:
            r0 = r6.f6331b;
            r0 = java.lang.Thread.holdsLock(r0);
            if (r0 == 0) goto L_0x0015;
        L_0x000f:
            r0 = new java.lang.AssertionError;
            r0.<init>();
            throw r0;
        L_0x0015:
            r1 = r6.f6331b;
            monitor-enter(r1);
            r0 = r6.f6333d;	 Catch:{ all -> 0x003f }
            if (r0 == 0) goto L_0x001e;
        L_0x001c:
            monitor-exit(r1);	 Catch:{ all -> 0x003f }
        L_0x001d:
            return;
        L_0x001e:
            monitor-exit(r1);	 Catch:{ all -> 0x003f }
            r0 = r6.f6331b;
            r0 = r0.f6354c;
            r0 = r0.f6334e;
            if (r0 != 0) goto L_0x0052;
        L_0x0027:
            r0 = r6.f6332c;
            r0 = r0.m10274b();
            r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r0 <= 0) goto L_0x0042;
        L_0x0031:
            r0 = r6.f6332c;
            r0 = r0.m10274b();
            r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r0 <= 0) goto L_0x0052;
        L_0x003b:
            r6.m9400a(r2);
            goto L_0x0031;
        L_0x003f:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x003f }
            throw r0;
        L_0x0042:
            r0 = r6.f6331b;
            r0 = r0.f6356f;
            r1 = r6.f6331b;
            r1 = r1.f6355e;
            r3 = 0;
            r0.m9390a(r1, r2, r3, r4);
        L_0x0052:
            r1 = r6.f6331b;
            monitor-enter(r1);
            r0 = 1;
            r6.f6333d = r0;	 Catch:{ all -> 0x0068 }
            monitor-exit(r1);	 Catch:{ all -> 0x0068 }
            r0 = r6.f6331b;
            r0 = r0.f6356f;
            r0.m9397c();
            r0 = r6.f6331b;
            r0.m9443j();
            goto L_0x001d;
        L_0x0068:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0068 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.persianswitch.a.a.a.e.a.close():void");
        }

        public void flush() {
            if (f6330a || !Thread.holdsLock(this.f6331b)) {
                synchronized (this.f6331b) {
                    this.f6331b.m9444k();
                }
                while (this.f6332c.m10274b() > 0) {
                    m9400a(false);
                    this.f6331b.f6356f.m9397c();
                }
                return;
            }
            throw new AssertionError();
        }
    }

    /* renamed from: com.persianswitch.a.a.a.e$b */
    private final class C2097b implements C2096s {
        /* renamed from: a */
        static final /* synthetic */ boolean f6335a = (!C2101e.class.desiredAssertionStatus());
        /* renamed from: b */
        final /* synthetic */ C2101e f6336b;
        /* renamed from: c */
        private final C2244c f6337c;
        /* renamed from: d */
        private final C2244c f6338d;
        /* renamed from: e */
        private final long f6339e;
        /* renamed from: f */
        private boolean f6340f;
        /* renamed from: g */
        private boolean f6341g;

        private C2097b(C2101e c2101e, long j) {
            this.f6336b = c2101e;
            this.f6337c = new C2244c();
            this.f6338d = new C2244c();
            this.f6339e = j;
        }

        /* renamed from: b */
        private void m9409b() {
            this.f6336b.f6360j.m9430c();
            while (this.f6338d.m10274b() == 0 && !this.f6341g && !this.f6340f && this.f6336b.f6362l == null) {
                try {
                    this.f6336b.m9445l();
                } catch (Throwable th) {
                    this.f6336b.f6360j.m9433b();
                }
            }
            this.f6336b.f6360j.m9433b();
        }

        /* renamed from: c */
        private void m9411c() {
            if (this.f6340f) {
                throw new IOException("stream closed");
            } else if (this.f6336b.f6362l != null) {
                throw new C2126p(this.f6336b.f6362l);
            }
        }

        /* renamed from: a */
        public long mo3105a(C2244c c2244c, long j) {
            if (j < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            }
            long j2;
            synchronized (this.f6336b) {
                m9409b();
                m9411c();
                if (this.f6338d.m10274b() == 0) {
                    j2 = -1;
                } else {
                    j2 = this.f6338d.mo3105a(c2244c, Math.min(j, this.f6338d.m10274b()));
                    C2101e c2101e = this.f6336b;
                    c2101e.f6352a += j2;
                    if (this.f6336b.f6352a >= ((long) (this.f6336b.f6356f.f6312e.m9582f(C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) / 2))) {
                        this.f6336b.f6356f.m9388a(this.f6336b.f6355e, this.f6336b.f6352a);
                        this.f6336b.f6352a = 0;
                    }
                    synchronized (this.f6336b.f6356f) {
                        C2092d a = this.f6336b.f6356f;
                        a.f6310c += j2;
                        if (this.f6336b.f6356f.f6310c >= ((long) (this.f6336b.f6356f.f6312e.m9582f(C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) / 2))) {
                            this.f6336b.f6356f.m9388a(0, this.f6336b.f6356f.f6310c);
                            this.f6336b.f6356f.f6310c = 0;
                        }
                    }
                }
            }
            return j2;
        }

        /* renamed from: a */
        public C2098t mo3106a() {
            return this.f6336b.f6360j;
        }

        /* renamed from: a */
        void m9414a(C2243e c2243e, long j) {
            if (f6335a || !Thread.holdsLock(this.f6336b)) {
                while (j > 0) {
                    boolean z;
                    Object obj;
                    synchronized (this.f6336b) {
                        z = this.f6341g;
                        obj = this.f6338d.m10274b() + j > this.f6339e ? 1 : null;
                    }
                    if (obj != null) {
                        c2243e.mo3185g(j);
                        this.f6336b.m9451b(C2073a.FLOW_CONTROL_ERROR);
                        return;
                    } else if (z) {
                        c2243e.mo3185g(j);
                        return;
                    } else {
                        long a = c2243e.mo3105a(this.f6337c, j);
                        if (a == -1) {
                            throw new EOFException();
                        }
                        j -= a;
                        synchronized (this.f6336b) {
                            obj = this.f6338d.m10274b() == 0 ? 1 : null;
                            this.f6338d.mo3173a(this.f6337c);
                            if (obj != null) {
                                this.f6336b.notifyAll();
                            }
                        }
                    }
                }
                return;
            }
            throw new AssertionError();
        }

        public void close() {
            synchronized (this.f6336b) {
                this.f6340f = true;
                this.f6338d.m10313r();
                this.f6336b.notifyAll();
            }
            this.f6336b.m9443j();
        }
    }

    /* renamed from: com.persianswitch.a.a.a.e$c */
    class C2100c extends C2099a {
        /* renamed from: a */
        final /* synthetic */ C2101e f6350a;

        C2100c(C2101e c2101e) {
            this.f6350a = c2101e;
        }

        /* renamed from: a */
        protected IOException mo3108a(IOException iOException) {
            IOException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        /* renamed from: a */
        protected void mo3109a() {
            this.f6350a.m9451b(C2073a.CANCEL);
        }

        /* renamed from: b */
        public void m9433b() {
            if (d_()) {
                throw mo3108a(null);
            }
        }
    }

    C2101e(int i, C2092d c2092d, boolean z, boolean z2, List<C2102f> list) {
        if (c2092d == null) {
            throw new NullPointerException("connection == null");
        } else if (list == null) {
            throw new NullPointerException("requestHeaders == null");
        } else {
            this.f6355e = i;
            this.f6356f = c2092d;
            this.f6353b = (long) c2092d.f6313f.m9582f(C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
            this.f6359i = new C2097b((long) c2092d.f6312e.m9582f(C3446C.DEFAULT_BUFFER_SEGMENT_SIZE));
            this.f6354c = new C2095a(this);
            this.f6359i.f6341g = z2;
            this.f6354c.f6334e = z;
            this.f6357g = list;
        }
    }

    /* renamed from: d */
    private boolean m9438d(C2073a c2073a) {
        if (f6351d || !Thread.holdsLock(this)) {
            synchronized (this) {
                if (this.f6362l != null) {
                    return false;
                } else if (this.f6359i.f6341g && this.f6354c.f6334e) {
                    return false;
                } else {
                    this.f6362l = c2073a;
                    notifyAll();
                    this.f6356f.m9395b(this.f6355e);
                    return true;
                }
            }
        }
        throw new AssertionError();
    }

    /* renamed from: j */
    private void m9443j() {
        if (f6351d || !Thread.holdsLock(this)) {
            Object obj;
            boolean b;
            synchronized (this) {
                obj = (!this.f6359i.f6341g && this.f6359i.f6340f && (this.f6354c.f6334e || this.f6354c.f6333d)) ? 1 : null;
                b = m9452b();
            }
            if (obj != null) {
                m9448a(C2073a.CANCEL);
                return;
            } else if (!b) {
                this.f6356f.m9395b(this.f6355e);
                return;
            } else {
                return;
            }
        }
        throw new AssertionError();
    }

    /* renamed from: k */
    private void m9444k() {
        if (this.f6354c.f6333d) {
            throw new IOException("stream closed");
        } else if (this.f6354c.f6334e) {
            throw new IOException("stream finished");
        } else if (this.f6362l != null) {
            throw new C2126p(this.f6362l);
        }
    }

    /* renamed from: l */
    private void m9445l() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new InterruptedIOException();
        }
    }

    /* renamed from: a */
    public int m9446a() {
        return this.f6355e;
    }

    /* renamed from: a */
    void m9447a(long j) {
        this.f6353b += j;
        if (j > 0) {
            notifyAll();
        }
    }

    /* renamed from: a */
    public void m9448a(C2073a c2073a) {
        if (m9438d(c2073a)) {
            this.f6356f.m9396b(this.f6355e, c2073a);
        }
    }

    /* renamed from: a */
    void m9449a(C2243e c2243e, int i) {
        if (f6351d || !Thread.holdsLock(this)) {
            this.f6359i.m9414a(c2243e, (long) i);
            return;
        }
        throw new AssertionError();
    }

    /* renamed from: a */
    void m9450a(List<C2102f> list, C2103g c2103g) {
        if (f6351d || !Thread.holdsLock(this)) {
            C2073a c2073a = null;
            boolean z = true;
            synchronized (this) {
                if (this.f6358h == null) {
                    if (c2103g.m9463c()) {
                        c2073a = C2073a.PROTOCOL_ERROR;
                    } else {
                        this.f6358h = list;
                        z = m9452b();
                        notifyAll();
                    }
                } else if (c2103g.m9464d()) {
                    c2073a = C2073a.STREAM_IN_USE;
                } else {
                    List arrayList = new ArrayList();
                    arrayList.addAll(this.f6358h);
                    arrayList.addAll(list);
                    this.f6358h = arrayList;
                }
            }
            if (c2073a != null) {
                m9451b(c2073a);
                return;
            } else if (!z) {
                this.f6356f.m9395b(this.f6355e);
                return;
            } else {
                return;
            }
        }
        throw new AssertionError();
    }

    /* renamed from: b */
    public void m9451b(C2073a c2073a) {
        if (m9438d(c2073a)) {
            this.f6356f.m9389a(this.f6355e, c2073a);
        }
    }

    /* renamed from: b */
    public synchronized boolean m9452b() {
        boolean z = false;
        synchronized (this) {
            if (this.f6362l == null) {
                if (!(this.f6359i.f6341g || this.f6359i.f6340f) || (!(this.f6354c.f6334e || this.f6354c.f6333d) || this.f6358h == null)) {
                    z = true;
                }
            }
        }
        return z;
    }

    /* renamed from: c */
    synchronized void m9453c(C2073a c2073a) {
        if (this.f6362l == null) {
            this.f6362l = c2073a;
            notifyAll();
        }
    }

    /* renamed from: c */
    public boolean m9454c() {
        return this.f6356f.f6309b == ((this.f6355e & 1) == 1);
    }

    /* renamed from: d */
    public synchronized List<C2102f> m9455d() {
        this.f6360j.m9430c();
        while (this.f6358h == null && this.f6362l == null) {
            try {
                m9445l();
            } catch (Throwable th) {
                this.f6360j.m9433b();
            }
        }
        this.f6360j.m9433b();
        if (this.f6358h != null) {
        } else {
            throw new C2126p(this.f6362l);
        }
        return this.f6358h;
    }

    /* renamed from: e */
    public C2098t m9456e() {
        return this.f6360j;
    }

    /* renamed from: f */
    public C2098t m9457f() {
        return this.f6361k;
    }

    /* renamed from: g */
    public C2096s m9458g() {
        return this.f6359i;
    }

    /* renamed from: h */
    public C2094r m9459h() {
        synchronized (this) {
            if (this.f6358h != null || m9454c()) {
            } else {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        return this.f6354c;
    }

    /* renamed from: i */
    void m9460i() {
        if (f6351d || !Thread.holdsLock(this)) {
            boolean b;
            synchronized (this) {
                this.f6359i.f6341g = true;
                b = m9452b();
                notifyAll();
            }
            if (!b) {
                this.f6356f.m9395b(this.f6355e);
                return;
            }
            return;
        }
        throw new AssertionError();
    }
}
