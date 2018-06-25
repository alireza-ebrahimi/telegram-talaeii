package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.C2187l;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;

/* renamed from: com.persianswitch.a.k */
public final class C2207k {
    /* renamed from: a */
    public static final C2207k f6756a = new C2206a(true).m9971a(f6759d).m9970a(ac.TLS_1_2, ac.TLS_1_1, ac.TLS_1_0).m9969a(true).m9973a();
    /* renamed from: b */
    public static final C2207k f6757b = new C2206a(f6756a).m9970a(ac.TLS_1_0).m9969a(true).m9973a();
    /* renamed from: c */
    public static final C2207k f6758c = new C2206a(false).m9973a();
    /* renamed from: d */
    private static final C2202h[] f6759d = new C2202h[]{C2202h.aK, C2202h.aO, C2202h.f6713W, C2202h.am, C2202h.al, C2202h.av, C2202h.aw, C2202h.f6696F, C2202h.f6700J, C2202h.f6711U, C2202h.f6694D, C2202h.f6698H, C2202h.f6724h};
    /* renamed from: e */
    private final boolean f6760e;
    /* renamed from: f */
    private final boolean f6761f;
    /* renamed from: g */
    private final String[] f6762g;
    /* renamed from: h */
    private final String[] f6763h;

    /* renamed from: com.persianswitch.a.k$a */
    public static final class C2206a {
        /* renamed from: a */
        private boolean f6752a;
        /* renamed from: b */
        private String[] f6753b;
        /* renamed from: c */
        private String[] f6754c;
        /* renamed from: d */
        private boolean f6755d;

        public C2206a(C2207k c2207k) {
            this.f6752a = c2207k.f6760e;
            this.f6753b = c2207k.f6762g;
            this.f6754c = c2207k.f6763h;
            this.f6755d = c2207k.f6761f;
        }

        C2206a(boolean z) {
            this.f6752a = z;
        }

        /* renamed from: a */
        public C2206a m9969a(boolean z) {
            if (this.f6752a) {
                this.f6755d = z;
                return this;
            }
            throw new IllegalStateException("no TLS extensions for cleartext connections");
        }

        /* renamed from: a */
        public C2206a m9970a(ac... acVarArr) {
            if (this.f6752a) {
                String[] strArr = new String[acVarArr.length];
                for (int i = 0; i < acVarArr.length; i++) {
                    strArr[i] = acVarArr[i].f6658e;
                }
                return m9974b(strArr);
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }

        /* renamed from: a */
        public C2206a m9971a(C2202h... c2202hArr) {
            if (this.f6752a) {
                String[] strArr = new String[c2202hArr.length];
                for (int i = 0; i < c2202hArr.length; i++) {
                    strArr[i] = c2202hArr[i].aS;
                }
                return m9972a(strArr);
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        /* renamed from: a */
        public C2206a m9972a(String... strArr) {
            if (!this.f6752a) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            } else if (strArr.length == 0) {
                throw new IllegalArgumentException("At least one cipher suite is required");
            } else {
                this.f6753b = (String[]) strArr.clone();
                return this;
            }
        }

        /* renamed from: a */
        public C2207k m9973a() {
            return new C2207k();
        }

        /* renamed from: b */
        public C2206a m9974b(String... strArr) {
            if (!this.f6752a) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            } else if (strArr.length == 0) {
                throw new IllegalArgumentException("At least one TLS version is required");
            } else {
                this.f6754c = (String[]) strArr.clone();
                return this;
            }
        }
    }

    private C2207k(C2206a c2206a) {
        this.f6760e = c2206a.f6752a;
        this.f6762g = c2206a.f6753b;
        this.f6763h = c2206a.f6754c;
        this.f6761f = c2206a.f6755d;
    }

    /* renamed from: a */
    private static boolean m9976a(String[] strArr, String[] strArr2) {
        if (strArr == null || strArr2 == null || strArr.length == 0 || strArr2.length == 0) {
            return false;
        }
        for (String a : strArr) {
            if (C2187l.m9904a(strArr2, a)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    private C2207k m9977b(SSLSocket sSLSocket, boolean z) {
        String[] enabledCipherSuites = this.f6762g != null ? (String[]) C2187l.m9905a(String.class, this.f6762g, sSLSocket.getEnabledCipherSuites()) : sSLSocket.getEnabledCipherSuites();
        String[] enabledProtocols = this.f6763h != null ? (String[]) C2187l.m9905a(String.class, this.f6763h, sSLSocket.getEnabledProtocols()) : sSLSocket.getEnabledProtocols();
        if (z && C2187l.m9904a(sSLSocket.getSupportedCipherSuites(), "TLS_FALLBACK_SCSV")) {
            enabledCipherSuites = C2187l.m9910b(enabledCipherSuites, "TLS_FALLBACK_SCSV");
        }
        return new C2206a(this).m9972a(enabledCipherSuites).m9974b(enabledProtocols).m9973a();
    }

    /* renamed from: a */
    void m9981a(SSLSocket sSLSocket, boolean z) {
        C2207k b = m9977b(sSLSocket, z);
        if (b.f6763h != null) {
            sSLSocket.setEnabledProtocols(b.f6763h);
        }
        if (b.f6762g != null) {
            sSLSocket.setEnabledCipherSuites(b.f6762g);
        }
    }

    /* renamed from: a */
    public boolean m9982a() {
        return this.f6760e;
    }

    /* renamed from: a */
    public boolean m9983a(SSLSocket sSLSocket) {
        return !this.f6760e ? false : (this.f6763h == null || C2207k.m9976a(this.f6763h, sSLSocket.getEnabledProtocols())) ? this.f6762g == null || C2207k.m9976a(this.f6762g, sSLSocket.getEnabledCipherSuites()) : false;
    }

    /* renamed from: b */
    public List<C2202h> m9984b() {
        if (this.f6762g == null) {
            return null;
        }
        Object[] objArr = new C2202h[this.f6762g.length];
        for (int i = 0; i < this.f6762g.length; i++) {
            objArr[i] = C2202h.m9958a(this.f6762g[i]);
        }
        return C2187l.m9894a(objArr);
    }

    /* renamed from: c */
    public List<ac> m9985c() {
        if (this.f6763h == null) {
            return null;
        }
        Object[] objArr = new ac[this.f6763h.length];
        for (int i = 0; i < this.f6763h.length; i++) {
            objArr[i] = ac.m9929a(this.f6763h[i]);
        }
        return C2187l.m9894a(objArr);
    }

    /* renamed from: d */
    public boolean m9986d() {
        return this.f6761f;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C2207k)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        C2207k c2207k = (C2207k) obj;
        return this.f6760e == c2207k.f6760e ? !this.f6760e || (Arrays.equals(this.f6762g, c2207k.f6762g) && Arrays.equals(this.f6763h, c2207k.f6763h) && this.f6761f == c2207k.f6761f) : false;
    }

    public int hashCode() {
        if (!this.f6760e) {
            return 17;
        }
        return (this.f6761f ? 0 : 1) + ((((Arrays.hashCode(this.f6762g) + 527) * 31) + Arrays.hashCode(this.f6763h)) * 31);
    }

    public String toString() {
        if (!this.f6760e) {
            return "ConnectionSpec()";
        }
        return "ConnectionSpec(cipherSuites=" + (this.f6762g != null ? m9984b().toString() : "[all enabled]") + ", tlsVersions=" + (this.f6763h != null ? m9985c().toString() : "[all enabled]") + ", supportsTlsExtensions=" + this.f6761f + ")";
    }
}
