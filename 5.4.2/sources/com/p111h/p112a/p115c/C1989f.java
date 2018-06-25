package com.p111h.p112a.p115c;

import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* renamed from: com.h.a.c.f */
public class C1989f extends SSLSocketFactory {
    /* renamed from: a */
    private final SSLSocketFactory f5865a = HttpsURLConnection.getDefaultSSLSocketFactory();
    /* renamed from: b */
    private final boolean f5866b;
    /* renamed from: c */
    private final boolean f5867c;

    public C1989f() {
        boolean z = false;
        String[] protocols;
        try {
            protocols = SSLContext.getDefault().getSupportedSSLParameters().getProtocols();
        } catch (NoSuchAlgorithmException e) {
            protocols = new String[0];
        }
        boolean z2 = false;
        for (String str : r0) {
            if (str.equals("TLSv1.1")) {
                z2 = true;
            } else if (str.equals("TLSv1.2")) {
                z = true;
            }
        }
        this.f5866b = z2;
        this.f5867c = z;
    }

    /* renamed from: a */
    private Socket m9002a(Socket socket) {
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            Set hashSet = new HashSet(Arrays.asList(sSLSocket.getEnabledProtocols()));
            if (this.f5866b) {
                hashSet.add("TLSv1.1");
            }
            if (this.f5867c) {
                hashSet.add("TLSv1.2");
            }
            sSLSocket.setEnabledProtocols((String[]) hashSet.toArray(new String[0]));
        }
        return socket;
    }

    public Socket createSocket(String str, int i) {
        return m9002a(this.f5865a.createSocket(str, i));
    }

    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) {
        return m9002a(this.f5865a.createSocket(str, i, inetAddress, i2));
    }

    public Socket createSocket(InetAddress inetAddress, int i) {
        return m9002a(this.f5865a.createSocket(inetAddress, i));
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) {
        return m9002a(this.f5865a.createSocket(inetAddress, i, inetAddress2, i2));
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) {
        return m9002a(this.f5865a.createSocket(socket, str, i, z));
    }

    public String[] getDefaultCipherSuites() {
        return this.f5865a.getDefaultCipherSuites();
    }

    public String[] getSupportedCipherSuites() {
        return this.f5865a.getSupportedCipherSuites();
    }
}
