package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.p124d.C2071b;
import com.persianswitch.p126b.C2245f;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;

/* renamed from: com.persianswitch.a.g */
public final class C2201g {
    /* renamed from: a */
    public static final C2201g f6688a = new C2199a().m9950a();
    /* renamed from: b */
    private final List<C2200b> f6689b;
    /* renamed from: c */
    private final C2071b f6690c;

    /* renamed from: com.persianswitch.a.g$a */
    public static final class C2199a {
        /* renamed from: a */
        private final List<C2200b> f6683a = new ArrayList();

        /* renamed from: a */
        public C2201g m9950a() {
            return new C2201g(C2187l.m9893a(this.f6683a), null);
        }
    }

    /* renamed from: com.persianswitch.a.g$b */
    static final class C2200b {
        /* renamed from: a */
        final String f6684a;
        /* renamed from: b */
        final String f6685b;
        /* renamed from: c */
        final String f6686c;
        /* renamed from: d */
        final C2245f f6687d;

        /* renamed from: a */
        boolean m9951a(String str) {
            if (!this.f6684a.startsWith("*.")) {
                return str.equals(this.f6685b);
            }
            return str.regionMatches(false, str.indexOf(46) + 1, this.f6685b, 0, this.f6685b.length());
        }

        public boolean equals(Object obj) {
            return (obj instanceof C2200b) && this.f6684a.equals(((C2200b) obj).f6684a) && this.f6686c.equals(((C2200b) obj).f6686c) && this.f6687d.equals(((C2200b) obj).f6687d);
        }

        public int hashCode() {
            return ((((this.f6684a.hashCode() + 527) * 31) + this.f6686c.hashCode()) * 31) + this.f6687d.hashCode();
        }

        public String toString() {
            return this.f6686c + this.f6687d.mo3213b();
        }
    }

    private C2201g(List<C2200b> list, C2071b c2071b) {
        this.f6689b = list;
        this.f6690c = c2071b;
    }

    /* renamed from: a */
    static C2245f m9952a(X509Certificate x509Certificate) {
        return C2187l.m9889a(C2245f.m10319a(x509Certificate.getPublicKey().getEncoded()));
    }

    /* renamed from: a */
    public static String m9953a(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            return "sha256/" + C2201g.m9954b((X509Certificate) certificate).mo3213b();
        }
        throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    }

    /* renamed from: b */
    static C2245f m9954b(X509Certificate x509Certificate) {
        return C2187l.m9907b(C2245f.m10319a(x509Certificate.getPublicKey().getEncoded()));
    }

    /* renamed from: a */
    C2201g m9955a(C2071b c2071b) {
        return this.f6690c != c2071b ? new C2201g(this.f6689b, c2071b) : this;
    }

    /* renamed from: a */
    List<C2200b> m9956a(String str) {
        List<C2200b> emptyList = Collections.emptyList();
        for (C2200b c2200b : this.f6689b) {
            if (c2200b.m9951a(str)) {
                if (emptyList.isEmpty()) {
                    emptyList = new ArrayList();
                }
                emptyList.add(c2200b);
            }
        }
        return emptyList;
    }

    /* renamed from: a */
    public void m9957a(String str, List<Certificate> list) {
        List a = m9956a(str);
        if (!a.isEmpty()) {
            List a2;
            int i;
            if (this.f6690c != null) {
                a2 = this.f6690c.mo3088a(list, str);
            }
            int size = a2.size();
            for (int i2 = 0; i2 < size; i2++) {
                X509Certificate x509Certificate = (X509Certificate) a2.get(i2);
                int size2 = a.size();
                int i3 = 0;
                Object obj = null;
                Object obj2 = null;
                while (i3 < size2) {
                    C2200b c2200b = (C2200b) a.get(i3);
                    if (c2200b.f6686c.equals("sha256/")) {
                        if (obj == null) {
                            obj = C2201g.m9954b(x509Certificate);
                        }
                        if (c2200b.f6687d.equals(obj)) {
                            return;
                        }
                    } else if (c2200b.f6686c.equals("sha1/")) {
                        if (obj2 == null) {
                            obj2 = C2201g.m9952a(x509Certificate);
                        }
                        if (c2200b.f6687d.equals(obj2)) {
                            return;
                        }
                    } else {
                        throw new AssertionError();
                    }
                    Object obj3 = obj;
                    i3++;
                    obj2 = obj2;
                    obj = obj3;
                }
            }
            StringBuilder append = new StringBuilder().append("Certificate pinning failure!").append("\n  Peer certificate chain:");
            int size3 = a2.size();
            for (i = 0; i < size3; i++) {
                Certificate certificate = (X509Certificate) a2.get(i);
                append.append("\n    ").append(C2201g.m9953a(certificate)).append(": ").append(certificate.getSubjectDN().getName());
            }
            append.append("\n  Pinned certificates for ").append(str).append(":");
            size3 = a.size();
            for (i = 0; i < size3; i++) {
                append.append("\n    ").append((C2200b) a.get(i));
            }
            throw new SSLPeerUnverifiedException(append.toString());
        }
    }
}
