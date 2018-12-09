package com.persianswitch.sdk.base.webservice;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public class APMBHostNameVerifier implements HostnameVerifier {
    /* renamed from: a */
    private HostnameVerifier f7135a;

    public APMBHostNameVerifier(HostnameVerifier hostnameVerifier) {
        this.f7135a = hostnameVerifier;
    }

    /* renamed from: a */
    private List<String> m10810a(X509Certificate x509Certificate, int i) {
        List<String> arrayList = new ArrayList();
        try {
            Collection<List> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
            if (subjectAlternativeNames == null) {
                return Collections.emptyList();
            }
            for (List list : subjectAlternativeNames) {
                if (list != null && list.size() >= 2) {
                    Integer num = (Integer) list.get(0);
                    if (num != null && num.intValue() == i) {
                        String str = (String) list.get(1);
                        if (str != null) {
                            arrayList.add(str);
                        }
                    }
                }
            }
            return arrayList;
        } catch (CertificateParsingException e) {
            return Collections.emptyList();
        }
    }

    /* renamed from: a */
    public static boolean m10811a(String str) {
        return Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$").matcher(str).find();
    }

    /* renamed from: a */
    public boolean m10812a(String str, X509Certificate x509Certificate) {
        for (String equals : m10810a(x509Certificate, 7)) {
            if (str.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    public boolean verify(String str, SSLSession sSLSession) {
        try {
            String trim = str.trim();
            return !m10811a(trim) ? this.f7135a.verify(trim, sSLSession) : m10812a(trim, (X509Certificate) sSLSession.getPeerCertificates()[0]);
        } catch (SSLException e) {
            return false;
        }
    }
}
