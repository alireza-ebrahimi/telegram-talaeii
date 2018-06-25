package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.C2133s;
import com.persianswitch.p122a.C2133s.C2154a;
import com.persianswitch.p122a.C2170i;
import com.persianswitch.p122a.C2221r;
import com.persianswitch.p122a.C2222t;
import com.persianswitch.p122a.C2225u;
import com.persianswitch.p122a.C2231x;
import com.persianswitch.p122a.C2231x.C2230a;
import com.persianswitch.p122a.C2232y;
import com.persianswitch.p122a.C2236z;
import com.persianswitch.p122a.ab;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpRetryException;
import java.net.ProtocolException;
import java.net.Proxy.Type;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;

/* renamed from: com.persianswitch.a.a.b.o */
public final class C2158o implements C2133s {
    /* renamed from: a */
    private final C2225u f6533a;
    /* renamed from: b */
    private C2151h f6534b;
    /* renamed from: c */
    private boolean f6535c;
    /* renamed from: d */
    private volatile boolean f6536d;

    public C2158o(C2225u c2225u) {
        this.f6533a = c2225u;
    }

    /* renamed from: a */
    private C2151h m9744a(IOException iOException, boolean z, C2231x c2231x) {
        this.f6534b.f6520b.m9775a(iOException);
        if (!this.f6533a.m10123r()) {
            return null;
        }
        if ((!z && (c2231x.m10161d() instanceof C2163t)) || !m9747a(iOException, z) || !this.f6534b.f6520b.m9780e()) {
            return null;
        }
        return new C2151h(this.f6533a, this.f6534b.f6522d, this.f6534b.m9718a(null), this.f6534b.f6521c);
    }

    /* renamed from: a */
    private C2231x m9745a(C2236z c2236z) {
        if (c2236z == null) {
            throw new IllegalStateException();
        }
        C2170i b = this.f6534b.f6520b.m9777b();
        ab a = b != null ? b.mo3152a() : null;
        int b2 = c2236z.m10217b();
        String b3 = c2236z.m10214a().m10159b();
        switch (b2) {
            case 300:
            case 301:
            case 302:
            case 303:
                break;
            case 307:
            case 308:
                if (!(b3.equals("GET") || b3.equals("HEAD"))) {
                    return null;
                }
            case 401:
                return this.f6533a.m10118m().mo3156a(a, c2236z);
            case 407:
                if ((a != null ? a.m9926b() : this.f6533a.m10109d()).type() == Type.HTTP) {
                    return this.f6533a.m10119n().mo3156a(a, c2236z);
                }
                throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
            case 408:
                return !(c2236z.m10214a().m10161d() instanceof C2163t) ? c2236z.m10214a() : null;
            default:
                return null;
        }
        if (!this.f6533a.m10122q()) {
            return null;
        }
        String a2 = c2236z.m10215a("Location");
        if (a2 == null) {
            return null;
        }
        C2221r c = c2236z.m10214a().m10157a().m10070c(a2);
        if (c == null) {
            return null;
        }
        if (!c.m10069b().equals(c2236z.m10214a().m10157a().m10069b()) && !this.f6533a.m10121p()) {
            return null;
        }
        C2230a e = c2236z.m10214a().m10162e();
        if (C2152i.m9723c(b3)) {
            if (C2152i.m9724d(b3)) {
                e.m10148a("GET", null);
            } else {
                e.m10148a(b3, null);
            }
            e.m10151b("Transfer-Encoding");
            e.m10151b("Content-Length");
            e.m10151b("Content-Type");
        }
        if (!m9746a(c2236z, c)) {
            e.m10151b("Authorization");
        }
        return e.m10145a(c).m10150a();
    }

    /* renamed from: a */
    private boolean m9746a(C2236z c2236z, C2221r c2221r) {
        C2221r a = c2236z.m10214a().m10157a();
        return a.m10075f().equals(c2221r.m10075f()) && a.m10076g() == c2221r.m10076g() && a.m10069b().equals(c2221r.m10069b());
    }

    /* renamed from: a */
    private boolean m9747a(IOException iOException, boolean z) {
        boolean z2 = true;
        if (iOException instanceof ProtocolException) {
            return false;
        }
        if (!(iOException instanceof InterruptedIOException)) {
            return (((iOException instanceof SSLHandshakeException) && (iOException.getCause() instanceof CertificateException)) || (iOException instanceof SSLPeerUnverifiedException)) ? false : true;
        } else {
            if (!((iOException instanceof SocketTimeoutException) && z)) {
                z2 = false;
            }
            return z2;
        }
    }

    /* renamed from: a */
    public C2236z mo3136a(C2154a c2154a) {
        C2151h a;
        Throwable th;
        Object obj = null;
        C2231x a2 = c2154a.mo3146a();
        C2232y d = a2.m10161d();
        if (d != null) {
            C2230a e = a2.m10162e();
            C2222t a3 = d.mo3169a();
            if (a3 != null) {
                e.m10149a("Content-Type", a3.toString());
            }
            long b = d.mo3171b();
            if (b != -1) {
                e.m10149a("Content-Length", Long.toString(b));
                e.m10151b("Transfer-Encoding");
            } else {
                e.m10149a("Transfer-Encoding", "chunked");
                e.m10151b("Content-Length");
            }
            a2 = e.m10150a();
        }
        this.f6534b = new C2151h(this.f6533a, a2.m10157a(), null, null);
        int i = 0;
        C2231x c2231x = a2;
        while (!this.f6536d) {
            try {
                C2236z a4 = this.f6534b.m9719a(c2231x, (C2155l) c2154a);
                c2231x = m9745a(a4);
                if (c2231x == null) {
                    if (!this.f6535c) {
                        this.f6534b.f6520b.m9778c();
                    }
                    return a4;
                }
                C2162s a5 = this.f6534b.m9718a(a4);
                int i2 = i + 1;
                if (i2 > 20) {
                    a5.m9778c();
                    throw new ProtocolException("Too many follow-up requests: " + i2);
                } else if (c2231x.m10161d() instanceof C2163t) {
                    throw new HttpRetryException("Cannot retry streamed HTTP body", a4.m10217b());
                } else {
                    C2162s c2162s;
                    if (!m9746a(a4, c2231x.m10157a())) {
                        a5.m9778c();
                        c2162s = null;
                    } else if (a5.m9772a() != null) {
                        throw new IllegalStateException("Closing the body of " + a4 + " didn't close its backing stream. Bad interceptor?");
                    } else {
                        c2162s = a5;
                    }
                    this.f6534b = new C2151h(this.f6533a, c2231x.m10157a(), c2162s, a4);
                    i = i2;
                }
            } catch (C2159p e2) {
                a = m9744a(e2.m9752a(), true, c2231x);
                if (a != null) {
                    this.f6534b = a;
                } else {
                    throw e2.m9752a();
                }
            } catch (IOException e3) {
                a = m9744a(e3, false, c2231x);
                if (a != null) {
                    this.f6534b = a;
                } else {
                    throw e3;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        this.f6534b.f6520b.m9778c();
        throw new IOException("Canceled");
        if (obj != null) {
            this.f6534b.m9718a(null).m9778c();
        }
        throw th;
    }

    /* renamed from: a */
    public boolean m9749a() {
        return this.f6536d;
    }

    /* renamed from: b */
    public boolean m9750b() {
        return this.f6535c;
    }
}
