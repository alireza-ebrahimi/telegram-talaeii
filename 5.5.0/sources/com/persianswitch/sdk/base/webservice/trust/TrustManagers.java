package com.persianswitch.sdk.base.webservice.trust;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class TrustManagers {
    /* renamed from: a */
    public static TrustManager[] m10983a(InputStream inputStream, String str) {
        try {
            Certificate generateCertificate = CertificateFactory.getInstance(str).generateCertificate(inputStream);
            KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
            instance.load(null, null);
            instance.setCertificateEntry("ca", generateCertificate);
            TrustManagerFactory instance2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance2.init(instance);
            return instance2.getTrustManagers();
        } finally {
            inputStream.close();
        }
    }
}
