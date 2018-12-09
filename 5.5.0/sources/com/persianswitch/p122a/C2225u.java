package com.persianswitch.p122a;

import com.persianswitch.p122a.C2217q.C2216a;
import com.persianswitch.p122a.p123a.C2127j;
import com.persianswitch.p122a.p123a.C2179d;
import com.persianswitch.p122a.p123a.C2180e;
import com.persianswitch.p122a.p123a.C2185k;
import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.p124d.C2071b;
import com.persianswitch.p122a.p123a.p124d.C2175d;
import com.persianswitch.p122a.p123a.p127b.C2162s;
import com.persianswitch.p122a.p123a.p128c.C2171b;
import java.net.Proxy;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* renamed from: com.persianswitch.a.u */
public class C2225u implements Cloneable {
    /* renamed from: A */
    private static final List<C2207k> f6848A;
    /* renamed from: z */
    private static final List<C2226v> f6849z = C2187l.m9894a(C2226v.HTTP_2, C2226v.SPDY_3, C2226v.HTTP_1_1);
    /* renamed from: a */
    final C2211n f6850a;
    /* renamed from: b */
    final Proxy f6851b;
    /* renamed from: c */
    final List<C2226v> f6852c;
    /* renamed from: d */
    final List<C2207k> f6853d;
    /* renamed from: e */
    final List<C2133s> f6854e;
    /* renamed from: f */
    final List<C2133s> f6855f;
    /* renamed from: g */
    final ProxySelector f6856g;
    /* renamed from: h */
    final C2209m f6857h;
    /* renamed from: i */
    final C2192c f6858i;
    /* renamed from: j */
    final C2180e f6859j;
    /* renamed from: k */
    final SocketFactory f6860k;
    /* renamed from: l */
    final SSLSocketFactory f6861l;
    /* renamed from: m */
    final C2071b f6862m;
    /* renamed from: n */
    final HostnameVerifier f6863n;
    /* renamed from: o */
    final C2201g f6864o;
    /* renamed from: p */
    final C2190b f6865p;
    /* renamed from: q */
    final C2190b f6866q;
    /* renamed from: r */
    final C2204j f6867r;
    /* renamed from: s */
    final C2212o f6868s;
    /* renamed from: t */
    final boolean f6869t;
    /* renamed from: u */
    final boolean f6870u;
    /* renamed from: v */
    final boolean f6871v;
    /* renamed from: w */
    final int f6872w;
    /* renamed from: x */
    final int f6873x;
    /* renamed from: y */
    final int f6874y;

    /* renamed from: com.persianswitch.a.u$1 */
    static class C22231 extends C2179d {
        C22231() {
        }

        /* renamed from: a */
        public C2171b mo3160a(C2204j c2204j, C2189a c2189a, C2162s c2162s) {
            return c2204j.m9962a(c2189a, c2162s);
        }

        /* renamed from: a */
        public C2180e mo3161a(C2225u c2225u) {
            return c2225u.m10112g();
        }

        /* renamed from: a */
        public C2185k mo3162a(C2204j c2204j) {
            return c2204j.f6746a;
        }

        /* renamed from: a */
        public void mo3163a(C2207k c2207k, SSLSocket sSLSocket, boolean z) {
            c2207k.m9981a(sSLSocket, z);
        }

        /* renamed from: a */
        public void mo3164a(C2216a c2216a, String str) {
            c2216a.m10016a(str);
        }

        /* renamed from: a */
        public void mo3165a(C2216a c2216a, String str, String str2) {
            c2216a.m10020b(str, str2);
        }

        /* renamed from: a */
        public boolean mo3166a(C2204j c2204j, C2171b c2171b) {
            return c2204j.m9964b(c2171b);
        }

        /* renamed from: b */
        public void mo3167b(C2204j c2204j, C2171b c2171b) {
            c2204j.m9963a(c2171b);
        }
    }

    /* renamed from: com.persianswitch.a.u$a */
    public static final class C2224a {
        /* renamed from: a */
        C2211n f6823a = new C2211n();
        /* renamed from: b */
        Proxy f6824b;
        /* renamed from: c */
        List<C2226v> f6825c = C2225u.f6849z;
        /* renamed from: d */
        List<C2207k> f6826d = C2225u.f6848A;
        /* renamed from: e */
        final List<C2133s> f6827e = new ArrayList();
        /* renamed from: f */
        final List<C2133s> f6828f = new ArrayList();
        /* renamed from: g */
        ProxySelector f6829g = ProxySelector.getDefault();
        /* renamed from: h */
        C2209m f6830h = C2209m.f6777a;
        /* renamed from: i */
        C2192c f6831i;
        /* renamed from: j */
        C2180e f6832j;
        /* renamed from: k */
        SocketFactory f6833k = SocketFactory.getDefault();
        /* renamed from: l */
        SSLSocketFactory f6834l;
        /* renamed from: m */
        C2071b f6835m;
        /* renamed from: n */
        HostnameVerifier f6836n = C2175d.f6613a;
        /* renamed from: o */
        C2201g f6837o = C2201g.f6688a;
        /* renamed from: p */
        C2190b f6838p = C2190b.f6659a;
        /* renamed from: q */
        C2190b f6839q = C2190b.f6659a;
        /* renamed from: r */
        C2204j f6840r = new C2204j();
        /* renamed from: s */
        C2212o f6841s = C2212o.f6785a;
        /* renamed from: t */
        boolean f6842t = true;
        /* renamed from: u */
        boolean f6843u = true;
        /* renamed from: v */
        boolean f6844v = true;
        /* renamed from: w */
        int f6845w = 10000;
        /* renamed from: x */
        int f6846x = 10000;
        /* renamed from: y */
        int f6847y = 10000;

        /* renamed from: a */
        public C2224a m10094a(long j, TimeUnit timeUnit) {
            if (j < 0) {
                throw new IllegalArgumentException("timeout < 0");
            } else if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            } else {
                long toMillis = timeUnit.toMillis(j);
                if (toMillis > 2147483647L) {
                    throw new IllegalArgumentException("Timeout too large.");
                } else if (toMillis != 0 || j <= 0) {
                    this.f6845w = (int) toMillis;
                    return this;
                } else {
                    throw new IllegalArgumentException("Timeout too small.");
                }
            }
        }

        /* renamed from: a */
        public C2224a m10095a(HostnameVerifier hostnameVerifier) {
            if (hostnameVerifier == null) {
                throw new NullPointerException("hostnameVerifier == null");
            }
            this.f6836n = hostnameVerifier;
            return this;
        }

        /* renamed from: a */
        public C2224a m10096a(SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
            if (sSLSocketFactory == null) {
                throw new NullPointerException("sslSocketFactory == null");
            } else if (x509TrustManager == null) {
                throw new NullPointerException("trustManager == null");
            } else {
                this.f6834l = sSLSocketFactory;
                this.f6835m = C2071b.m9285a(x509TrustManager);
                return this;
            }
        }

        /* renamed from: a */
        public C2224a m10097a(boolean z) {
            this.f6844v = z;
            return this;
        }

        /* renamed from: a */
        public C2225u m10098a() {
            return new C2225u();
        }

        /* renamed from: b */
        public C2224a m10099b(long j, TimeUnit timeUnit) {
            if (j < 0) {
                throw new IllegalArgumentException("timeout < 0");
            } else if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            } else {
                long toMillis = timeUnit.toMillis(j);
                if (toMillis > 2147483647L) {
                    throw new IllegalArgumentException("Timeout too large.");
                } else if (toMillis != 0 || j <= 0) {
                    this.f6846x = (int) toMillis;
                    return this;
                } else {
                    throw new IllegalArgumentException("Timeout too small.");
                }
            }
        }

        /* renamed from: c */
        public C2224a m10100c(long j, TimeUnit timeUnit) {
            if (j < 0) {
                throw new IllegalArgumentException("timeout < 0");
            } else if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            } else {
                long toMillis = timeUnit.toMillis(j);
                if (toMillis > 2147483647L) {
                    throw new IllegalArgumentException("Timeout too large.");
                } else if (toMillis != 0 || j <= 0) {
                    this.f6847y = (int) toMillis;
                    return this;
                } else {
                    throw new IllegalArgumentException("Timeout too small.");
                }
            }
        }
    }

    static {
        List arrayList = new ArrayList(Arrays.asList(new C2207k[]{C2207k.f6756a, C2207k.f6757b}));
        if (C2127j.m9615c().mo3135a()) {
            arrayList.add(C2207k.f6758c);
        }
        f6848A = C2187l.m9893a(arrayList);
        C2179d.f6617a = new C22231();
    }

    public C2225u() {
        this(new C2224a());
    }

    private C2225u(C2224a c2224a) {
        this.f6850a = c2224a.f6823a;
        this.f6851b = c2224a.f6824b;
        this.f6852c = c2224a.f6825c;
        this.f6853d = c2224a.f6826d;
        this.f6854e = C2187l.m9893a(c2224a.f6827e);
        this.f6855f = C2187l.m9893a(c2224a.f6828f);
        this.f6856g = c2224a.f6829g;
        this.f6857h = c2224a.f6830h;
        this.f6858i = c2224a.f6831i;
        this.f6859j = c2224a.f6832j;
        this.f6860k = c2224a.f6833k;
        Object obj = null;
        for (C2207k a : this.f6853d) {
            Object obj2 = (obj != null || a.m9982a()) ? 1 : null;
            obj = obj2;
        }
        if (c2224a.f6834l != null || obj == null) {
            this.f6861l = c2224a.f6834l;
            this.f6862m = c2224a.f6835m;
        } else {
            X509TrustManager z = m10104z();
            this.f6861l = m10101a(z);
            this.f6862m = C2071b.m9285a(z);
        }
        this.f6863n = c2224a.f6836n;
        this.f6864o = c2224a.f6837o.m9955a(this.f6862m);
        this.f6865p = c2224a.f6838p;
        this.f6866q = c2224a.f6839q;
        this.f6867r = c2224a.f6840r;
        this.f6868s = c2224a.f6841s;
        this.f6869t = c2224a.f6842t;
        this.f6870u = c2224a.f6843u;
        this.f6871v = c2224a.f6844v;
        this.f6872w = c2224a.f6845w;
        this.f6873x = c2224a.f6846x;
        this.f6874y = c2224a.f6847y;
    }

    /* renamed from: a */
    private SSLSocketFactory m10101a(X509TrustManager x509TrustManager) {
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, new TrustManager[]{x509TrustManager}, null);
            return instance.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new AssertionError();
        }
    }

    /* renamed from: z */
    private X509TrustManager m10104z() {
        try {
            TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance.init((KeyStore) null);
            TrustManager[] trustManagers = instance.getTrustManagers();
            if (trustManagers.length == 1 && (trustManagers[0] instanceof X509TrustManager)) {
                return (X509TrustManager) trustManagers[0];
            }
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        } catch (GeneralSecurityException e) {
            throw new AssertionError();
        }
    }

    /* renamed from: a */
    public int m10105a() {
        return this.f6872w;
    }

    /* renamed from: a */
    public C2196e m10106a(C2231x c2231x) {
        return new C2228w(this, c2231x);
    }

    /* renamed from: b */
    public int m10107b() {
        return this.f6873x;
    }

    /* renamed from: c */
    public int m10108c() {
        return this.f6874y;
    }

    /* renamed from: d */
    public Proxy m10109d() {
        return this.f6851b;
    }

    /* renamed from: e */
    public ProxySelector m10110e() {
        return this.f6856g;
    }

    /* renamed from: f */
    public C2209m m10111f() {
        return this.f6857h;
    }

    /* renamed from: g */
    C2180e m10112g() {
        return this.f6858i != null ? this.f6858i.f6660a : this.f6859j;
    }

    /* renamed from: h */
    public C2212o m10113h() {
        return this.f6868s;
    }

    /* renamed from: i */
    public SocketFactory m10114i() {
        return this.f6860k;
    }

    /* renamed from: j */
    public SSLSocketFactory m10115j() {
        return this.f6861l;
    }

    /* renamed from: k */
    public HostnameVerifier m10116k() {
        return this.f6863n;
    }

    /* renamed from: l */
    public C2201g m10117l() {
        return this.f6864o;
    }

    /* renamed from: m */
    public C2190b m10118m() {
        return this.f6866q;
    }

    /* renamed from: n */
    public C2190b m10119n() {
        return this.f6865p;
    }

    /* renamed from: o */
    public C2204j m10120o() {
        return this.f6867r;
    }

    /* renamed from: p */
    public boolean m10121p() {
        return this.f6869t;
    }

    /* renamed from: q */
    public boolean m10122q() {
        return this.f6870u;
    }

    /* renamed from: r */
    public boolean m10123r() {
        return this.f6871v;
    }

    /* renamed from: s */
    public C2211n m10124s() {
        return this.f6850a;
    }

    /* renamed from: t */
    public List<C2226v> m10125t() {
        return this.f6852c;
    }

    /* renamed from: u */
    public List<C2207k> m10126u() {
        return this.f6853d;
    }

    /* renamed from: v */
    public List<C2133s> m10127v() {
        return this.f6854e;
    }

    /* renamed from: w */
    public List<C2133s> m10128w() {
        return this.f6855f;
    }
}
