package com.persianswitch.p122a.p123a;

import com.persianswitch.p122a.C2207k;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSocket;

/* renamed from: com.persianswitch.a.a.b */
public final class C2164b {
    /* renamed from: a */
    private final List<C2207k> f6560a;
    /* renamed from: b */
    private int f6561b = 0;
    /* renamed from: c */
    private boolean f6562c;
    /* renamed from: d */
    private boolean f6563d;

    public C2164b(List<C2207k> list) {
        this.f6560a = list;
    }

    /* renamed from: b */
    private boolean m9781b(SSLSocket sSLSocket) {
        for (int i = this.f6561b; i < this.f6560a.size(); i++) {
            if (((C2207k) this.f6560a.get(i)).m9983a(sSLSocket)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    public C2207k m9782a(SSLSocket sSLSocket) {
        C2207k c2207k;
        int i = this.f6561b;
        int size = this.f6560a.size();
        for (int i2 = i; i2 < size; i2++) {
            c2207k = (C2207k) this.f6560a.get(i2);
            if (c2207k.m9983a(sSLSocket)) {
                this.f6561b = i2 + 1;
                break;
            }
        }
        c2207k = null;
        if (c2207k == null) {
            throw new UnknownServiceException("Unable to find acceptable protocols. isFallback=" + this.f6563d + ", modes=" + this.f6560a + ", supported protocols=" + Arrays.toString(sSLSocket.getEnabledProtocols()));
        }
        this.f6562c = m9781b(sSLSocket);
        C2179d.f6617a.mo3163a(c2207k, sSLSocket, this.f6563d);
        return c2207k;
    }

    /* renamed from: a */
    public boolean m9783a(IOException iOException) {
        this.f6563d = true;
        return (!this.f6562c || (iOException instanceof ProtocolException) || (iOException instanceof InterruptedIOException)) ? false : (((iOException instanceof SSLHandshakeException) && (iOException.getCause() instanceof CertificateException)) || (iOException instanceof SSLPeerUnverifiedException)) ? false : (iOException instanceof SSLHandshakeException) || (iOException instanceof SSLProtocolException);
    }
}
