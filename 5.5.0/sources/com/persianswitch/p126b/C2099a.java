package com.persianswitch.p126b;

import java.io.IOException;
import java.io.InterruptedIOException;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: com.persianswitch.b.a */
public class C2099a extends C2098t {
    /* renamed from: a */
    private static C2099a f6346a;
    /* renamed from: c */
    private boolean f6347c;
    /* renamed from: d */
    private C2099a f6348d;
    /* renamed from: e */
    private long f6349e;

    /* renamed from: com.persianswitch.b.a$a */
    private static final class C2239a extends Thread {
        public C2239a() {
            super("Okio Watchdog");
            setDaemon(true);
        }

        public void run() {
            while (true) {
                try {
                    C2099a e = C2099a.m9423e();
                    if (e != null) {
                        e.mo3109a();
                    }
                } catch (InterruptedException e2) {
                }
            }
        }
    }

    /* renamed from: a */
    private static synchronized void m9420a(C2099a c2099a, long j, boolean z) {
        synchronized (C2099a.class) {
            if (f6346a == null) {
                f6346a = new C2099a();
                new C2239a().start();
            }
            long nanoTime = System.nanoTime();
            if (j != 0 && z) {
                c2099a.f6349e = Math.min(j, c2099a.mo3201d() - nanoTime) + nanoTime;
            } else if (j != 0) {
                c2099a.f6349e = nanoTime + j;
            } else if (z) {
                c2099a.f6349e = c2099a.mo3201d();
            } else {
                throw new AssertionError();
            }
            long b = c2099a.m9422b(nanoTime);
            C2099a c2099a2 = f6346a;
            while (c2099a2.f6348d != null && b >= c2099a2.f6348d.m9422b(nanoTime)) {
                c2099a2 = c2099a2.f6348d;
            }
            c2099a.f6348d = c2099a2.f6348d;
            c2099a2.f6348d = c2099a;
            if (c2099a2 == f6346a) {
                C2099a.class.notify();
            }
        }
    }

    /* renamed from: a */
    private static synchronized boolean m9421a(C2099a c2099a) {
        boolean z;
        synchronized (C2099a.class) {
            for (C2099a c2099a2 = f6346a; c2099a2 != null; c2099a2 = c2099a2.f6348d) {
                if (c2099a2.f6348d == c2099a) {
                    c2099a2.f6348d = c2099a.f6348d;
                    c2099a.f6348d = null;
                    z = false;
                    break;
                }
            }
            z = true;
        }
        return z;
    }

    /* renamed from: b */
    private long m9422b(long j) {
        return this.f6349e - j;
    }

    /* renamed from: e */
    static synchronized C2099a m9423e() {
        C2099a c2099a = null;
        synchronized (C2099a.class) {
            C2099a c2099a2 = f6346a.f6348d;
            if (c2099a2 == null) {
                C2099a.class.wait();
            } else {
                long b = c2099a2.m9422b(System.nanoTime());
                if (b > 0) {
                    long j = b / C3446C.MICROS_PER_SECOND;
                    C2099a.class.wait(j, (int) (b - (C3446C.MICROS_PER_SECOND * j)));
                } else {
                    f6346a.f6348d = c2099a2.f6348d;
                    c2099a2.f6348d = null;
                    c2099a = c2099a2;
                }
            }
        }
        return c2099a;
    }

    /* renamed from: a */
    public final C2094r m9424a(final C2094r c2094r) {
        return new C2094r(this) {
            /* renamed from: b */
            final /* synthetic */ C2099a f6928b;

            /* renamed from: a */
            public C2098t mo3101a() {
                return this.f6928b;
            }

            public void a_(C2244c c2244c, long j) {
                C2261u.m10423a(c2244c.f6936b, 0, j);
                long j2 = j;
                while (j2 > 0) {
                    C2257o c2257o = c2244c.f6935a;
                    long j3 = 0;
                    while (j3 < 65536) {
                        long j4 = ((long) (c2244c.f6935a.f6970c - c2244c.f6935a.f6969b)) + j3;
                        if (j4 >= j2) {
                            j3 = j2;
                            break;
                        } else {
                            c2257o = c2257o.f6973f;
                            j3 = j4;
                        }
                    }
                    this.f6928b.m9430c();
                    try {
                        c2094r.a_(c2244c, j3);
                        j2 -= j3;
                        this.f6928b.m9428a(true);
                    } catch (IOException e) {
                        throw this.f6928b.m9429b(e);
                    } catch (Throwable th) {
                        this.f6928b.m9428a(false);
                    }
                }
            }

            public void close() {
                this.f6928b.m9430c();
                try {
                    c2094r.close();
                    this.f6928b.m9428a(true);
                } catch (IOException e) {
                    throw this.f6928b.m9429b(e);
                } catch (Throwable th) {
                    this.f6928b.m9428a(false);
                }
            }

            public void flush() {
                this.f6928b.m9430c();
                try {
                    c2094r.flush();
                    this.f6928b.m9428a(true);
                } catch (IOException e) {
                    throw this.f6928b.m9429b(e);
                } catch (Throwable th) {
                    this.f6928b.m9428a(false);
                }
            }

            public String toString() {
                return "AsyncTimeout.sink(" + c2094r + ")";
            }
        };
    }

    /* renamed from: a */
    public final C2096s m9425a(final C2096s c2096s) {
        return new C2096s(this) {
            /* renamed from: b */
            final /* synthetic */ C2099a f6930b;

            /* renamed from: a */
            public long mo3105a(C2244c c2244c, long j) {
                this.f6930b.m9430c();
                try {
                    long a = c2096s.mo3105a(c2244c, j);
                    this.f6930b.m9428a(true);
                    return a;
                } catch (IOException e) {
                    throw this.f6930b.m9429b(e);
                } catch (Throwable th) {
                    this.f6930b.m9428a(false);
                }
            }

            /* renamed from: a */
            public C2098t mo3106a() {
                return this.f6930b;
            }

            public void close() {
                try {
                    c2096s.close();
                    this.f6930b.m9428a(true);
                } catch (IOException e) {
                    throw this.f6930b.m9429b(e);
                } catch (Throwable th) {
                    this.f6930b.m9428a(false);
                }
            }

            public String toString() {
                return "AsyncTimeout.source(" + c2096s + ")";
            }
        };
    }

    /* renamed from: a */
    protected IOException mo3108a(IOException iOException) {
        IOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    /* renamed from: a */
    protected void mo3109a() {
    }

    /* renamed from: a */
    final void m9428a(boolean z) {
        if (d_() && z) {
            throw mo3108a(null);
        }
    }

    /* renamed from: b */
    final IOException m9429b(IOException iOException) {
        return !d_() ? iOException : mo3108a(iOException);
    }

    /* renamed from: c */
    public final void m9430c() {
        if (this.f6347c) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long e_ = e_();
        boolean f_ = f_();
        if (e_ != 0 || f_) {
            this.f6347c = true;
            C2099a.m9420a(this, e_, f_);
        }
    }

    public final boolean d_() {
        if (!this.f6347c) {
            return false;
        }
        this.f6347c = false;
        return C2099a.m9421a(this);
    }
}
