package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.C2127j;
import com.persianswitch.p122a.p123a.C2185k;
import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.p127b.C2162s;
import com.persianswitch.p122a.p123a.p128c.C2171b;
import java.lang.ref.Reference;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: com.persianswitch.a.j */
public final class C2204j {
    /* renamed from: c */
    static final /* synthetic */ boolean f6744c = (!C2204j.class.desiredAssertionStatus());
    /* renamed from: d */
    private static final Executor f6745d = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), C2187l.m9896a("OkHttp ConnectionPool", true));
    /* renamed from: a */
    final C2185k f6746a;
    /* renamed from: b */
    boolean f6747b;
    /* renamed from: e */
    private final int f6748e;
    /* renamed from: f */
    private final long f6749f;
    /* renamed from: g */
    private final Runnable f6750g;
    /* renamed from: h */
    private final Deque<C2171b> f6751h;

    /* renamed from: com.persianswitch.a.j$1 */
    class C22031 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C2204j f6743a;

        C22031(C2204j c2204j) {
            this.f6743a = c2204j;
        }

        public void run() {
            while (true) {
                long a = this.f6743a.m9961a(System.nanoTime());
                if (a != -1) {
                    if (a > 0) {
                        long j = a / C3446C.MICROS_PER_SECOND;
                        a -= j * C3446C.MICROS_PER_SECOND;
                        synchronized (this.f6743a) {
                            try {
                                this.f6743a.wait(j, (int) a);
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    public C2204j() {
        this(5, 5, TimeUnit.MINUTES);
    }

    public C2204j(int i, long j, TimeUnit timeUnit) {
        this.f6750g = new C22031(this);
        this.f6751h = new ArrayDeque();
        this.f6746a = new C2185k();
        this.f6748e = i;
        this.f6749f = timeUnit.toNanos(j);
        if (j <= 0) {
            throw new IllegalArgumentException("keepAliveDuration <= 0: " + j);
        }
    }

    /* renamed from: a */
    private int m9960a(C2171b c2171b, long j) {
        List list = c2171b.f6582h;
        int i = 0;
        while (i < list.size()) {
            if (((Reference) list.get(i)).get() != null) {
                i++;
            } else {
                C2127j.m9615c().mo3132a(5, "A connection to " + c2171b.mo3152a().m9925a().m9914a() + " was leaked. Did you forget to close a response body?", null);
                list.remove(i);
                c2171b.f6583i = true;
                if (list.isEmpty()) {
                    c2171b.f6584j = j - this.f6749f;
                    return 0;
                }
            }
        }
        return list.size();
    }

    /* renamed from: a */
    long m9961a(long j) {
        C2171b c2171b = null;
        long j2 = Long.MIN_VALUE;
        synchronized (this) {
            long j3;
            int i = 0;
            int i2 = 0;
            for (C2171b c2171b2 : this.f6751h) {
                if (m9960a(c2171b2, j) > 0) {
                    i2++;
                } else {
                    C2171b c2171b3;
                    int i3 = i + 1;
                    long j4 = j - c2171b2.f6584j;
                    if (j4 > j2) {
                        long j5 = j4;
                        c2171b3 = c2171b2;
                        j3 = j5;
                    } else {
                        c2171b3 = c2171b;
                        j3 = j2;
                    }
                    j2 = j3;
                    c2171b = c2171b3;
                    i = i3;
                }
            }
            if (j2 >= this.f6749f || i > this.f6748e) {
                this.f6751h.remove(c2171b);
                C2187l.m9900a(c2171b.m9820b());
                return 0;
            } else if (i > 0) {
                j3 = this.f6749f - j2;
                return j3;
            } else if (i2 > 0) {
                j3 = this.f6749f;
                return j3;
            } else {
                this.f6747b = false;
                return -1;
            }
        }
    }

    /* renamed from: a */
    C2171b m9962a(C2189a c2189a, C2162s c2162s) {
        if (f6744c || Thread.holdsLock(this)) {
            for (C2171b c2171b : this.f6751h) {
                if (c2171b.f6582h.size() < c2171b.f6581g && c2189a.equals(c2171b.mo3152a().f6650a) && !c2171b.f6583i) {
                    c2162s.m9774a(c2171b);
                    return c2171b;
                }
            }
            return null;
        }
        throw new AssertionError();
    }

    /* renamed from: a */
    void m9963a(C2171b c2171b) {
        if (f6744c || Thread.holdsLock(this)) {
            if (!this.f6747b) {
                this.f6747b = true;
                f6745d.execute(this.f6750g);
            }
            this.f6751h.add(c2171b);
            return;
        }
        throw new AssertionError();
    }

    /* renamed from: b */
    boolean m9964b(C2171b c2171b) {
        if (!f6744c && !Thread.holdsLock(this)) {
            throw new AssertionError();
        } else if (c2171b.f6583i || this.f6748e == 0) {
            this.f6751h.remove(c2171b);
            return true;
        } else {
            notifyAll();
            return false;
        }
    }
}
