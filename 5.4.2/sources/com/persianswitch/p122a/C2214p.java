package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.C2187l;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

/* renamed from: com.persianswitch.a.p */
public final class C2214p {
    /* renamed from: a */
    private final ac f6786a;
    /* renamed from: b */
    private final C2202h f6787b;
    /* renamed from: c */
    private final List<Certificate> f6788c;
    /* renamed from: d */
    private final List<Certificate> f6789d;

    private C2214p(ac acVar, C2202h c2202h, List<Certificate> list, List<Certificate> list2) {
        this.f6786a = acVar;
        this.f6787b = c2202h;
        this.f6788c = list;
        this.f6789d = list2;
    }

    /* renamed from: a */
    public static C2214p m10011a(SSLSession sSLSession) {
        String cipherSuite = sSLSession.getCipherSuite();
        if (cipherSuite == null) {
            throw new IllegalStateException("cipherSuite == null");
        }
        C2202h a = C2202h.m9958a(cipherSuite);
        cipherSuite = sSLSession.getProtocol();
        if (cipherSuite == null) {
            throw new IllegalStateException("tlsVersion == null");
        }
        Object[] peerCertificates;
        ac a2 = ac.m9929a(cipherSuite);
        try {
            peerCertificates = sSLSession.getPeerCertificates();
        } catch (SSLPeerUnverifiedException e) {
            peerCertificates = null;
        }
        List a3 = peerCertificates != null ? C2187l.m9894a(peerCertificates) : Collections.emptyList();
        Object[] localCertificates = sSLSession.getLocalCertificates();
        return new C2214p(a2, a, a3, localCertificates != null ? C2187l.m9894a(localCertificates) : Collections.emptyList());
    }

    /* renamed from: a */
    public C2202h m10012a() {
        return this.f6787b;
    }

    /* renamed from: b */
    public List<Certificate> m10013b() {
        return this.f6788c;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C2214p)) {
            return false;
        }
        C2214p c2214p = (C2214p) obj;
        return C2187l.m9903a(this.f6787b, c2214p.f6787b) && this.f6787b.equals(c2214p.f6787b) && this.f6788c.equals(c2214p.f6788c) && this.f6789d.equals(c2214p.f6789d);
    }

    public int hashCode() {
        return (((((((this.f6786a != null ? this.f6786a.hashCode() : 0) + 527) * 31) + this.f6787b.hashCode()) * 31) + this.f6788c.hashCode()) * 31) + this.f6789d.hashCode();
    }
}
