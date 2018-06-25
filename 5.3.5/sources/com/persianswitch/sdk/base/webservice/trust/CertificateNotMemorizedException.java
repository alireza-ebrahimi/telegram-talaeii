package com.persianswitch.sdk.base.webservice.trust;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CertificateNotMemorizedException extends CertificateException {
    private static final long serialVersionUID = -4093879253540605439L;
    X509Certificate[] chain = null;

    public CertificateNotMemorizedException(X509Certificate[] chain) {
        super("Certificate not found in keystore");
        this.chain = chain;
    }

    public X509Certificate[] getCertificateChain() {
        return this.chain;
    }
}
