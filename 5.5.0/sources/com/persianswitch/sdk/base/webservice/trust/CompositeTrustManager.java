package com.persianswitch.sdk.base.webservice.trust;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.net.ssl.X509TrustManager;

public class CompositeTrustManager implements X509TrustManager {
    /* renamed from: a */
    private ArrayList<X509TrustManager> f7292a = new ArrayList();
    /* renamed from: b */
    private boolean f7293b;

    protected CompositeTrustManager(X509TrustManager[] x509TrustManagerArr, boolean z) {
        if (x509TrustManagerArr != null) {
            m10973c(x509TrustManagerArr);
        }
        m10970a(z);
    }

    /* renamed from: a */
    public static CompositeTrustManager m10967a(X509TrustManager... x509TrustManagerArr) {
        return new CompositeTrustManager(x509TrustManagerArr, true);
    }

    /* renamed from: b */
    public static CompositeTrustManager m10968b(X509TrustManager... x509TrustManagerArr) {
        return new CompositeTrustManager(x509TrustManagerArr, false);
    }

    /* renamed from: a */
    public void m10969a(X509TrustManager x509TrustManager) {
        this.f7292a.add(x509TrustManager);
    }

    /* renamed from: a */
    public void m10970a(boolean z) {
        if (this.f7292a.size() > 1) {
            throw new IllegalStateException("Cannot change mode once 2+ managers added");
        }
        this.f7293b = z;
    }

    /* renamed from: a */
    public boolean m10971a() {
        return this.f7293b;
    }

    /* renamed from: b */
    public int m10972b() {
        return this.f7292a.size();
    }

    /* renamed from: c */
    public void m10973c(X509TrustManager[] x509TrustManagerArr) {
        for (Object add : x509TrustManagerArr) {
            this.f7292a.add(add);
        }
    }

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        CertificateException certificateException;
        CertificateException certificateException2 = null;
        Iterator it = this.f7292a.iterator();
        while (it.hasNext()) {
            try {
                ((X509TrustManager) it.next()).checkClientTrusted(x509CertificateArr, str);
                if (this.f7293b) {
                    certificateException = certificateException2;
                    certificateException2 = certificateException;
                } else {
                    return;
                }
            } catch (CertificateException e) {
                certificateException = e;
                if (this.f7293b) {
                    throw certificateException;
                }
            }
        }
        if (certificateException2 != null) {
            throw certificateException2;
        }
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        CertificateException certificateException = null;
        Iterator it = this.f7292a.iterator();
        while (it.hasNext()) {
            CertificateException certificateException2;
            try {
                ((X509TrustManager) it.next()).checkClientTrusted(x509CertificateArr, str);
                if (this.f7293b) {
                    certificateException2 = certificateException;
                    certificateException = certificateException2;
                } else {
                    return;
                }
            } catch (CertificateException e) {
                certificateException2 = e;
                if (this.f7293b) {
                    throw certificateException2;
                }
            }
        }
        if (certificateException != null) {
            throw certificateException;
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        HashSet hashSet = new HashSet();
        Iterator it = this.f7292a.iterator();
        while (it.hasNext()) {
            for (Object add : ((X509TrustManager) it.next()).getAcceptedIssuers()) {
                hashSet.add(add);
            }
        }
        return (X509Certificate[]) hashSet.toArray(new X509Certificate[hashSet.size()]);
    }
}
