package com.persianswitch.p122a.p123a.p124d;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

/* renamed from: com.persianswitch.a.a.d.e */
public abstract class C2176e {

    /* renamed from: com.persianswitch.a.a.d.e$a */
    static final class C2177a extends C2176e {
        /* renamed from: a */
        private final X509TrustManager f6614a;
        /* renamed from: b */
        private final Method f6615b;

        C2177a(X509TrustManager x509TrustManager, Method method) {
            this.f6615b = method;
            this.f6614a = x509TrustManager;
        }

        /* renamed from: a */
        public X509Certificate mo3154a(X509Certificate x509Certificate) {
            try {
                TrustAnchor trustAnchor = (TrustAnchor) this.f6615b.invoke(this.f6614a, new Object[]{x509Certificate});
                return trustAnchor != null ? trustAnchor.getTrustedCert() : null;
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            } catch (InvocationTargetException e2) {
                return null;
            }
        }
    }

    /* renamed from: com.persianswitch.a.a.d.e$b */
    static final class C2178b extends C2176e {
        /* renamed from: a */
        private final Map<X500Principal, List<X509Certificate>> f6616a = new LinkedHashMap();

        public C2178b(X509Certificate... x509CertificateArr) {
            for (X509Certificate x509Certificate : x509CertificateArr) {
                X500Principal subjectX500Principal = x509Certificate.getSubjectX500Principal();
                List list = (List) this.f6616a.get(subjectX500Principal);
                if (list == null) {
                    list = new ArrayList(1);
                    this.f6616a.put(subjectX500Principal, list);
                }
                list.add(x509Certificate);
            }
        }

        /* renamed from: a */
        public X509Certificate mo3154a(X509Certificate x509Certificate) {
            List<X509Certificate> list = (List) this.f6616a.get(x509Certificate.getIssuerX500Principal());
            if (list == null) {
                return null;
            }
            for (X509Certificate x509Certificate2 : list) {
                try {
                    x509Certificate.verify(x509Certificate2.getPublicKey());
                    return x509Certificate2;
                } catch (Exception e) {
                }
            }
            return null;
        }
    }

    /* renamed from: a */
    public static C2176e m9848a(X509TrustManager x509TrustManager) {
        try {
            Method declaredMethod = x509TrustManager.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", new Class[]{X509Certificate.class});
            declaredMethod.setAccessible(true);
            return new C2177a(x509TrustManager, declaredMethod);
        } catch (NoSuchMethodException e) {
            return C2176e.m9849a(x509TrustManager.getAcceptedIssuers());
        }
    }

    /* renamed from: a */
    public static C2176e m9849a(X509Certificate... x509CertificateArr) {
        return new C2178b(x509CertificateArr);
    }

    /* renamed from: a */
    public abstract X509Certificate mo3154a(X509Certificate x509Certificate);
}
