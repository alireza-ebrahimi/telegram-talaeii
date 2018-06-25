package com.persianswitch.sdk.base.webservice.trust;

import java.security.cert.CertificateException;

public class CertificateMemorizationException extends CertificateException {
    private static final long serialVersionUID = -1325487564178615414L;

    public CertificateMemorizationException(Throwable t) {
        super(t);
    }
}
