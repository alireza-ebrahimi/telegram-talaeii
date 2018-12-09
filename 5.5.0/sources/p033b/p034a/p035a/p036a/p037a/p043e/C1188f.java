package p033b.p034a.p035a.p036a.p037a.p043e;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/* renamed from: b.a.a.a.a.e.f */
public final class C1188f {
    /* renamed from: a */
    public static final SSLSocketFactory m6298a(C1189g c1189g) {
        SSLContext instance = SSLContext.getInstance("TLS");
        C1190h c1190h = new C1190h(new C1191i(c1189g.mo1170a(), c1189g.mo1171b()), c1189g);
        instance.init(null, new TrustManager[]{c1190h}, null);
        return instance.getSocketFactory();
    }
}
