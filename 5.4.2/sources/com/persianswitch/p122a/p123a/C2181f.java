package com.persianswitch.p122a.p123a;

import com.persianswitch.p122a.C2226v;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;

/* renamed from: com.persianswitch.a.a.f */
final class C2181f extends C2127j {
    /* renamed from: a */
    final Method f6618a;
    /* renamed from: b */
    final Method f6619b;

    public C2181f(Method method, Method method2) {
        this.f6618a = method;
        this.f6619b = method2;
    }

    /* renamed from: b */
    public static C2181f m9867b() {
        try {
            return new C2181f(SSLParameters.class.getMethod("setApplicationProtocols", new Class[]{String[].class}), SSLSocket.class.getMethod("getApplicationProtocol", new Class[0]));
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /* renamed from: a */
    public String mo3131a(SSLSocket sSLSocket) {
        try {
            String str = (String) this.f6619b.invoke(sSLSocket, new Object[0]);
            return (str == null || str.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? null : str;
        } catch (IllegalAccessException e) {
            throw new AssertionError();
        } catch (InvocationTargetException e2) {
            throw new AssertionError();
        }
    }

    /* renamed from: a */
    public void mo3134a(SSLSocket sSLSocket, String str, List<C2226v> list) {
        try {
            SSLParameters sSLParameters = sSLSocket.getSSLParameters();
            List a = C2127j.m9612a((List) list);
            this.f6618a.invoke(sSLParameters, new Object[]{a.toArray(new String[a.size()])});
            sSLSocket.setSSLParameters(sSLParameters);
        } catch (IllegalAccessException e) {
            throw new AssertionError();
        } catch (InvocationTargetException e2) {
            throw new AssertionError();
        }
    }
}
