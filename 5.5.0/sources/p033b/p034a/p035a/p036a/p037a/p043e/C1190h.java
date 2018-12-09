package p033b.p034a.p035a.p036a.p037a.p043e;

import com.google.android.gms.common.util.AndroidUtilsLight;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: b.a.a.a.a.e.h */
class C1190h implements X509TrustManager {
    /* renamed from: a */
    private static final X509Certificate[] f3433a = new X509Certificate[0];
    /* renamed from: b */
    private final TrustManager[] f3434b;
    /* renamed from: c */
    private final C1191i f3435c;
    /* renamed from: d */
    private final long f3436d;
    /* renamed from: e */
    private final List<byte[]> f3437e = new LinkedList();
    /* renamed from: f */
    private final Set<X509Certificate> f3438f = Collections.synchronizedSet(new HashSet());

    public C1190h(C1191i c1191i, C1189g c1189g) {
        this.f3434b = m6307a(c1191i);
        this.f3435c = c1191i;
        this.f3436d = c1189g.mo1173d();
        for (String a : c1189g.mo1172c()) {
            this.f3437e.add(m6306a(a));
        }
    }

    /* renamed from: a */
    private void m6303a(X509Certificate[] x509CertificateArr) {
        if (this.f3436d == -1 || System.currentTimeMillis() - this.f3436d <= 15552000000L) {
            X509Certificate[] a = C1175a.m6224a(x509CertificateArr, this.f3435c);
            int length = a.length;
            int i = 0;
            while (i < length) {
                if (!m6305a(a[i])) {
                    i++;
                } else {
                    return;
                }
            }
            throw new CertificateException("No valid pins found in chain!");
        }
        C1230c.m6414h().mo1067d("Fabric", "Certificate pins are stale, (" + (System.currentTimeMillis() - this.f3436d) + " millis vs " + 15552000000L + " millis) falling back to system trust.");
    }

    /* renamed from: a */
    private void m6304a(X509Certificate[] x509CertificateArr, String str) {
        for (TrustManager trustManager : this.f3434b) {
            ((X509TrustManager) trustManager).checkServerTrusted(x509CertificateArr, str);
        }
    }

    /* renamed from: a */
    private boolean m6305a(X509Certificate x509Certificate) {
        try {
            byte[] digest = MessageDigest.getInstance(AndroidUtilsLight.DIGEST_ALGORITHM_SHA1).digest(x509Certificate.getPublicKey().getEncoded());
            for (byte[] equals : this.f3437e) {
                if (Arrays.equals(equals, digest)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable e) {
            throw new CertificateException(e);
        }
    }

    /* renamed from: a */
    private byte[] m6306a(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    /* renamed from: a */
    private TrustManager[] m6307a(C1191i c1191i) {
        try {
            TrustManagerFactory instance = TrustManagerFactory.getInstance("X509");
            instance.init(c1191i.f3439a);
            return instance.getTrustManagers();
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        } catch (KeyStoreException e2) {
            throw new AssertionError(e2);
        }
    }

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        throw new CertificateException("Client certificates not supported!");
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        if (!this.f3438f.contains(x509CertificateArr[0])) {
            m6304a(x509CertificateArr, str);
            m6303a(x509CertificateArr);
            this.f3438f.add(x509CertificateArr[0]);
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return f3433a;
    }
}
