package com.persianswitch.p122a.p123a.p124d;

import com.persianswitch.p122a.p123a.C2187l;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

/* renamed from: com.persianswitch.a.a.d.d */
public final class C2175d implements HostnameVerifier {
    /* renamed from: a */
    public static final C2175d f6613a = new C2175d();

    private C2175d() {
    }

    /* renamed from: a */
    public static List<String> m9842a(X509Certificate x509Certificate) {
        Collection a = C2175d.m9843a(x509Certificate, 7);
        Collection a2 = C2175d.m9843a(x509Certificate, 2);
        List<String> arrayList = new ArrayList(a.size() + a2.size());
        arrayList.addAll(a);
        arrayList.addAll(a2);
        return arrayList;
    }

    /* renamed from: a */
    private static List<String> m9843a(X509Certificate x509Certificate, int i) {
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
    private boolean m9844a(String str, String str2) {
        if (str == null || str.length() == 0 || str.startsWith(".") || str.endsWith("..") || str2 == null || str2.length() == 0 || str2.startsWith(".") || str2.endsWith("..")) {
            return false;
        }
        if (!str.endsWith(".")) {
            str = str + '.';
        }
        if (!str2.endsWith(".")) {
            str2 = str2 + '.';
        }
        String toLowerCase = str2.toLowerCase(Locale.US);
        if (!toLowerCase.contains("*")) {
            return str.equals(toLowerCase);
        }
        if (!toLowerCase.startsWith("*.") || toLowerCase.indexOf(42, 1) != -1 || str.length() < toLowerCase.length() || "*.".equals(toLowerCase)) {
            return false;
        }
        toLowerCase = toLowerCase.substring(1);
        if (!str.endsWith(toLowerCase)) {
            return false;
        }
        int length = str.length() - toLowerCase.length();
        return length <= 0 || str.lastIndexOf(46, length - 1) == -1;
    }

    /* renamed from: b */
    private boolean m9845b(String str, X509Certificate x509Certificate) {
        List a = C2175d.m9843a(x509Certificate, 7);
        int size = a.size();
        for (int i = 0; i < size; i++) {
            if (str.equalsIgnoreCase((String) a.get(i))) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: c */
    private boolean m9846c(String str, X509Certificate x509Certificate) {
        String toLowerCase = str.toLowerCase(Locale.US);
        List a = C2175d.m9843a(x509Certificate, 2);
        int size = a.size();
        int i = 0;
        Object obj = null;
        while (i < size) {
            if (m9844a(toLowerCase, (String) a.get(i))) {
                return true;
            }
            i++;
            int i2 = 1;
        }
        if (obj == null) {
            String a2 = new C2174c(x509Certificate.getSubjectX500Principal()).m9841a("cn");
            if (a2 != null) {
                return m9844a(toLowerCase, a2);
            }
        }
        return false;
    }

    /* renamed from: a */
    public boolean m9847a(String str, X509Certificate x509Certificate) {
        return C2187l.m9909b(str) ? m9845b(str, x509Certificate) : m9846c(str, x509Certificate);
    }

    public boolean verify(String str, SSLSession sSLSession) {
        try {
            return m9847a(str, (X509Certificate) sSLSession.getPeerCertificates()[0]);
        } catch (SSLException e) {
            return false;
        }
    }
}
