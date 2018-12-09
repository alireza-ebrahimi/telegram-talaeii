package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.C2189a;
import com.persianswitch.p122a.C2204j;
import com.persianswitch.p122a.ab;
import com.persianswitch.p122a.p123a.C2179d;
import com.persianswitch.p122a.p123a.C2185k;
import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.p125a.C2073a;
import com.persianswitch.p122a.p123a.p125a.C2126p;
import com.persianswitch.p122a.p123a.p128c.C2171b;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/* renamed from: com.persianswitch.a.a.b.s */
public final class C2162s {
    /* renamed from: a */
    public final C2189a f6551a;
    /* renamed from: b */
    private ab f6552b;
    /* renamed from: c */
    private final C2204j f6553c;
    /* renamed from: d */
    private final C2160q f6554d;
    /* renamed from: e */
    private int f6555e;
    /* renamed from: f */
    private C2171b f6556f;
    /* renamed from: g */
    private boolean f6557g;
    /* renamed from: h */
    private boolean f6558h;
    /* renamed from: i */
    private C2143j f6559i;

    public C2162s(C2204j c2204j, C2189a c2189a) {
        this.f6553c = c2204j;
        this.f6551a = c2189a;
        this.f6554d = new C2160q(c2189a, m9771f());
    }

    /* renamed from: a */
    private C2171b m9767a(int i, int i2, int i3, boolean z) {
        C2171b c2171b;
        synchronized (this.f6553c) {
            if (this.f6557g) {
                throw new IllegalStateException("released");
            } else if (this.f6559i != null) {
                throw new IllegalStateException("stream != null");
            } else if (this.f6558h) {
                throw new IOException("Canceled");
            } else {
                c2171b = this.f6556f;
                if (c2171b == null || c2171b.f6583i) {
                    c2171b = C2179d.f6617a.mo3160a(this.f6553c, this.f6551a, this);
                    if (c2171b != null) {
                        this.f6556f = c2171b;
                    } else {
                        ab abVar;
                        ab abVar2 = this.f6552b;
                        if (abVar2 == null) {
                            abVar2 = this.f6554d.m9765b();
                            synchronized (this.f6553c) {
                                this.f6552b = abVar2;
                                this.f6555e = 0;
                            }
                            abVar = abVar2;
                        } else {
                            abVar = abVar2;
                        }
                        c2171b = new C2171b(abVar);
                        m9774a(c2171b);
                        synchronized (this.f6553c) {
                            C2179d.f6617a.mo3167b(this.f6553c, c2171b);
                            this.f6556f = c2171b;
                            if (this.f6558h) {
                                throw new IOException("Canceled");
                            }
                        }
                        c2171b.m9816a(i, i2, i3, this.f6551a.m9919f(), z);
                        m9771f().m9884b(c2171b.mo3152a());
                    }
                }
            }
        }
        return c2171b;
    }

    /* renamed from: a */
    private void m9768a(boolean z, boolean z2, boolean z3) {
        C2171b c2171b = null;
        synchronized (this.f6553c) {
            if (z3) {
                this.f6559i = null;
            }
            if (z2) {
                this.f6557g = true;
            }
            if (this.f6556f != null) {
                if (z) {
                    this.f6556f.f6583i = true;
                }
                if (this.f6559i == null && (this.f6557g || this.f6556f.f6583i)) {
                    m9770b(this.f6556f);
                    if (this.f6556f.f6582h.isEmpty()) {
                        this.f6556f.f6584j = System.nanoTime();
                        if (C2179d.f6617a.mo3166a(this.f6553c, this.f6556f)) {
                            c2171b = this.f6556f;
                        }
                    }
                    this.f6556f = null;
                }
            }
        }
        if (c2171b != null) {
            C2187l.m9900a(c2171b.m9820b());
        }
    }

    /* renamed from: b */
    private C2171b m9769b(int i, int i2, int i3, boolean z, boolean z2) {
        C2171b a;
        while (true) {
            a = m9767a(i, i2, i3, z);
            synchronized (this.f6553c) {
                if (a.f6578d != 0) {
                    if (a.m9819a(z2)) {
                        break;
                    }
                    m9779d();
                } else {
                    break;
                }
            }
        }
        return a;
    }

    /* renamed from: b */
    private void m9770b(C2171b c2171b) {
        int size = c2171b.f6582h.size();
        for (int i = 0; i < size; i++) {
            if (((Reference) c2171b.f6582h.get(i)).get() == this) {
                c2171b.f6582h.remove(i);
                return;
            }
        }
        throw new IllegalStateException();
    }

    /* renamed from: f */
    private C2185k m9771f() {
        return C2179d.f6617a.mo3162a(this.f6553c);
    }

    /* renamed from: a */
    public C2143j m9772a() {
        C2143j c2143j;
        synchronized (this.f6553c) {
            c2143j = this.f6559i;
        }
        return c2143j;
    }

    /* renamed from: a */
    public C2143j m9773a(int i, int i2, int i3, boolean z, boolean z2) {
        try {
            C2143j c2146f;
            C2171b b = m9769b(i, i2, i3, z, z2);
            if (b.f6577c != null) {
                c2146f = new C2146f(this, b.f6577c);
            } else {
                b.m9820b().setSoTimeout(i2);
                b.f6579e.mo3106a().mo3200a((long) i2, TimeUnit.MILLISECONDS);
                b.f6580f.mo3101a().mo3200a((long) i3, TimeUnit.MILLISECONDS);
                c2146f = new C2144e(this, b.f6579e, b.f6580f);
            }
            synchronized (this.f6553c) {
                this.f6559i = c2146f;
            }
            return c2146f;
        } catch (IOException e) {
            throw new C2159p(e);
        }
    }

    /* renamed from: a */
    public void m9774a(C2171b c2171b) {
        c2171b.f6582h.add(new WeakReference(this));
    }

    /* renamed from: a */
    public void m9775a(IOException iOException) {
        boolean z;
        synchronized (this.f6553c) {
            if (iOException instanceof C2126p) {
                C2126p c2126p = (C2126p) iOException;
                if (c2126p.f6447a == C2073a.REFUSED_STREAM) {
                    this.f6555e++;
                }
                if (c2126p.f6447a != C2073a.REFUSED_STREAM || this.f6555e > 1) {
                    this.f6552b = null;
                }
                z = false;
            } else {
                if (!(this.f6556f == null || this.f6556f.m9822d())) {
                    if (this.f6556f.f6578d == 0) {
                        if (!(this.f6552b == null || iOException == null)) {
                            this.f6554d.m9763a(this.f6552b, iOException);
                        }
                        this.f6552b = null;
                    }
                }
                z = false;
            }
            z = true;
        }
        m9768a(z, false, true);
    }

    /* renamed from: a */
    public void m9776a(boolean z, C2143j c2143j) {
        synchronized (this.f6553c) {
            if (c2143j != null) {
                if (c2143j == this.f6559i) {
                    if (!z) {
                        C2171b c2171b = this.f6556f;
                        c2171b.f6578d++;
                    }
                }
            }
            throw new IllegalStateException("expected " + this.f6559i + " but was " + c2143j);
        }
        m9768a(z, false, true);
    }

    /* renamed from: b */
    public synchronized C2171b m9777b() {
        return this.f6556f;
    }

    /* renamed from: c */
    public void m9778c() {
        m9768a(false, true, false);
    }

    /* renamed from: d */
    public void m9779d() {
        m9768a(true, false, false);
    }

    /* renamed from: e */
    public boolean m9780e() {
        return this.f6552b != null || this.f6554d.m9764a();
    }

    public String toString() {
        return this.f6551a.toString();
    }
}
