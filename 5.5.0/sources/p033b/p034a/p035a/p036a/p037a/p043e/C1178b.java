package p033b.p034a.p035a.p036a.p037a.p043e;

import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import p033b.p034a.p035a.p036a.C1224l;
import p033b.p034a.p035a.p036a.C1225b;

/* renamed from: b.a.a.a.a.e.b */
public class C1178b implements C1177e {
    /* renamed from: a */
    private final C1224l f3405a;
    /* renamed from: b */
    private C1189g f3406b;
    /* renamed from: c */
    private SSLSocketFactory f3407c;
    /* renamed from: d */
    private boolean f3408d;

    public C1178b() {
        this(new C1225b());
    }

    public C1178b(C1224l c1224l) {
        this.f3405a = c1224l;
    }

    /* renamed from: a */
    private synchronized void m6227a() {
        this.f3408d = false;
        this.f3407c = null;
    }

    /* renamed from: a */
    private boolean m6228a(String str) {
        return str != null && str.toLowerCase(Locale.US).startsWith("https");
    }

    /* renamed from: b */
    private synchronized SSLSocketFactory m6229b() {
        if (this.f3407c == null && !this.f3408d) {
            this.f3407c = m6230c();
        }
        return this.f3407c;
    }

    /* renamed from: c */
    private synchronized SSLSocketFactory m6230c() {
        SSLSocketFactory a;
        this.f3408d = true;
        try {
            a = C1188f.m6298a(this.f3406b);
            this.f3405a.mo1062a("Fabric", "Custom SSL pinning enabled");
        } catch (Throwable e) {
            this.f3405a.mo1070e("Fabric", "Exception while validating pinned certs", e);
            a = null;
        }
        return a;
    }

    /* renamed from: a */
    public C1187d mo1044a(C1179c c1179c, String str, Map<String, String> map) {
        C1187d a;
        switch (c1179c) {
            case GET:
                a = C1187d.m6245a((CharSequence) str, (Map) map, true);
                break;
            case POST:
                a = C1187d.m6250b((CharSequence) str, (Map) map, true);
                break;
            case PUT:
                a = C1187d.m6253d((CharSequence) str);
                break;
            case DELETE:
                a = C1187d.m6254e((CharSequence) str);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method!");
        }
        if (m6228a(str) && this.f3406b != null) {
            SSLSocketFactory b = m6229b();
            if (b != null) {
                ((HttpsURLConnection) a.m6272a()).setSSLSocketFactory(b);
            }
        }
        return a;
    }

    /* renamed from: a */
    public void mo1045a(C1189g c1189g) {
        if (this.f3406b != c1189g) {
            this.f3406b = c1189g;
            m6227a();
        }
    }
}
