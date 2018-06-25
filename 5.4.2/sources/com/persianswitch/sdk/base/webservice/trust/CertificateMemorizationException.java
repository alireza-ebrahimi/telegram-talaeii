package com.persianswitch.sdk.base.webservice.trust;

import java.security.cert.CertificateException;

public class CertificateMemorizationException extends CertificateException {
    public CertificateMemorizationException(Throwable th) {
        super(th);
    }
}
