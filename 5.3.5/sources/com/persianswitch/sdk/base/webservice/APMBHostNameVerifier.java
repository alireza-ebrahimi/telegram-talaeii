package com.persianswitch.sdk.base.webservice;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public class APMBHostNameVerifier implements HostnameVerifier {
    private static final int ALT_IPA_NAME = 7;
    private HostnameVerifier defaultHostNameVerifier;

    public APMBHostNameVerifier(HostnameVerifier defaultHostNameVerifier) {
        this.defaultHostNameVerifier = defaultHostNameVerifier;
    }

    public boolean verify(String host, SSLSession sslSession) {
        try {
            host = host.trim();
            if (isIP(host)) {
                return verify(host, (X509Certificate) sslSession.getPeerCertificates()[0]);
            }
            return this.defaultHostNameVerifier.verify(host, sslSession);
        } catch (SSLException e) {
            return false;
        }
    }

    public boolean verify(String host, X509Certificate certificate) {
        for (String s : getSubjectAltNames(certificate, 7)) {
            if (host.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getSubjectAltNames(X509Certificate certificate, int type) {
        List<String> result = new ArrayList();
        try {
            Collection<?> subjectAltNames = certificate.getSubjectAlternativeNames();
            if (subjectAltNames == null) {
                return Collections.emptyList();
            }
            Iterator it = subjectAltNames.iterator();
            while (it.hasNext()) {
                List<?> entry = (List) it.next();
                if (entry != null && entry.size() >= 2) {
                    Integer altNameType = (Integer) entry.get(0);
                    if (altNameType != null && altNameType.intValue() == type) {
                        String altName = (String) entry.get(1);
                        if (altName != null) {
                            result.add(altName);
                        }
                    }
                }
            }
            return result;
        } catch (CertificateParsingException e) {
            return Collections.emptyList();
        }
    }

    public static boolean isIP(String hostname) {
        return Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$").matcher(hostname).find();
    }
}
