package com.persianswitch.sdk.base.webservice.trust;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CertificateNotMemorizedException extends CertificateException {
    /* renamed from: a */
    X509Certificate[] f7291a = null;

    public CertificateNotMemorizedException(X509Certificate[] x509CertificateArr) {
        super("Certificate not found in keystore");
        this.f7291a = x509CertificateArr;
    }
}
