package com.google.p098a.p100b.p101a;

import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.p100b.C1713b;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p102d.C1758b;
import com.google.p098a.p103c.C1748a;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.a.b.a.a */
public final class C1671a<E> extends C1670w<Object> {
    /* renamed from: a */
    public static final C1668x f5123a = new C16691();
    /* renamed from: b */
    private final Class<E> f5124b;
    /* renamed from: c */
    private final C1670w<E> f5125c;

    /* renamed from: com.google.a.b.a.a$1 */
    static class C16691 implements C1668x {
        C16691() {
        }

        public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
            Type b = c1748a.m8360b();
            if (!(b instanceof GenericArrayType) && (!(b instanceof Class) || !((Class) b).isArray())) {
                return null;
            }
            b = C1713b.m8296g(b);
            return new C1671a(c1768f, c1768f.m8387a(C1748a.m8356a(b)), C1713b.m8294e(b));
        }
    }

    public C1671a(C1768f c1768f, C1670w<E> c1670w, Class<E> cls) {
        this.f5125c = new C1696l(c1768f, c1670w, cls);
        this.f5124b = cls;
    }

    public Object read(C1678a c1678a) {
        if (c1678a.mo1262f() == C1758b.NULL) {
            c1678a.mo1266j();
            return null;
        }
        List arrayList = new ArrayList();
        c1678a.mo1256a();
        while (c1678a.mo1261e()) {
            arrayList.add(this.f5125c.read(c1678a));
        }
        c1678a.mo1257b();
        Object newInstance = Array.newInstance(this.f5124b, arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            Array.set(newInstance, i, arrayList.get(i));
        }
        return newInstance;
    }

    public void write(C1681c c1681c, Object obj) {
        if (obj == null) {
            c1681c.mo1284f();
            return;
        }
        c1681c.mo1278b();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.f5125c.write(c1681c, Array.get(obj, i));
        }
        c1681c.mo1280c();
    }
}
