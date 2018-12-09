package com.persianswitch.p122a.p123a;

import com.persianswitch.p122a.C2226v;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import javax.net.ssl.SSLSocket;

/* renamed from: com.persianswitch.a.a.g */
class C2183g extends C2127j {
    /* renamed from: a */
    private final Method f6623a;
    /* renamed from: b */
    private final Method f6624b;
    /* renamed from: c */
    private final Method f6625c;
    /* renamed from: d */
    private final Class<?> f6626d;
    /* renamed from: e */
    private final Class<?> f6627e;

    /* renamed from: com.persianswitch.a.a.g$a */
    private static class C2182a implements InvocationHandler {
        /* renamed from: a */
        private final List<String> f6620a;
        /* renamed from: b */
        private boolean f6621b;
        /* renamed from: c */
        private String f6622c;

        public C2182a(List<String> list) {
            this.f6620a = list;
        }

        public Object invoke(Object obj, Method method, Object[] objArr) {
            String name = method.getName();
            Class returnType = method.getReturnType();
            if (objArr == null) {
                objArr = C2187l.f6635b;
            }
            if (name.equals("supports") && Boolean.TYPE == returnType) {
                return Boolean.valueOf(true);
            }
            if (name.equals("unsupported") && Void.TYPE == returnType) {
                this.f6621b = true;
                return null;
            } else if (name.equals("protocols") && objArr.length == 0) {
                return this.f6620a;
            } else {
                if ((name.equals("selectProtocol") || name.equals("select")) && String.class == returnType && objArr.length == 1 && (objArr[0] instanceof List)) {
                    List list = (List) objArr[0];
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (this.f6620a.contains(list.get(i))) {
                            name = (String) list.get(i);
                            this.f6622c = name;
                            return name;
                        }
                    }
                    name = (String) this.f6620a.get(0);
                    this.f6622c = name;
                    return name;
                } else if ((!name.equals("protocolSelected") && !name.equals("selected")) || objArr.length != 1) {
                    return method.invoke(this, objArr);
                } else {
                    this.f6622c = (String) objArr[0];
                    return null;
                }
            }
        }
    }

    public C2183g(Method method, Method method2, Method method3, Class<?> cls, Class<?> cls2) {
        this.f6623a = method;
        this.f6624b = method2;
        this.f6625c = method3;
        this.f6626d = cls;
        this.f6627e = cls2;
    }

    /* renamed from: b */
    public static C2127j m9872b() {
        try {
            String str = "org.eclipse.jetty.alpn.ALPN";
            Class cls = Class.forName(str);
            Class cls2 = Class.forName(str + "$Provider");
            Class cls3 = Class.forName(str + "$ClientProvider");
            Class cls4 = Class.forName(str + "$ServerProvider");
            return new C2183g(cls.getMethod("put", new Class[]{SSLSocket.class, cls2}), cls.getMethod("get", new Class[]{SSLSocket.class}), cls.getMethod("remove", new Class[]{SSLSocket.class}), cls3, cls4);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e2) {
        }
        return null;
    }

    /* renamed from: a */
    public String mo3131a(SSLSocket sSLSocket) {
        try {
            C2182a c2182a = (C2182a) Proxy.getInvocationHandler(this.f6624b.invoke(null, new Object[]{sSLSocket}));
            if (c2182a.f6621b || c2182a.f6622c != null) {
                return c2182a.f6621b ? null : c2182a.f6622c;
            }
            C2127j.m9615c().mo3132a(4, "ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?", null);
            return null;
        } catch (InvocationTargetException e) {
            throw new AssertionError();
        } catch (IllegalAccessException e2) {
            throw new AssertionError();
        }
    }

    /* renamed from: a */
    public void mo3134a(SSLSocket sSLSocket, String str, List<C2226v> list) {
        List a = C2127j.m9612a((List) list);
        Object newProxyInstance;
        try {
            newProxyInstance = Proxy.newProxyInstance(C2127j.class.getClassLoader(), new Class[]{this.f6626d, this.f6627e}, new C2182a(a));
            this.f6623a.invoke(null, new Object[]{sSLSocket, newProxyInstance});
        } catch (InvocationTargetException e) {
            newProxyInstance = e;
            throw new AssertionError(newProxyInstance);
        } catch (IllegalAccessException e2) {
            newProxyInstance = e2;
            throw new AssertionError(newProxyInstance);
        }
    }

    /* renamed from: b */
    public void mo3155b(SSLSocket sSLSocket) {
        try {
            this.f6625c.invoke(null, new Object[]{sSLSocket});
        } catch (IllegalAccessException e) {
            throw new AssertionError();
        } catch (InvocationTargetException e2) {
            throw new AssertionError();
        }
    }
}
