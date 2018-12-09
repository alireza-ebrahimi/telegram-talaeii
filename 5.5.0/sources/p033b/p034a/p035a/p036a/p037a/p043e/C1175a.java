package p033b.p034a.p035a.p036a.p037a.p043e;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;

/* renamed from: b.a.a.a.a.e.a */
final class C1175a {
    /* renamed from: a */
    private static boolean m6223a(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        if (!x509Certificate.getSubjectX500Principal().equals(x509Certificate2.getIssuerX500Principal())) {
            return false;
        }
        try {
            x509Certificate2.verify(x509Certificate.getPublicKey());
            return true;
        } catch (GeneralSecurityException e) {
            return false;
        }
    }

    /* renamed from: a */
    public static X509Certificate[] m6224a(X509Certificate[] x509CertificateArr, C1191i c1191i) {
        int i = 1;
        LinkedList linkedList = new LinkedList();
        int i2 = c1191i.m6310a(x509CertificateArr[0]) ? 1 : 0;
        linkedList.add(x509CertificateArr[0]);
        int i3 = i2;
        i2 = 1;
        while (i2 < x509CertificateArr.length) {
            if (c1191i.m6310a(x509CertificateArr[i2])) {
                i3 = 1;
            }
            if (!C1175a.m6223a(x509CertificateArr[i2], x509CertificateArr[i2 - 1])) {
                break;
            }
            linkedList.add(x509CertificateArr[i2]);
            i2++;
        }
        X509Certificate b = c1191i.m6311b(x509CertificateArr[i2 - 1]);
        if (b != null) {
            linkedList.add(b);
        } else {
            i = i3;
        }
        if (i != 0) {
            return (X509Certificate[]) linkedList.toArray(new X509Certificate[linkedList.size()]);
        }
        throw new CertificateException("Didn't find a trust anchor in chain cleanup!");
    }
}
