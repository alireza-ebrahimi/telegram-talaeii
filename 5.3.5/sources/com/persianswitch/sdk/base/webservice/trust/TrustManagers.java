package com.persianswitch.sdk.base.webservice.trust;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class TrustManagers {
    public static TrustManager[] useTrustStore(InputStream in, char[] password, String format) throws GeneralSecurityException, IOException, NullPointerException {
        if (format == null) {
            format = KeyStore.getDefaultType();
        }
        KeyStore store = KeyStore.getInstance(format);
        try {
            store.load(in, password);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(store);
            return tmf.getTrustManagers();
        } finally {
            in.close();
        }
    }

    public static TrustManager[] allowCA(InputStream in, String certType) throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException {
        try {
            Certificate caCert = CertificateFactory.getInstance(certType).generateCertificate(in);
            KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
            store.load(null, null);
            store.setCertificateEntry("ca", caCert);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(store);
            return tmf.getTrustManagers();
        } finally {
            in.close();
        }
    }
}
