package com.persianswitch.p122a.p123a;

import android.util.Log;
import com.google.android.gms.wearable.WearableStatusCodes;
import com.persianswitch.p122a.C2226v;
import com.persianswitch.p122a.p123a.p124d.C2071b;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509TrustManager;

/* renamed from: com.persianswitch.a.a.a */
class C2128a extends C2127j {
    /* renamed from: a */
    private final Class<?> f6450a;
    /* renamed from: b */
    private final C2184i<Socket> f6451b;
    /* renamed from: c */
    private final C2184i<Socket> f6452c;
    /* renamed from: d */
    private final C2184i<Socket> f6453d;
    /* renamed from: e */
    private final C2184i<Socket> f6454e;

    /* renamed from: com.persianswitch.a.a.a$a */
    static final class C2072a extends C2071b {
        /* renamed from: a */
        private final Object f6239a;
        /* renamed from: b */
        private final Method f6240b;

        C2072a(Object obj, Method method) {
            this.f6239a = obj;
            this.f6240b = method;
        }

        /* renamed from: a */
        public List<Certificate> mo3088a(List<Certificate> list, String str) {
            try {
                X509Certificate[] x509CertificateArr = (X509Certificate[]) list.toArray(new X509Certificate[list.size()]);
                return (List) this.f6240b.invoke(this.f6239a, new Object[]{x509CertificateArr, "RSA", str});
            } catch (Throwable e) {
                SSLPeerUnverifiedException sSLPeerUnverifiedException = new SSLPeerUnverifiedException(e.getMessage());
                sSLPeerUnverifiedException.initCause(e);
                throw sSLPeerUnverifiedException;
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }
    }

    public C2128a(Class<?> cls, C2184i<Socket> c2184i, C2184i<Socket> c2184i2, C2184i<Socket> c2184i3, C2184i<Socket> c2184i4) {
        this.f6450a = cls;
        this.f6451b = c2184i;
        this.f6452c = c2184i2;
        this.f6453d = c2184i3;
        this.f6454e = c2184i4;
    }

    /* renamed from: b */
    public static C2127j m9623b() {
        Class cls;
        C2184i c2184i;
        C2184i c2184i2;
        C2184i c2184i3;
        try {
            cls = Class.forName("com.android.org.conscrypt.SSLParametersImpl");
            try {
                C2184i c2184i4 = new C2184i(null, "setUseSessionTickets", Boolean.TYPE);
                C2184i c2184i5 = new C2184i(null, "setHostname", String.class);
                try {
                    Class.forName("android.net.Network");
                    c2184i = new C2184i(byte[].class, "getAlpnSelectedProtocol", new Class[0]);
                    try {
                        c2184i2 = new C2184i(null, "setAlpnProtocols", byte[].class);
                        c2184i3 = c2184i;
                    } catch (ClassNotFoundException e) {
                        c2184i2 = null;
                        c2184i3 = c2184i;
                        return new C2128a(cls, c2184i4, c2184i5, c2184i3, c2184i2);
                    }
                } catch (ClassNotFoundException e2) {
                    c2184i = null;
                    c2184i2 = null;
                    c2184i3 = c2184i;
                    return new C2128a(cls, c2184i4, c2184i5, c2184i3, c2184i2);
                }
                return new C2128a(cls, c2184i4, c2184i5, c2184i3, c2184i2);
            } catch (ClassNotFoundException e3) {
                return null;
            }
        } catch (ClassNotFoundException e4) {
            cls = Class.forName("org.apache.harmony.xnet.provider.jsse.SSLParametersImpl");
        }
    }

    /* renamed from: a */
    public C2071b mo3130a(X509TrustManager x509TrustManager) {
        try {
            Class cls = Class.forName("android.net.http.X509TrustManagerExtensions");
            return new C2072a(cls.getConstructor(new Class[]{X509TrustManager.class}).newInstance(new Object[]{x509TrustManager}), cls.getMethod("checkServerTrusted", new Class[]{X509Certificate[].class, String.class, String.class}));
        } catch (Exception e) {
            return super.mo3130a(x509TrustManager);
        }
    }

    /* renamed from: a */
    public String mo3131a(SSLSocket sSLSocket) {
        if (this.f6453d == null || !this.f6453d.m9879a((Object) sSLSocket)) {
            return null;
        }
        byte[] bArr = (byte[]) this.f6453d.m9882d(sSLSocket, new Object[0]);
        return bArr != null ? new String(bArr, C2187l.f6636c) : null;
    }

    /* renamed from: a */
    public void mo3132a(int i, String str, Throwable th) {
        int i2 = i == 5 ? 5 : 3;
        if (th != null) {
            str = str + '\n' + Log.getStackTraceString(th);
        }
        int i3 = 0;
        int length = str.length();
        while (i3 < length) {
            int min;
            int indexOf = str.indexOf(10, i3);
            if (indexOf == -1) {
                indexOf = length;
            }
            while (true) {
                min = Math.min(indexOf, i3 + WearableStatusCodes.TARGET_NODE_NOT_CONNECTED);
                Log.println(i2, "OkHttp", str.substring(i3, min));
                if (min >= indexOf) {
                    break;
                }
                i3 = min;
            }
            i3 = min + 1;
        }
    }

    /* renamed from: a */
    public void mo3133a(Socket socket, InetSocketAddress inetSocketAddress, int i) {
        IOException connectException;
        try {
            socket.connect(inetSocketAddress, i);
        } catch (AssertionError e) {
            if (C2187l.m9902a(e)) {
                ConnectException connectException2 = new ConnectException(e.getMessage());
                connectException2.initCause(e);
                throw connectException2;
            }
            throw e;
        } catch (Throwable e2) {
            connectException = new ConnectException("Exception in connect");
            connectException.initCause(e2);
            throw connectException;
        } catch (Throwable e22) {
            connectException = new ConnectException(e22.getMessage());
            connectException.initCause(e22);
            throw connectException;
        }
    }

    /* renamed from: a */
    public void mo3134a(SSLSocket sSLSocket, String str, List<C2226v> list) {
        if (str != null) {
            this.f6451b.m9880b(sSLSocket, Boolean.valueOf(true));
            this.f6452c.m9880b(sSLSocket, str);
        }
        if (this.f6454e != null && this.f6454e.m9879a((Object) sSLSocket)) {
            this.f6454e.m9882d(sSLSocket, C2127j.m9614b((List) list));
        }
    }

    /* renamed from: a */
    public boolean mo3135a() {
        try {
            Class cls = Class.forName("android.security.NetworkSecurityPolicy");
            return ((Boolean) cls.getMethod("isCleartextTrafficPermitted", new Class[0]).invoke(cls.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]), new Object[0])).booleanValue();
        } catch (ClassNotFoundException e) {
            return super.mo3135a();
        } catch (NoSuchMethodException e2) {
            throw new AssertionError();
        } catch (IllegalAccessException e3) {
            throw new AssertionError();
        } catch (IllegalArgumentException e4) {
            throw new AssertionError();
        } catch (InvocationTargetException e5) {
            throw new AssertionError();
        }
    }
}
