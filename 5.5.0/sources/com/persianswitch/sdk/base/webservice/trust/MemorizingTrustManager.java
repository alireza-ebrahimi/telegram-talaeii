package com.persianswitch.sdk.base.webservice.trust;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MemorizingTrustManager implements X509TrustManager {
    /* renamed from: a */
    private KeyStore f7297a;
    /* renamed from: b */
    private Options f7298b;
    /* renamed from: c */
    private X509TrustManager f7299c;
    /* renamed from: d */
    private KeyStore f7300d;
    /* renamed from: e */
    private X509TrustManager f7301e;

    public static class Options {
        /* renamed from: a */
        File f7294a;
        /* renamed from: b */
        String f7295b;
        /* renamed from: c */
        boolean f7296c;
    }

    /* renamed from: a */
    private void m10974a() {
        int i = 0;
        TrustManagerFactory instance = TrustManagerFactory.getInstance("X509");
        instance.init(this.f7297a);
        for (TrustManager trustManager : instance.getTrustManagers()) {
            TrustManager trustManager2;
            if (trustManager2 instanceof X509TrustManager) {
                this.f7299c = (X509TrustManager) trustManager2;
                break;
            }
        }
        instance = TrustManagerFactory.getInstance("X509");
        instance.init(this.f7300d);
        TrustManager[] trustManagers = instance.getTrustManagers();
        int length = trustManagers.length;
        while (i < length) {
            trustManager2 = trustManagers[i];
            if (trustManager2 instanceof X509TrustManager) {
                this.f7301e = (X509TrustManager) trustManager2;
                return;
            }
            i++;
        }
    }

    /* renamed from: a */
    public synchronized void m10975a(X509Certificate[] x509CertificateArr) {
        for (Certificate certificate : x509CertificateArr) {
            this.f7297a.setCertificateEntry(certificate.getSubjectDN().getName(), certificate);
        }
        m10974a();
        OutputStream fileOutputStream = new FileOutputStream(this.f7298b.f7294a);
        this.f7297a.store(fileOutputStream, this.f7298b.f7295b.toCharArray());
        fileOutputStream.close();
    }

    public synchronized void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        try {
            this.f7299c.checkClientTrusted(x509CertificateArr, str);
        } catch (CertificateException e) {
            try {
                this.f7301e.checkClientTrusted(x509CertificateArr, str);
            } catch (Throwable e2) {
                throw new CertificateMemorizationException(e2);
            } catch (CertificateException e3) {
                if (!this.f7298b.f7296c || this.f7298b.f7294a.exists()) {
                    throw new CertificateNotMemorizedException(x509CertificateArr);
                }
                m10975a(x509CertificateArr);
            }
        }
    }

    public synchronized void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        try {
            this.f7299c.checkServerTrusted(x509CertificateArr, str);
        } catch (CertificateException e) {
            try {
                this.f7301e.checkServerTrusted(x509CertificateArr, str);
            } catch (Throwable e2) {
                throw new CertificateMemorizationException(e2);
            } catch (CertificateException e3) {
                if (!this.f7298b.f7296c || this.f7298b.f7294a.exists()) {
                    throw new CertificateNotMemorizedException(x509CertificateArr);
                }
                m10975a(x509CertificateArr);
            }
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
