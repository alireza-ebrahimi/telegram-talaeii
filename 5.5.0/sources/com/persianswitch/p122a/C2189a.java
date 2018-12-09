package com.persianswitch.p122a;

import com.persianswitch.p122a.C2221r.C2220a;
import com.persianswitch.p122a.p123a.C2187l;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/* renamed from: com.persianswitch.a.a */
public final class C2189a {
    /* renamed from: a */
    final C2221r f6639a;
    /* renamed from: b */
    final C2212o f6640b;
    /* renamed from: c */
    final SocketFactory f6641c;
    /* renamed from: d */
    final C2190b f6642d;
    /* renamed from: e */
    final List<C2226v> f6643e;
    /* renamed from: f */
    final List<C2207k> f6644f;
    /* renamed from: g */
    final ProxySelector f6645g;
    /* renamed from: h */
    final Proxy f6646h;
    /* renamed from: i */
    final SSLSocketFactory f6647i;
    /* renamed from: j */
    final HostnameVerifier f6648j;
    /* renamed from: k */
    final C2201g f6649k;

    public C2189a(String str, int i, C2212o c2212o, SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier, C2201g c2201g, C2190b c2190b, Proxy proxy, List<C2226v> list, List<C2207k> list2, ProxySelector proxySelector) {
        this.f6639a = new C2220a().m10046a(sSLSocketFactory != null ? "https" : "http").m10048b(str).m10045a(i).m10050c();
        if (c2212o == null) {
            throw new NullPointerException("dns == null");
        }
        this.f6640b = c2212o;
        if (socketFactory == null) {
            throw new NullPointerException("socketFactory == null");
        }
        this.f6641c = socketFactory;
        if (c2190b == null) {
            throw new NullPointerException("proxyAuthenticator == null");
        }
        this.f6642d = c2190b;
        if (list == null) {
            throw new NullPointerException("protocols == null");
        }
        this.f6643e = C2187l.m9893a((List) list);
        if (list2 == null) {
            throw new NullPointerException("connectionSpecs == null");
        }
        this.f6644f = C2187l.m9893a((List) list2);
        if (proxySelector == null) {
            throw new NullPointerException("proxySelector == null");
        }
        this.f6645g = proxySelector;
        this.f6646h = proxy;
        this.f6647i = sSLSocketFactory;
        this.f6648j = hostnameVerifier;
        this.f6649k = c2201g;
    }

    /* renamed from: a */
    public C2221r m9914a() {
        return this.f6639a;
    }

    /* renamed from: b */
    public C2212o m9915b() {
        return this.f6640b;
    }

    /* renamed from: c */
    public SocketFactory m9916c() {
        return this.f6641c;
    }

    /* renamed from: d */
    public C2190b m9917d() {
        return this.f6642d;
    }

    /* renamed from: e */
    public List<C2226v> m9918e() {
        return this.f6643e;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C2189a)) {
            return false;
        }
        C2189a c2189a = (C2189a) obj;
        return this.f6639a.equals(c2189a.f6639a) && this.f6640b.equals(c2189a.f6640b) && this.f6642d.equals(c2189a.f6642d) && this.f6643e.equals(c2189a.f6643e) && this.f6644f.equals(c2189a.f6644f) && this.f6645g.equals(c2189a.f6645g) && C2187l.m9903a(this.f6646h, c2189a.f6646h) && C2187l.m9903a(this.f6647i, c2189a.f6647i) && C2187l.m9903a(this.f6648j, c2189a.f6648j) && C2187l.m9903a(this.f6649k, c2189a.f6649k);
    }

    /* renamed from: f */
    public List<C2207k> m9919f() {
        return this.f6644f;
    }

    /* renamed from: g */
    public ProxySelector m9920g() {
        return this.f6645g;
    }

    /* renamed from: h */
    public Proxy m9921h() {
        return this.f6646h;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.f6648j != null ? this.f6648j.hashCode() : 0) + (((this.f6647i != null ? this.f6647i.hashCode() : 0) + (((this.f6646h != null ? this.f6646h.hashCode() : 0) + ((((((((((((this.f6639a.hashCode() + 527) * 31) + this.f6640b.hashCode()) * 31) + this.f6642d.hashCode()) * 31) + this.f6643e.hashCode()) * 31) + this.f6644f.hashCode()) * 31) + this.f6645g.hashCode()) * 31)) * 31)) * 31)) * 31;
        if (this.f6649k != null) {
            i = this.f6649k.hashCode();
        }
        return hashCode + i;
    }

    /* renamed from: i */
    public SSLSocketFactory m9922i() {
        return this.f6647i;
    }

    /* renamed from: j */
    public HostnameVerifier m9923j() {
        return this.f6648j;
    }

    /* renamed from: k */
    public C2201g m9924k() {
        return this.f6649k;
    }
}
