package com.persianswitch.p122a.p123a.p128c;

import com.persianswitch.p122a.C2170i;
import com.persianswitch.p122a.C2189a;
import com.persianswitch.p122a.C2201g;
import com.persianswitch.p122a.C2207k;
import com.persianswitch.p122a.C2214p;
import com.persianswitch.p122a.C2221r;
import com.persianswitch.p122a.C2226v;
import com.persianswitch.p122a.C2231x;
import com.persianswitch.p122a.C2231x.C2230a;
import com.persianswitch.p122a.C2236z;
import com.persianswitch.p122a.ab;
import com.persianswitch.p122a.p123a.C2127j;
import com.persianswitch.p122a.p123a.C2164b;
import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.C2188m;
import com.persianswitch.p122a.p123a.p124d.C2175d;
import com.persianswitch.p122a.p123a.p125a.C2073a;
import com.persianswitch.p122a.p123a.p125a.C2092d;
import com.persianswitch.p122a.p123a.p125a.C2092d.C2085a;
import com.persianswitch.p122a.p123a.p125a.C2092d.C2086b;
import com.persianswitch.p122a.p123a.p125a.C2101e;
import com.persianswitch.p122a.p123a.p127b.C2144e;
import com.persianswitch.p122a.p123a.p127b.C2153k;
import com.persianswitch.p122a.p123a.p127b.C2159p;
import com.persianswitch.p122a.p123a.p127b.C2162s;
import com.persianswitch.p126b.C2096s;
import com.persianswitch.p126b.C2242d;
import com.persianswitch.p126b.C2243e;
import com.persianswitch.p126b.C2253l;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownServiceException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: com.persianswitch.a.a.c.b */
public final class C2171b extends C2086b implements C2170i {
    /* renamed from: b */
    public Socket f6576b;
    /* renamed from: c */
    public volatile C2092d f6577c;
    /* renamed from: d */
    public int f6578d;
    /* renamed from: e */
    public C2243e f6579e;
    /* renamed from: f */
    public C2242d f6580f;
    /* renamed from: g */
    public int f6581g;
    /* renamed from: h */
    public final List<Reference<C2162s>> f6582h = new ArrayList();
    /* renamed from: i */
    public boolean f6583i;
    /* renamed from: j */
    public long f6584j = Long.MAX_VALUE;
    /* renamed from: k */
    private final ab f6585k;
    /* renamed from: l */
    private Socket f6586l;
    /* renamed from: m */
    private C2214p f6587m;
    /* renamed from: n */
    private C2226v f6588n;

    public C2171b(ab abVar) {
        this.f6585k = abVar;
    }

    /* renamed from: a */
    private C2231x m9808a(int i, int i2, C2231x c2231x, C2221r c2221r) {
        String str = "CONNECT " + C2187l.m9890a(c2221r, true) + " HTTP/1.1";
        C2236z a;
        do {
            C2144e c2144e = new C2144e(null, this.f6579e, this.f6580f);
            this.f6579e.mo3106a().mo3200a((long) i, TimeUnit.MILLISECONDS);
            this.f6580f.mo3101a().mo3200a((long) i2, TimeUnit.MILLISECONDS);
            c2144e.m9671a(c2231x.m10160c(), str);
            c2144e.mo3142b();
            a = c2144e.m9676c().m10194a(c2231x).m10198a();
            long a2 = C2153k.m9726a(a);
            if (a2 == -1) {
                a2 = 0;
            }
            C2096s b = c2144e.m9673b(a2);
            C2187l.m9908b(b, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
            b.close();
            switch (a.m10217b()) {
                case Callback.DEFAULT_DRAG_ANIMATION_DURATION /*200*/:
                    if (this.f6579e.mo3177c().mo3181e() && this.f6580f.mo3177c().mo3181e()) {
                        return null;
                    }
                    throw new IOException("TLS tunnel buffered too many bytes!");
                case 407:
                    c2231x = this.f6585k.m9925a().m9917d().mo3156a(this.f6585k, a);
                    if (c2231x != null) {
                        break;
                    }
                    throw new IOException("Failed to authenticate with proxy");
                default:
                    throw new IOException("Unexpected response code for CONNECT: " + a.m10217b());
            }
        } while (!"close".equalsIgnoreCase(a.m10215a("Connection")));
        return c2231x;
    }

    /* renamed from: a */
    private void m9809a(int i, int i2, int i3, C2164b c2164b) {
        C2231x e = m9814e();
        C2221r a = e.m10157a();
        int i4 = 0;
        while (true) {
            i4++;
            if (i4 > 21) {
                throw new ProtocolException("Too many tunnel connections attempted: " + 21);
            }
            m9813c(i, i2, i3, c2164b);
            e = m9808a(i2, i3, e, a);
            if (e == null) {
                m9810a(i2, i3, c2164b);
                return;
            }
            C2187l.m9900a(this.f6586l);
            this.f6586l = null;
            this.f6580f = null;
            this.f6579e = null;
        }
    }

    /* renamed from: a */
    private void m9810a(int i, int i2, C2164b c2164b) {
        if (this.f6585k.m9925a().m9922i() != null) {
            m9812b(i, i2, c2164b);
        } else {
            this.f6588n = C2226v.HTTP_1_1;
            this.f6576b = this.f6586l;
        }
        if (this.f6588n == C2226v.SPDY_3 || this.f6588n == C2226v.HTTP_2) {
            this.f6576b.setSoTimeout(0);
            C2092d a = new C2085a(true).m9333a(this.f6576b, this.f6585k.m9925a().m9914a().m10075f(), this.f6579e, this.f6580f).m9332a(this.f6588n).m9331a((C2086b) this).m9334a();
            a.m9398d();
            this.f6581g = a.m9394b();
            this.f6577c = a;
            return;
        }
        this.f6581g = 1;
    }

    /* renamed from: b */
    private void m9811b(int i, int i2, int i3, C2164b c2164b) {
        m9813c(i, i2, i3, c2164b);
        m9810a(i2, i3, c2164b);
    }

    /* renamed from: b */
    private void m9812b(int i, int i2, C2164b c2164b) {
        Throwable th;
        Socket socket;
        AssertionError assertionError;
        Throwable th2;
        String str = null;
        C2189a a = this.f6585k.m9925a();
        try {
            Socket socket2 = (SSLSocket) a.m9922i().createSocket(this.f6586l, a.m9914a().m10075f(), a.m9914a().m10076g(), true);
            try {
                C2207k a2 = c2164b.m9782a((SSLSocket) socket2);
                if (a2.m9986d()) {
                    C2127j.m9615c().mo3134a((SSLSocket) socket2, a.m9914a().m10075f(), a.m9918e());
                }
                socket2.startHandshake();
                C2214p a3 = C2214p.m10011a(socket2.getSession());
                if (a.m9923j().verify(a.m9914a().m10075f(), socket2.getSession())) {
                    a.m9924k().m9957a(a.m9914a().m10075f(), a3.m10013b());
                    if (a2.m9986d()) {
                        str = C2127j.m9615c().mo3131a((SSLSocket) socket2);
                    }
                    this.f6576b = socket2;
                    this.f6579e = C2253l.m10358a(C2253l.m10363b(this.f6576b));
                    this.f6580f = C2253l.m10357a(C2253l.m10360a(this.f6576b));
                    this.f6587m = a3;
                    this.f6588n = str != null ? C2226v.m10129a(str) : C2226v.HTTP_1_1;
                    if (socket2 != null) {
                        C2127j.m9615c().mo3155b((SSLSocket) socket2);
                        return;
                    }
                    return;
                }
                Certificate certificate = (X509Certificate) a3.m10013b().get(0);
                throw new SSLPeerUnverifiedException("Hostname " + a.m9914a().m10075f() + " not verified:" + "\n    certificate: " + C2201g.m9953a(certificate) + "\n    DN: " + certificate.getSubjectDN().getName() + "\n    subjectAltNames: " + C2175d.m9842a(certificate));
            } catch (Throwable e) {
                th = e;
                socket = socket2;
                assertionError = th;
                try {
                    if (C2187l.m9902a(assertionError)) {
                        throw new IOException(assertionError);
                    }
                    throw assertionError;
                } catch (Throwable th3) {
                    th2 = th3;
                }
            } catch (Throwable e2) {
                th = e2;
                socket = socket2;
                th2 = th;
                if (socket != null) {
                    C2127j.m9615c().mo3155b((SSLSocket) socket);
                }
                C2187l.m9900a(socket);
                throw th2;
            }
        } catch (AssertionError e3) {
            assertionError = e3;
            if (C2187l.m9902a(assertionError)) {
                throw new IOException(assertionError);
            }
            throw assertionError;
        }
    }

    /* renamed from: c */
    private void m9813c(int i, int i2, int i3, C2164b c2164b) {
        Proxy b = this.f6585k.m9926b();
        Socket createSocket = (b.type() == Type.DIRECT || b.type() == Type.HTTP) ? this.f6585k.m9925a().m9916c().createSocket() : new Socket(b);
        this.f6586l = createSocket;
        this.f6586l.setSoTimeout(i2);
        try {
            C2127j.m9615c().mo3133a(this.f6586l, this.f6585k.m9927c(), i);
            this.f6579e = C2253l.m10358a(C2253l.m10363b(this.f6586l));
            this.f6580f = C2253l.m10357a(C2253l.m10360a(this.f6586l));
        } catch (ConnectException e) {
            throw new ConnectException("Failed to connect to " + this.f6585k.m9927c());
        }
    }

    /* renamed from: e */
    private C2231x m9814e() {
        return new C2230a().m10145a(this.f6585k.m9925a().m9914a()).m10149a("Host", C2187l.m9890a(this.f6585k.m9925a().m9914a(), true)).m10149a("Proxy-Connection", "Keep-Alive").m10149a("User-Agent", C2188m.m9913a()).m10150a();
    }

    /* renamed from: a */
    public ab mo3152a() {
        return this.f6585k;
    }

    /* renamed from: a */
    public void m9816a(int i, int i2, int i3, List<C2207k> list, boolean z) {
        if (this.f6588n != null) {
            throw new IllegalStateException("already connected");
        }
        C2164b c2164b = new C2164b(list);
        if (this.f6585k.m9925a().m9922i() != null || list.contains(C2207k.f6758c)) {
            C2159p c2159p = null;
            while (this.f6588n == null) {
                try {
                    if (this.f6585k.m9928d()) {
                        m9809a(i, i2, i3, c2164b);
                    } else {
                        m9811b(i, i2, i3, c2164b);
                    }
                } catch (IOException e) {
                    C2187l.m9900a(this.f6576b);
                    C2187l.m9900a(this.f6586l);
                    this.f6576b = null;
                    this.f6586l = null;
                    this.f6579e = null;
                    this.f6580f = null;
                    this.f6587m = null;
                    this.f6588n = null;
                    if (c2159p == null) {
                        c2159p = new C2159p(e);
                    } else {
                        c2159p.m9753a(e);
                    }
                    if (!z || !c2164b.m9783a(e)) {
                        throw c2159p;
                    }
                }
            }
            return;
        }
        throw new C2159p(new UnknownServiceException("CLEARTEXT communication not supported: " + list));
    }

    /* renamed from: a */
    public void mo3153a(C2092d c2092d) {
        this.f6581g = c2092d.m9394b();
    }

    /* renamed from: a */
    public void mo3090a(C2101e c2101e) {
        c2101e.m9448a(C2073a.REFUSED_STREAM);
    }

    /* renamed from: a */
    public boolean m9819a(boolean z) {
        if (this.f6576b.isClosed() || this.f6576b.isInputShutdown() || this.f6576b.isOutputShutdown()) {
            return false;
        }
        if (this.f6577c != null || !z) {
            return true;
        }
        int soTimeout;
        try {
            soTimeout = this.f6576b.getSoTimeout();
            this.f6576b.setSoTimeout(1);
            if (this.f6579e.mo3181e()) {
                this.f6576b.setSoTimeout(soTimeout);
                return false;
            }
            this.f6576b.setSoTimeout(soTimeout);
            return true;
        } catch (SocketTimeoutException e) {
            return true;
        } catch (IOException e2) {
            return false;
        } catch (Throwable th) {
            this.f6576b.setSoTimeout(soTimeout);
        }
    }

    /* renamed from: b */
    public Socket m9820b() {
        return this.f6576b;
    }

    /* renamed from: c */
    public C2214p m9821c() {
        return this.f6587m;
    }

    /* renamed from: d */
    public boolean m9822d() {
        return this.f6577c != null;
    }

    public String toString() {
        return "Connection{" + this.f6585k.m9925a().m9914a().m10075f() + ":" + this.f6585k.m9925a().m9914a().m10076g() + ", proxy=" + this.f6585k.m9926b() + " hostAddress=" + this.f6585k.m9927c() + " cipherSuite=" + (this.f6587m != null ? this.f6587m.m10012a() : "none") + " protocol=" + this.f6588n + '}';
    }
}
