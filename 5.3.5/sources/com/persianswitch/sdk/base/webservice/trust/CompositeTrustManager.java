package com.persianswitch.sdk.base.webservice.trust;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.net.ssl.X509TrustManager;

public class CompositeTrustManager implements X509TrustManager {
    private ArrayList<X509TrustManager> managers = new ArrayList();
    private boolean matchAll;

    protected CompositeTrustManager(X509TrustManager[] mgrs, boolean matchAll) {
        if (mgrs != null) {
            addAll(mgrs);
        }
        setMatchAll(matchAll);
    }

    public static CompositeTrustManager matchAll(X509TrustManager... managers) {
        return new CompositeTrustManager(managers, true);
    }

    public static CompositeTrustManager matchAny(X509TrustManager... managers) {
        return new CompositeTrustManager(managers, false);
    }

    public void add(X509TrustManager mgr) {
        this.managers.add(mgr);
    }

    public void addAll(X509TrustManager[] mgrs) {
        for (X509TrustManager mgr : mgrs) {
            this.managers.add(mgr);
        }
    }

    public boolean isMatchAll() {
        return this.matchAll;
    }

    public void setMatchAll(boolean matchAll) {
        if (this.managers.size() > 1) {
            throw new IllegalStateException("Cannot change mode once 2+ managers added");
        }
        this.matchAll = matchAll;
    }

    public int size() {
        return this.managers.size();
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        CertificateException first = null;
        Iterator it = this.managers.iterator();
        while (it.hasNext()) {
            try {
                ((X509TrustManager) it.next()).checkClientTrusted(chain, authType);
                if (!this.matchAll) {
                    return;
                }
            } catch (CertificateException e) {
                if (this.matchAll) {
                    throw e;
                }
                first = e;
            }
        }
        if (first != null) {
            throw first;
        }
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        CertificateException first = null;
        Iterator it = this.managers.iterator();
        while (it.hasNext()) {
            try {
                ((X509TrustManager) it.next()).checkClientTrusted(chain, authType);
                if (!this.matchAll) {
                    return;
                }
            } catch (CertificateException e) {
                if (this.matchAll) {
                    throw e;
                }
                first = e;
            }
        }
        if (first != null) {
            throw first;
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        HashSet<X509Certificate> issuers = new HashSet();
        Iterator it = this.managers.iterator();
        while (it.hasNext()) {
            for (X509Certificate cert : ((X509TrustManager) it.next()).getAcceptedIssuers()) {
                issuers.add(cert);
            }
        }
        return (X509Certificate[]) issuers.toArray(new X509Certificate[issuers.size()]);
    }
}
