package com.persianswitch.p122a.p123a;

import com.persianswitch.p122a.C2225u;
import com.persianswitch.p122a.C2226v;
import com.persianswitch.p122a.p123a.p124d.C2071b;
import com.persianswitch.p122a.p123a.p124d.C2173a;
import com.persianswitch.p122a.p123a.p124d.C2176e;
import com.persianswitch.p126b.C2244c;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509TrustManager;

/* renamed from: com.persianswitch.a.a.j */
public class C2127j {
    /* renamed from: a */
    private static final C2127j f6448a = C2127j.m9613b();
    /* renamed from: b */
    private static final Logger f6449b = Logger.getLogger(C2225u.class.getName());

    /* renamed from: a */
    public static List<String> m9612a(List<C2226v> list) {
        List<String> arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            C2226v c2226v = (C2226v) list.get(i);
            if (c2226v != C2226v.HTTP_1_0) {
                arrayList.add(c2226v.toString());
            }
        }
        return arrayList;
    }

    /* renamed from: b */
    private static C2127j m9613b() {
        C2127j b = C2128a.m9623b();
        if (b != null) {
            return b;
        }
        b = C2181f.m9867b();
        if (b != null) {
            return b;
        }
        b = C2183g.m9872b();
        return b == null ? new C2127j() : b;
    }

    /* renamed from: b */
    static byte[] m9614b(List<C2226v> list) {
        C2244c c2244c = new C2244c();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            C2226v c2226v = (C2226v) list.get(i);
            if (c2226v != C2226v.HTTP_1_0) {
                c2244c.m10275b(c2226v.toString().length());
                c2244c.m10267a(c2226v.toString());
            }
        }
        return c2244c.mo3197q();
    }

    /* renamed from: c */
    public static C2127j m9615c() {
        return f6448a;
    }

    /* renamed from: a */
    public C2071b mo3130a(X509TrustManager x509TrustManager) {
        return new C2173a(C2176e.m9848a(x509TrustManager));
    }

    /* renamed from: a */
    public String mo3131a(SSLSocket sSLSocket) {
        return null;
    }

    /* renamed from: a */
    public void mo3132a(int i, String str, Throwable th) {
        f6449b.log(i == 5 ? Level.WARNING : Level.INFO, str, th);
    }

    /* renamed from: a */
    public void mo3133a(Socket socket, InetSocketAddress inetSocketAddress, int i) {
        try {
            socket.connect(inetSocketAddress, i);
        } catch (Throwable e) {
            new ConnectException(e.getMessage()).initCause(e);
            throw e;
        }
    }

    /* renamed from: a */
    public void mo3134a(SSLSocket sSLSocket, String str, List<C2226v> list) {
    }

    /* renamed from: a */
    public boolean mo3135a() {
        return true;
    }

    /* renamed from: b */
    public void mo3155b(SSLSocket sSLSocket) {
    }
}
