package com.google.p098a.p100b.p101a;

import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.p100b.C1736g;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p103c.C1748a;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.a.b.a.h */
public final class C1687h extends C1670w<Object> {
    /* renamed from: a */
    public static final C1668x f5176a = new C16851();
    /* renamed from: b */
    private final C1768f f5177b;

    /* renamed from: com.google.a.b.a.h$1 */
    static class C16851 implements C1668x {
        C16851() {
        }

        public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
            return c1748a.m8359a() == Object.class ? new C1687h(c1768f) : null;
        }
    }

    private C1687h(C1768f c1768f) {
        this.f5177b = c1768f;
    }

    public Object read(C1678a c1678a) {
        switch (c1678a.mo1262f()) {
            case BEGIN_ARRAY:
                List arrayList = new ArrayList();
                c1678a.mo1256a();
                while (c1678a.mo1261e()) {
                    arrayList.add(read(c1678a));
                }
                c1678a.mo1257b();
                return arrayList;
            case BEGIN_OBJECT:
                Map c1736g = new C1736g();
                c1678a.mo1258c();
                while (c1678a.mo1261e()) {
                    c1736g.put(c1678a.mo1263g(), read(c1678a));
                }
                c1678a.mo1260d();
                return c1736g;
            case STRING:
                return c1678a.mo1264h();
            case NUMBER:
                return Double.valueOf(c1678a.mo1267k());
            case BOOLEAN:
                return Boolean.valueOf(c1678a.mo1265i());
            case NULL:
                c1678a.mo1266j();
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    public void write(C1681c c1681c, Object obj) {
        if (obj == null) {
            c1681c.mo1284f();
            return;
        }
        C1670w a = this.f5177b.m8389a(obj.getClass());
        if (a instanceof C1687h) {
            c1681c.mo1282d();
            c1681c.mo1283e();
            return;
        }
        a.write(c1681c, obj);
    }
}
