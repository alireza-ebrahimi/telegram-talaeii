package com.persianswitch.p122a.p123a.p125a;

import com.persianswitch.p122a.C2226v;
import com.persianswitch.p122a.p123a.C2077h;
import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.p125a.C2075b.C2074a;
import com.persianswitch.p126b.C2242d;
import com.persianswitch.p126b.C2243e;
import com.persianswitch.p126b.C2244c;
import com.persianswitch.p126b.C2245f;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: com.persianswitch.a.a.a.d */
public final class C2092d implements Closeable {
    /* renamed from: k */
    static final /* synthetic */ boolean f6306k = (!C2092d.class.desiredAssertionStatus());
    /* renamed from: l */
    private static final ExecutorService f6307l = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), C2187l.m9896a("OkHttp FramedConnection", true));
    /* renamed from: a */
    final C2226v f6308a;
    /* renamed from: b */
    final boolean f6309b;
    /* renamed from: c */
    long f6310c;
    /* renamed from: d */
    long f6311d;
    /* renamed from: e */
    C2122n f6312e;
    /* renamed from: f */
    final C2122n f6313f;
    /* renamed from: g */
    final C2111q f6314g;
    /* renamed from: h */
    final Socket f6315h;
    /* renamed from: i */
    final C2076c f6316i;
    /* renamed from: j */
    final C2091c f6317j;
    /* renamed from: m */
    private final C2086b f6318m;
    /* renamed from: n */
    private final Map<Integer, C2101e> f6319n;
    /* renamed from: o */
    private final String f6320o;
    /* renamed from: p */
    private int f6321p;
    /* renamed from: q */
    private int f6322q;
    /* renamed from: r */
    private boolean f6323r;
    /* renamed from: s */
    private final ExecutorService f6324s;
    /* renamed from: t */
    private Map<Integer, C2119l> f6325t;
    /* renamed from: u */
    private final C2120m f6326u;
    /* renamed from: v */
    private int f6327v;
    /* renamed from: w */
    private boolean f6328w;
    /* renamed from: x */
    private final Set<Integer> f6329x;

    /* renamed from: com.persianswitch.a.a.a.d$a */
    public static class C2085a {
        /* renamed from: a */
        private Socket f6290a;
        /* renamed from: b */
        private String f6291b;
        /* renamed from: c */
        private C2243e f6292c;
        /* renamed from: d */
        private C2242d f6293d;
        /* renamed from: e */
        private C2086b f6294e = C2086b.f6298a;
        /* renamed from: f */
        private C2226v f6295f = C2226v.SPDY_3;
        /* renamed from: g */
        private C2120m f6296g = C2120m.f6433a;
        /* renamed from: h */
        private boolean f6297h;

        public C2085a(boolean z) {
            this.f6297h = z;
        }

        /* renamed from: a */
        public C2085a m9331a(C2086b c2086b) {
            this.f6294e = c2086b;
            return this;
        }

        /* renamed from: a */
        public C2085a m9332a(C2226v c2226v) {
            this.f6295f = c2226v;
            return this;
        }

        /* renamed from: a */
        public C2085a m9333a(Socket socket, String str, C2243e c2243e, C2242d c2242d) {
            this.f6290a = socket;
            this.f6291b = str;
            this.f6292c = c2243e;
            this.f6293d = c2242d;
            return this;
        }

        /* renamed from: a */
        public C2092d m9334a() {
            return new C2092d();
        }
    }

    /* renamed from: com.persianswitch.a.a.a.d$b */
    public static abstract class C2086b {
        /* renamed from: a */
        public static final C2086b f6298a = new C20871();

        /* renamed from: com.persianswitch.a.a.a.d$b$1 */
        static class C20871 extends C2086b {
            C20871() {
            }

            /* renamed from: a */
            public void mo3090a(C2101e c2101e) {
                c2101e.m9448a(C2073a.REFUSED_STREAM);
            }
        }

        /* renamed from: a */
        public void mo3153a(C2092d c2092d) {
        }

        /* renamed from: a */
        public abstract void mo3090a(C2101e c2101e);
    }

    /* renamed from: com.persianswitch.a.a.a.d$c */
    class C2091c extends C2077h implements C2074a {
        /* renamed from: a */
        final C2075b f6304a;
        /* renamed from: c */
        final /* synthetic */ C2092d f6305c;

        private C2091c(C2092d c2092d, C2075b c2075b) {
            this.f6305c = c2092d;
            super("OkHttp %s", c2092d.f6320o);
            this.f6304a = c2075b;
        }

        /* renamed from: a */
        private void m9341a(final C2122n c2122n) {
            C2092d.f6307l.execute(new C2077h(this, "OkHttp %s ACK Settings", new Object[]{this.f6305c.f6320o}) {
                /* renamed from: c */
                final /* synthetic */ C2091c f6303c;

                /* renamed from: b */
                public void mo3089b() {
                    try {
                        this.f6303c.f6305c.f6316i.mo3117a(c2122n);
                    } catch (IOException e) {
                    }
                }
            });
        }

        /* renamed from: a */
        public void mo3091a() {
        }

        /* renamed from: a */
        public void mo3092a(int i, int i2, int i3, boolean z) {
        }

        /* renamed from: a */
        public void mo3093a(int i, int i2, List<C2102f> list) {
            this.f6305c.m9356a(i2, (List) list);
        }

        /* renamed from: a */
        public void mo3094a(int i, long j) {
            if (i == 0) {
                synchronized (this.f6305c) {
                    C2092d c2092d = this.f6305c;
                    c2092d.f6311d += j;
                    this.f6305c.notifyAll();
                }
                return;
            }
            C2101e a = this.f6305c.m9385a(i);
            if (a != null) {
                synchronized (a) {
                    a.m9447a(j);
                }
            }
        }

        /* renamed from: a */
        public void mo3095a(int i, C2073a c2073a) {
            if (this.f6305c.m9378d(i)) {
                this.f6305c.m9376c(i, c2073a);
                return;
            }
            C2101e b = this.f6305c.m9395b(i);
            if (b != null) {
                b.m9453c(c2073a);
            }
        }

        /* renamed from: a */
        public void mo3096a(int i, C2073a c2073a, C2245f c2245f) {
            if (c2245f.mo3216e() > 0) {
            }
            synchronized (this.f6305c) {
                C2101e[] c2101eArr = (C2101e[]) this.f6305c.f6319n.values().toArray(new C2101e[this.f6305c.f6319n.size()]);
                this.f6305c.f6323r = true;
            }
            for (C2101e c2101e : c2101eArr) {
                if (c2101e.m9446a() > i && c2101e.m9454c()) {
                    c2101e.m9453c(C2073a.REFUSED_STREAM);
                    this.f6305c.m9395b(c2101e.m9446a());
                }
            }
        }

        /* renamed from: a */
        public void mo3097a(boolean z, int i, int i2) {
            if (z) {
                C2119l c = this.f6305c.m9374c(i);
                if (c != null) {
                    c.m9562b();
                    return;
                }
                return;
            }
            this.f6305c.m9365a(true, i, i2, null);
        }

        /* renamed from: a */
        public void mo3098a(boolean z, int i, C2243e c2243e, int i2) {
            if (this.f6305c.m9378d(i)) {
                this.f6305c.m9355a(i, c2243e, i2, z);
                return;
            }
            C2101e a = this.f6305c.m9385a(i);
            if (a == null) {
                this.f6305c.m9389a(i, C2073a.INVALID_STREAM);
                c2243e.mo3185g((long) i2);
                return;
            }
            a.m9449a(c2243e, i2);
            if (z) {
                a.m9460i();
            }
        }

        /* renamed from: a */
        public void mo3099a(boolean z, C2122n c2122n) {
            C2101e[] c2101eArr;
            long j;
            synchronized (this.f6305c) {
                int f = this.f6305c.f6313f.m9582f(C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
                if (z) {
                    this.f6305c.f6313f.m9573a();
                }
                this.f6305c.f6313f.m9574a(c2122n);
                if (this.f6305c.m9387a() == C2226v.HTTP_2) {
                    m9341a(c2122n);
                }
                int f2 = this.f6305c.f6313f.m9582f(C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
                if (f2 == -1 || f2 == f) {
                    c2101eArr = null;
                    j = 0;
                } else {
                    long j2 = (long) (f2 - f);
                    if (!this.f6305c.f6328w) {
                        this.f6305c.m9391a(j2);
                        this.f6305c.f6328w = true;
                    }
                    if (this.f6305c.f6319n.isEmpty()) {
                        j = j2;
                        c2101eArr = null;
                    } else {
                        j = j2;
                        c2101eArr = (C2101e[]) this.f6305c.f6319n.values().toArray(new C2101e[this.f6305c.f6319n.size()]);
                    }
                }
                C2092d.f6307l.execute(new C2077h(this, "OkHttp %s settings", this.f6305c.f6320o) {
                    /* renamed from: a */
                    final /* synthetic */ C2091c f6301a;

                    /* renamed from: b */
                    public void mo3089b() {
                        this.f6301a.f6305c.f6318m.mo3153a(this.f6301a.f6305c);
                    }
                });
            }
            if (c2101eArr != null && j != 0) {
                for (C2101e c2101e : c2101eArr) {
                    synchronized (c2101e) {
                        c2101e.m9447a(j);
                    }
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        /* renamed from: a */
        public void mo3100a(boolean r9, boolean r10, int r11, int r12, java.util.List<com.persianswitch.p122a.p123a.p125a.C2102f> r13, com.persianswitch.p122a.p123a.p125a.C2103g r14) {
            /*
            r8 = this;
            r0 = r8.f6305c;
            r0 = r0.m9378d(r11);
            if (r0 == 0) goto L_0x000e;
        L_0x0008:
            r0 = r8.f6305c;
            r0.m9357a(r11, r13, r10);
        L_0x000d:
            return;
        L_0x000e:
            r6 = r8.f6305c;
            monitor-enter(r6);
            r0 = r8.f6305c;	 Catch:{ all -> 0x001b }
            r0 = r0.f6323r;	 Catch:{ all -> 0x001b }
            if (r0 == 0) goto L_0x001e;
        L_0x0019:
            monitor-exit(r6);	 Catch:{ all -> 0x001b }
            goto L_0x000d;
        L_0x001b:
            r0 = move-exception;
            monitor-exit(r6);	 Catch:{ all -> 0x001b }
            throw r0;
        L_0x001e:
            r0 = r8.f6305c;	 Catch:{ all -> 0x001b }
            r0 = r0.m9385a(r11);	 Catch:{ all -> 0x001b }
            if (r0 != 0) goto L_0x008e;
        L_0x0026:
            r0 = r14.m9461a();	 Catch:{ all -> 0x001b }
            if (r0 == 0) goto L_0x0035;
        L_0x002c:
            r0 = r8.f6305c;	 Catch:{ all -> 0x001b }
            r1 = com.persianswitch.p122a.p123a.p125a.C2073a.INVALID_STREAM;	 Catch:{ all -> 0x001b }
            r0.m9389a(r11, r1);	 Catch:{ all -> 0x001b }
            monitor-exit(r6);	 Catch:{ all -> 0x001b }
            goto L_0x000d;
        L_0x0035:
            r0 = r8.f6305c;	 Catch:{ all -> 0x001b }
            r0 = r0.f6321p;	 Catch:{ all -> 0x001b }
            if (r11 > r0) goto L_0x003f;
        L_0x003d:
            monitor-exit(r6);	 Catch:{ all -> 0x001b }
            goto L_0x000d;
        L_0x003f:
            r0 = r11 % 2;
            r1 = r8.f6305c;	 Catch:{ all -> 0x001b }
            r1 = r1.f6322q;	 Catch:{ all -> 0x001b }
            r1 = r1 % 2;
            if (r0 != r1) goto L_0x004d;
        L_0x004b:
            monitor-exit(r6);	 Catch:{ all -> 0x001b }
            goto L_0x000d;
        L_0x004d:
            r0 = new com.persianswitch.a.a.a.e;	 Catch:{ all -> 0x001b }
            r2 = r8.f6305c;	 Catch:{ all -> 0x001b }
            r1 = r11;
            r3 = r9;
            r4 = r10;
            r5 = r13;
            r0.<init>(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x001b }
            r1 = r8.f6305c;	 Catch:{ all -> 0x001b }
            r1.f6321p = r11;	 Catch:{ all -> 0x001b }
            r1 = r8.f6305c;	 Catch:{ all -> 0x001b }
            r1 = r1.f6319n;	 Catch:{ all -> 0x001b }
            r2 = java.lang.Integer.valueOf(r11);	 Catch:{ all -> 0x001b }
            r1.put(r2, r0);	 Catch:{ all -> 0x001b }
            r1 = com.persianswitch.p122a.p123a.p125a.C2092d.f6307l;	 Catch:{ all -> 0x001b }
            r2 = new com.persianswitch.a.a.a.d$c$1;	 Catch:{ all -> 0x001b }
            r3 = "OkHttp %s stream %d";
            r4 = 2;
            r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x001b }
            r5 = 0;
            r7 = r8.f6305c;	 Catch:{ all -> 0x001b }
            r7 = r7.f6320o;	 Catch:{ all -> 0x001b }
            r4[r5] = r7;	 Catch:{ all -> 0x001b }
            r5 = 1;
            r7 = java.lang.Integer.valueOf(r11);	 Catch:{ all -> 0x001b }
            r4[r5] = r7;	 Catch:{ all -> 0x001b }
            r2.<init>(r8, r3, r4, r0);	 Catch:{ all -> 0x001b }
            r1.execute(r2);	 Catch:{ all -> 0x001b }
            monitor-exit(r6);	 Catch:{ all -> 0x001b }
            goto L_0x000d;
        L_0x008e:
            monitor-exit(r6);	 Catch:{ all -> 0x001b }
            r1 = r14.m9462b();
            if (r1 == 0) goto L_0x00a1;
        L_0x0095:
            r1 = com.persianswitch.p122a.p123a.p125a.C2073a.PROTOCOL_ERROR;
            r0.m9451b(r1);
            r0 = r8.f6305c;
            r0.m9395b(r11);
            goto L_0x000d;
        L_0x00a1:
            r0.m9450a(r13, r14);
            if (r10 == 0) goto L_0x000d;
        L_0x00a6:
            r0.m9460i();
            goto L_0x000d;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.persianswitch.a.a.a.d.c.a(boolean, boolean, int, int, java.util.List, com.persianswitch.a.a.a.g):void");
        }

        /* renamed from: b */
        protected void mo3089b() {
            C2073a c2073a;
            Throwable th;
            C2073a c2073a2 = C2073a.INTERNAL_ERROR;
            C2073a c2073a3 = C2073a.INTERNAL_ERROR;
            try {
                if (!this.f6305c.f6309b) {
                    this.f6304a.mo3110a();
                }
                do {
                } while (this.f6304a.mo3111a(this));
                try {
                    this.f6305c.m9358a(C2073a.NO_ERROR, C2073a.CANCEL);
                } catch (IOException e) {
                }
                C2187l.m9898a(this.f6304a);
            } catch (IOException e2) {
                c2073a = C2073a.PROTOCOL_ERROR;
                try {
                    this.f6305c.m9358a(c2073a, C2073a.PROTOCOL_ERROR);
                } catch (IOException e3) {
                }
                C2187l.m9898a(this.f6304a);
            } catch (Throwable th2) {
                th = th2;
                this.f6305c.m9358a(c2073a, c2073a3);
                C2187l.m9898a(this.f6304a);
                throw th;
            }
        }
    }

    private C2092d(C2085a c2085a) {
        int i = 2;
        this.f6319n = new HashMap();
        this.f6310c = 0;
        this.f6312e = new C2122n();
        this.f6313f = new C2122n();
        this.f6328w = false;
        this.f6329x = new LinkedHashSet();
        this.f6308a = c2085a.f6295f;
        this.f6326u = c2085a.f6296g;
        this.f6309b = c2085a.f6297h;
        this.f6318m = c2085a.f6294e;
        this.f6322q = c2085a.f6297h ? 1 : 2;
        if (c2085a.f6297h && this.f6308a == C2226v.HTTP_2) {
            this.f6322q += 2;
        }
        if (c2085a.f6297h) {
            i = 1;
        }
        this.f6327v = i;
        if (c2085a.f6297h) {
            this.f6312e.m9572a(7, 0, 16777216);
        }
        this.f6320o = c2085a.f6291b;
        if (this.f6308a == C2226v.HTTP_2) {
            this.f6314g = new C2112i();
            this.f6324s = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), C2187l.m9896a(C2187l.m9892a("OkHttp %s Push Observer", this.f6320o), true));
            this.f6313f.m9572a(7, 0, 65535);
            this.f6313f.m9572a(5, 0, MessagesController.UPDATE_MASK_CHAT_ADMINS);
        } else if (this.f6308a == C2226v.SPDY_3) {
            this.f6314g = new C2125o();
            this.f6324s = null;
        } else {
            throw new AssertionError(this.f6308a);
        }
        this.f6311d = (long) this.f6313f.m9582f(C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
        this.f6315h = c2085a.f6290a;
        this.f6316i = this.f6314g.mo3125a(c2085a.f6293d, this.f6309b);
        this.f6317j = new C2091c(this.f6314g.mo3124a(c2085a.f6292c, this.f6309b));
    }

    /* renamed from: a */
    private C2101e m9353a(int i, List<C2102f> list, boolean z, boolean z2) {
        C2101e c2101e;
        boolean z3 = !z;
        boolean z4 = !z2;
        synchronized (this.f6316i) {
            synchronized (this) {
                if (this.f6323r) {
                    throw new IOException("shutdown");
                }
                int i2 = this.f6322q;
                this.f6322q += 2;
                c2101e = new C2101e(i2, this, z3, z4, list);
                Object obj = (!z || this.f6311d == 0 || c2101e.f6353b == 0) ? 1 : null;
                if (c2101e.m9452b()) {
                    this.f6319n.put(Integer.valueOf(i2), c2101e);
                }
            }
            if (i == 0) {
                this.f6316i.mo3120a(z3, z4, i2, i, list);
            } else if (this.f6309b) {
                throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
            } else {
                this.f6316i.mo3113a(i, i2, (List) list);
            }
        }
        if (obj != null) {
            this.f6316i.mo3121b();
        }
        return c2101e;
    }

    /* renamed from: a */
    private void m9355a(int i, C2243e c2243e, int i2, boolean z) {
        final C2244c c2244c = new C2244c();
        c2243e.mo3174a((long) i2);
        c2243e.mo3105a(c2244c, (long) i2);
        if (c2244c.m10274b() != ((long) i2)) {
            throw new IOException(c2244c.m10274b() + " != " + i2);
        }
        final int i3 = i;
        final int i4 = i2;
        final boolean z2 = z;
        this.f6324s.execute(new C2077h(this, "OkHttp %s Push Data[%s]", new Object[]{this.f6320o, Integer.valueOf(i)}) {
            /* renamed from: f */
            final /* synthetic */ C2092d f6286f;

            /* renamed from: b */
            public void mo3089b() {
                try {
                    boolean a = this.f6286f.f6326u.mo3127a(i3, c2244c, i4, z2);
                    if (a) {
                        this.f6286f.f6316i.mo3115a(i3, C2073a.CANCEL);
                    }
                    if (a || z2) {
                        synchronized (this.f6286f) {
                            this.f6286f.f6329x.remove(Integer.valueOf(i3));
                        }
                    }
                } catch (IOException e) {
                }
            }
        });
    }

    /* renamed from: a */
    private void m9356a(int i, List<C2102f> list) {
        synchronized (this) {
            if (this.f6329x.contains(Integer.valueOf(i))) {
                m9389a(i, C2073a.PROTOCOL_ERROR);
                return;
            }
            this.f6329x.add(Integer.valueOf(i));
            final int i2 = i;
            final List<C2102f> list2 = list;
            this.f6324s.execute(new C2077h(this, "OkHttp %s Push Request[%s]", new Object[]{this.f6320o, Integer.valueOf(i)}) {
                /* renamed from: d */
                final /* synthetic */ C2092d f6277d;

                /* renamed from: b */
                public void mo3089b() {
                    if (this.f6277d.f6326u.mo3128a(i2, list2)) {
                        try {
                            this.f6277d.f6316i.mo3115a(i2, C2073a.CANCEL);
                            synchronized (this.f6277d) {
                                this.f6277d.f6329x.remove(Integer.valueOf(i2));
                            }
                        } catch (IOException e) {
                        }
                    }
                }
            });
        }
    }

    /* renamed from: a */
    private void m9357a(int i, List<C2102f> list, boolean z) {
        final int i2 = i;
        final List<C2102f> list2 = list;
        final boolean z2 = z;
        this.f6324s.execute(new C2077h(this, "OkHttp %s Push Headers[%s]", new Object[]{this.f6320o, Integer.valueOf(i)}) {
            /* renamed from: e */
            final /* synthetic */ C2092d f6281e;

            /* renamed from: b */
            public void mo3089b() {
                boolean a = this.f6281e.f6326u.mo3129a(i2, list2, z2);
                if (a) {
                    try {
                        this.f6281e.f6316i.mo3115a(i2, C2073a.CANCEL);
                    } catch (IOException e) {
                        return;
                    }
                }
                if (a || z2) {
                    synchronized (this.f6281e) {
                        this.f6281e.f6329x.remove(Integer.valueOf(i2));
                    }
                }
            }
        });
    }

    /* renamed from: a */
    private void m9358a(C2073a c2073a, C2073a c2073a2) {
        IOException iOException;
        if (f6306k || !Thread.holdsLock(this)) {
            IOException e;
            C2101e[] c2101eArr;
            C2119l[] c2119lArr;
            try {
                m9392a(c2073a);
                iOException = null;
            } catch (IOException e2) {
                iOException = e2;
            }
            synchronized (this) {
                if (this.f6319n.isEmpty()) {
                    c2101eArr = null;
                } else {
                    C2101e[] c2101eArr2 = (C2101e[]) this.f6319n.values().toArray(new C2101e[this.f6319n.size()]);
                    this.f6319n.clear();
                    c2101eArr = c2101eArr2;
                }
                if (this.f6325t != null) {
                    C2119l[] c2119lArr2 = (C2119l[]) this.f6325t.values().toArray(new C2119l[this.f6325t.size()]);
                    this.f6325t = null;
                    c2119lArr = c2119lArr2;
                } else {
                    c2119lArr = null;
                }
            }
            if (c2101eArr != null) {
                e2 = iOException;
                for (C2101e a : c2101eArr) {
                    try {
                        a.m9448a(c2073a2);
                    } catch (IOException iOException2) {
                        if (e2 != null) {
                            e2 = iOException2;
                        }
                    }
                }
                iOException2 = e2;
            }
            if (c2119lArr != null) {
                for (C2119l c : c2119lArr) {
                    c.m9563c();
                }
            }
            try {
                this.f6316i.close();
                e2 = iOException2;
            } catch (IOException e3) {
                e2 = e3;
                if (iOException2 != null) {
                    e2 = iOException2;
                }
            }
            try {
                this.f6315h.close();
            } catch (IOException e4) {
                e2 = e4;
            }
            if (e2 != null) {
                throw e2;
            }
            return;
        }
        throw new AssertionError();
    }

    /* renamed from: a */
    private void m9365a(boolean z, int i, int i2, C2119l c2119l) {
        final boolean z2 = z;
        final int i3 = i;
        final int i4 = i2;
        final C2119l c2119l2 = c2119l;
        f6307l.execute(new C2077h(this, "OkHttp %s ping %08x%08x", new Object[]{this.f6320o, Integer.valueOf(i), Integer.valueOf(i2)}) {
            /* renamed from: f */
            final /* synthetic */ C2092d f6274f;

            /* renamed from: b */
            public void mo3089b() {
                try {
                    this.f6274f.m9370b(z2, i3, i4, c2119l2);
                } catch (IOException e) {
                }
            }
        });
    }

    /* renamed from: b */
    private void m9370b(boolean z, int i, int i2, C2119l c2119l) {
        synchronized (this.f6316i) {
            if (c2119l != null) {
                c2119l.m9561a();
            }
            this.f6316i.mo3118a(z, i, i2);
        }
    }

    /* renamed from: c */
    private synchronized C2119l m9374c(int i) {
        return this.f6325t != null ? (C2119l) this.f6325t.remove(Integer.valueOf(i)) : null;
    }

    /* renamed from: c */
    private void m9376c(int i, C2073a c2073a) {
        final int i2 = i;
        final C2073a c2073a2 = c2073a;
        this.f6324s.execute(new C2077h(this, "OkHttp %s Push Reset[%s]", new Object[]{this.f6320o, Integer.valueOf(i)}) {
            /* renamed from: d */
            final /* synthetic */ C2092d f6289d;

            /* renamed from: b */
            public void mo3089b() {
                this.f6289d.f6326u.mo3126a(i2, c2073a2);
                synchronized (this.f6289d) {
                    this.f6289d.f6329x.remove(Integer.valueOf(i2));
                }
            }
        });
    }

    /* renamed from: d */
    private boolean m9378d(int i) {
        return this.f6308a == C2226v.HTTP_2 && i != 0 && (i & 1) == 0;
    }

    /* renamed from: a */
    synchronized C2101e m9385a(int i) {
        return (C2101e) this.f6319n.get(Integer.valueOf(i));
    }

    /* renamed from: a */
    public C2101e m9386a(List<C2102f> list, boolean z, boolean z2) {
        return m9353a(0, (List) list, z, z2);
    }

    /* renamed from: a */
    public C2226v m9387a() {
        return this.f6308a;
    }

    /* renamed from: a */
    void m9388a(int i, long j) {
        final int i2 = i;
        final long j2 = j;
        f6307l.execute(new C2077h(this, "OkHttp Window Update %s stream %d", new Object[]{this.f6320o, Integer.valueOf(i)}) {
            /* renamed from: d */
            final /* synthetic */ C2092d f6269d;

            /* renamed from: b */
            public void mo3089b() {
                try {
                    this.f6269d.f6316i.mo3114a(i2, j2);
                } catch (IOException e) {
                }
            }
        });
    }

    /* renamed from: a */
    void m9389a(int i, C2073a c2073a) {
        final int i2 = i;
        final C2073a c2073a2 = c2073a;
        f6307l.submit(new C2077h(this, "OkHttp %s stream %d", new Object[]{this.f6320o, Integer.valueOf(i)}) {
            /* renamed from: d */
            final /* synthetic */ C2092d f6266d;

            /* renamed from: b */
            public void mo3089b() {
                try {
                    this.f6266d.m9396b(i2, c2073a2);
                } catch (IOException e) {
                }
            }
        });
    }

    /* renamed from: a */
    public void m9390a(int i, boolean z, C2244c c2244c, long j) {
        if (j == 0) {
            this.f6316i.mo3119a(z, i, c2244c, 0);
            return;
        }
        while (j > 0) {
            int min;
            synchronized (this) {
                while (this.f6311d <= 0) {
                    try {
                        if (this.f6319n.containsKey(Integer.valueOf(i))) {
                            wait();
                        } else {
                            throw new IOException("stream closed");
                        }
                    } catch (InterruptedException e) {
                        throw new InterruptedIOException();
                    }
                }
                min = Math.min((int) Math.min(j, this.f6311d), this.f6316i.mo3123c());
                this.f6311d -= (long) min;
            }
            j -= (long) min;
            C2076c c2076c = this.f6316i;
            boolean z2 = z && j == 0;
            c2076c.mo3119a(z2, i, c2244c, min);
        }
    }

    /* renamed from: a */
    void m9391a(long j) {
        this.f6311d += j;
        if (j > 0) {
            notifyAll();
        }
    }

    /* renamed from: a */
    public void m9392a(C2073a c2073a) {
        synchronized (this.f6316i) {
            synchronized (this) {
                if (this.f6323r) {
                    return;
                }
                this.f6323r = true;
                int i = this.f6321p;
                this.f6316i.mo3116a(i, c2073a, C2187l.f6634a);
            }
        }
    }

    /* renamed from: a */
    void m9393a(boolean z) {
        if (z) {
            this.f6316i.mo3112a();
            this.f6316i.mo3122b(this.f6312e);
            int f = this.f6312e.m9582f(C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
            if (f != C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) {
                this.f6316i.mo3114a(0, (long) (f - C3446C.DEFAULT_BUFFER_SEGMENT_SIZE));
            }
        }
        new Thread(this.f6317j).start();
    }

    /* renamed from: b */
    public synchronized int m9394b() {
        return this.f6313f.m9580d(Integer.MAX_VALUE);
    }

    /* renamed from: b */
    synchronized C2101e m9395b(int i) {
        C2101e c2101e;
        c2101e = (C2101e) this.f6319n.remove(Integer.valueOf(i));
        notifyAll();
        return c2101e;
    }

    /* renamed from: b */
    void m9396b(int i, C2073a c2073a) {
        this.f6316i.mo3115a(i, c2073a);
    }

    /* renamed from: c */
    public void m9397c() {
        this.f6316i.mo3121b();
    }

    public void close() {
        m9358a(C2073a.NO_ERROR, C2073a.CANCEL);
    }

    /* renamed from: d */
    public void m9398d() {
        m9393a(true);
    }
}
