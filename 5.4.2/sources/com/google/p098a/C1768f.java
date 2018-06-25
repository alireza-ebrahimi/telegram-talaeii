package com.google.p098a;

import com.google.p098a.p100b.C1724c;
import com.google.p098a.p100b.C1726d;
import com.google.p098a.p100b.C1737i;
import com.google.p098a.p100b.C1741j;
import com.google.p098a.p100b.p101a.C1671a;
import com.google.p098a.p100b.p101a.C1673b;
import com.google.p098a.p100b.p101a.C1675c;
import com.google.p098a.p100b.p101a.C1676d;
import com.google.p098a.p100b.p101a.C1684g;
import com.google.p098a.p100b.p101a.C1687h;
import com.google.p098a.p100b.p101a.C1691i;
import com.google.p098a.p100b.p101a.C1693j;
import com.google.p098a.p100b.p101a.C1695k;
import com.google.p098a.p100b.p101a.C1708m;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p102d.C1758b;
import com.google.p098a.p103c.C1748a;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.a.f */
public final class C1768f {
    /* renamed from: a */
    final C1760j f5364a;
    /* renamed from: b */
    final C1762r f5365b;
    /* renamed from: c */
    private final ThreadLocal<Map<C1748a<?>, C1767a<?>>> f5366c;
    /* renamed from: d */
    private final Map<C1748a<?>, C1670w<?>> f5367d;
    /* renamed from: e */
    private final List<C1668x> f5368e;
    /* renamed from: f */
    private final C1724c f5369f;
    /* renamed from: g */
    private final boolean f5370g;
    /* renamed from: h */
    private final boolean f5371h;
    /* renamed from: i */
    private final boolean f5372i;
    /* renamed from: j */
    private final boolean f5373j;

    /* renamed from: com.google.a.f$1 */
    class C17611 implements C1760j {
        /* renamed from: a */
        final /* synthetic */ C1768f f5358a;

        C17611(C1768f c1768f) {
            this.f5358a = c1768f;
        }
    }

    /* renamed from: com.google.a.f$2 */
    class C17632 implements C1762r {
        /* renamed from: a */
        final /* synthetic */ C1768f f5359a;

        C17632(C1768f c1768f) {
            this.f5359a = c1768f;
        }
    }

    /* renamed from: com.google.a.f$3 */
    class C17643 extends C1670w<Number> {
        /* renamed from: a */
        final /* synthetic */ C1768f f5360a;

        C17643(C1768f c1768f) {
            this.f5360a = c1768f;
        }

        /* renamed from: a */
        public Double m8373a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return Double.valueOf(c1678a.mo1267k());
            }
            c1678a.mo1266j();
            return null;
        }

        /* renamed from: a */
        public void m8374a(C1681c c1681c, Number number) {
            if (number == null) {
                c1681c.mo1284f();
                return;
            }
            this.f5360a.m8383a(number.doubleValue());
            c1681c.mo1274a(number);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8373a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8374a(c1681c, (Number) obj);
        }
    }

    /* renamed from: com.google.a.f$4 */
    class C17654 extends C1670w<Number> {
        /* renamed from: a */
        final /* synthetic */ C1768f f5361a;

        C17654(C1768f c1768f) {
            this.f5361a = c1768f;
        }

        /* renamed from: a */
        public Float m8375a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return Float.valueOf((float) c1678a.mo1267k());
            }
            c1678a.mo1266j();
            return null;
        }

        /* renamed from: a */
        public void m8376a(C1681c c1681c, Number number) {
            if (number == null) {
                c1681c.mo1284f();
                return;
            }
            this.f5361a.m8383a((double) number.floatValue());
            c1681c.mo1274a(number);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8375a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8376a(c1681c, (Number) obj);
        }
    }

    /* renamed from: com.google.a.f$5 */
    class C17665 extends C1670w<Number> {
        /* renamed from: a */
        final /* synthetic */ C1768f f5362a;

        C17665(C1768f c1768f) {
            this.f5362a = c1768f;
        }

        /* renamed from: a */
        public Number m8377a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return Long.valueOf(c1678a.mo1268l());
            }
            c1678a.mo1266j();
            return null;
        }

        /* renamed from: a */
        public void m8378a(C1681c c1681c, Number number) {
            if (number == null) {
                c1681c.mo1284f();
            } else {
                c1681c.mo1279b(number.toString());
            }
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8377a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8378a(c1681c, (Number) obj);
        }
    }

    /* renamed from: com.google.a.f$a */
    static class C1767a<T> extends C1670w<T> {
        /* renamed from: a */
        private C1670w<T> f5363a;

        C1767a() {
        }

        /* renamed from: a */
        public void m8379a(C1670w<T> c1670w) {
            if (this.f5363a != null) {
                throw new AssertionError();
            }
            this.f5363a = c1670w;
        }

        public T read(C1678a c1678a) {
            if (this.f5363a != null) {
                return this.f5363a.read(c1678a);
            }
            throw new IllegalStateException();
        }

        public void write(C1681c c1681c, T t) {
            if (this.f5363a == null) {
                throw new IllegalStateException();
            }
            this.f5363a.write(c1681c, t);
        }
    }

    public C1768f() {
        this(C1726d.f5293a, C1751d.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, C1779u.DEFAULT, Collections.emptyList());
    }

    C1768f(C1726d c1726d, C1750e c1750e, Map<Type, C1770h<?>> map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, C1779u c1779u, List<C1668x> list) {
        this.f5366c = new ThreadLocal();
        this.f5367d = Collections.synchronizedMap(new HashMap());
        this.f5364a = new C17611(this);
        this.f5365b = new C17632(this);
        this.f5369f = new C1724c(map);
        this.f5370g = z;
        this.f5372i = z3;
        this.f5371h = z4;
        this.f5373j = z5;
        List arrayList = new ArrayList();
        arrayList.add(C1708m.f5230Q);
        arrayList.add(C1687h.f5176a);
        arrayList.add(c1726d);
        arrayList.addAll(list);
        arrayList.add(C1708m.f5255x);
        arrayList.add(C1708m.f5244m);
        arrayList.add(C1708m.f5238g);
        arrayList.add(C1708m.f5240i);
        arrayList.add(C1708m.f5242k);
        arrayList.add(C1708m.m8272a(Long.TYPE, Long.class, m8381a(c1779u)));
        arrayList.add(C1708m.m8272a(Double.TYPE, Double.class, m8382a(z6)));
        arrayList.add(C1708m.m8272a(Float.TYPE, Float.class, m8386b(z6)));
        arrayList.add(C1708m.f5249r);
        arrayList.add(C1708m.f5251t);
        arrayList.add(C1708m.f5257z);
        arrayList.add(C1708m.f5215B);
        arrayList.add(C1708m.m8271a(BigDecimal.class, C1708m.f5253v));
        arrayList.add(C1708m.m8271a(BigInteger.class, C1708m.f5254w));
        arrayList.add(C1708m.f5217D);
        arrayList.add(C1708m.f5219F);
        arrayList.add(C1708m.f5223J);
        arrayList.add(C1708m.f5228O);
        arrayList.add(C1708m.f5221H);
        arrayList.add(C1708m.f5235d);
        arrayList.add(C1675c.f5129a);
        arrayList.add(C1708m.f5226M);
        arrayList.add(C1695k.f5194a);
        arrayList.add(C1693j.f5192a);
        arrayList.add(C1708m.f5224K);
        arrayList.add(C1671a.f5123a);
        arrayList.add(C1708m.f5231R);
        arrayList.add(C1708m.f5233b);
        arrayList.add(new C1673b(this.f5369f));
        arrayList.add(new C1684g(this.f5369f, z2));
        arrayList.add(new C1676d(this.f5369f));
        arrayList.add(new C1691i(this.f5369f, c1750e, c1726d));
        this.f5368e = Collections.unmodifiableList(arrayList);
    }

    /* renamed from: a */
    private C1681c m8380a(Writer writer) {
        if (this.f5372i) {
            writer.write(")]}'\n");
        }
        C1681c c1681c = new C1681c(writer);
        if (this.f5373j) {
            c1681c.m8176c("  ");
        }
        c1681c.m8179d(this.f5370g);
        return c1681c;
    }

    /* renamed from: a */
    private C1670w<Number> m8381a(C1779u c1779u) {
        return c1779u == C1779u.DEFAULT ? C1708m.f5245n : new C17665(this);
    }

    /* renamed from: a */
    private C1670w<Number> m8382a(boolean z) {
        return z ? C1708m.f5247p : new C17643(this);
    }

    /* renamed from: a */
    private void m8383a(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(d + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    /* renamed from: a */
    private static void m8385a(Object obj, C1678a c1678a) {
        if (obj != null) {
            try {
                if (c1678a.mo1262f() != C1758b.END_DOCUMENT) {
                    throw new C1774m("JSON document was not fully consumed.");
                }
            } catch (Throwable e) {
                throw new C1778t(e);
            } catch (Throwable e2) {
                throw new C1774m(e2);
            }
        }
    }

    /* renamed from: b */
    private C1670w<Number> m8386b(boolean z) {
        return z ? C1708m.f5246o : new C17654(this);
    }

    /* renamed from: a */
    public <T> C1670w<T> m8387a(C1748a<T> c1748a) {
        C1670w<T> c1670w = (C1670w) this.f5367d.get(c1748a);
        if (c1670w == null) {
            Map map;
            Map map2 = (Map) this.f5366c.get();
            Object obj = null;
            if (map2 == null) {
                HashMap hashMap = new HashMap();
                this.f5366c.set(hashMap);
                map = hashMap;
                obj = 1;
            } else {
                map = map2;
            }
            C1767a c1767a = (C1767a) map.get(c1748a);
            if (c1767a == null) {
                try {
                    C1767a c1767a2 = new C1767a();
                    map.put(c1748a, c1767a2);
                    for (C1668x create : this.f5368e) {
                        c1670w = create.create(this, c1748a);
                        if (c1670w != null) {
                            c1767a2.m8379a(c1670w);
                            this.f5367d.put(c1748a, c1670w);
                            map.remove(c1748a);
                            if (obj != null) {
                                this.f5366c.remove();
                            }
                        }
                    }
                    throw new IllegalArgumentException("GSON cannot handle " + c1748a);
                } catch (Throwable th) {
                    map.remove(c1748a);
                    if (obj != null) {
                        this.f5366c.remove();
                    }
                }
            }
        }
        return c1670w;
    }

    /* renamed from: a */
    public <T> C1670w<T> m8388a(C1668x c1668x, C1748a<T> c1748a) {
        Object obj = null;
        for (C1668x c1668x2 : this.f5368e) {
            if (obj != null) {
                C1670w<T> create = c1668x2.create(this, c1748a);
                if (create != null) {
                    return create;
                }
            } else if (c1668x2 == c1668x) {
                obj = 1;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + c1748a);
    }

    /* renamed from: a */
    public <T> C1670w<T> m8389a(Class<T> cls) {
        return m8387a(C1748a.m8358b(cls));
    }

    /* renamed from: a */
    public <T> T m8390a(C1678a c1678a, Type type) {
        boolean z = true;
        boolean p = c1678a.m8138p();
        c1678a.m8124a(true);
        try {
            c1678a.mo1262f();
            z = false;
            T read = m8387a(C1748a.m8356a(type)).read(c1678a);
            c1678a.m8124a(p);
            return read;
        } catch (Throwable e) {
            if (z) {
                c1678a.m8124a(p);
                return null;
            }
            throw new C1778t(e);
        } catch (Throwable e2) {
            throw new C1778t(e2);
        } catch (Throwable e22) {
            throw new C1778t(e22);
        } catch (Throwable th) {
            c1678a.m8124a(p);
        }
    }

    /* renamed from: a */
    public <T> T m8391a(Reader reader, Type type) {
        C1678a c1678a = new C1678a(reader);
        Object a = m8390a(c1678a, type);
        C1768f.m8385a(a, c1678a);
        return a;
    }

    /* renamed from: a */
    public <T> T m8392a(String str, Class<T> cls) {
        return C1737i.m8342a((Class) cls).cast(m8393a(str, (Type) cls));
    }

    /* renamed from: a */
    public <T> T m8393a(String str, Type type) {
        return str == null ? null : m8391a(new StringReader(str), type);
    }

    /* renamed from: a */
    public String m8394a(C1771l c1771l) {
        Appendable stringWriter = new StringWriter();
        m8398a(c1771l, stringWriter);
        return stringWriter.toString();
    }

    /* renamed from: a */
    public String m8395a(Object obj) {
        return obj == null ? m8394a(C1775n.f5390a) : m8396a(obj, obj.getClass());
    }

    /* renamed from: a */
    public String m8396a(Object obj, Type type) {
        Appendable stringWriter = new StringWriter();
        m8400a(obj, type, stringWriter);
        return stringWriter.toString();
    }

    /* renamed from: a */
    public void m8397a(C1771l c1771l, C1681c c1681c) {
        boolean g = c1681c.m8182g();
        c1681c.m8174b(true);
        boolean h = c1681c.m8183h();
        c1681c.m8177c(this.f5371h);
        boolean i = c1681c.m8184i();
        c1681c.m8179d(this.f5370g);
        try {
            C1741j.m8347a(c1771l, c1681c);
            c1681c.m8174b(g);
            c1681c.m8177c(h);
            c1681c.m8179d(i);
        } catch (Throwable e) {
            throw new C1774m(e);
        } catch (Throwable th) {
            c1681c.m8174b(g);
            c1681c.m8177c(h);
            c1681c.m8179d(i);
        }
    }

    /* renamed from: a */
    public void m8398a(C1771l c1771l, Appendable appendable) {
        try {
            m8397a(c1771l, m8380a(C1741j.m8346a(appendable)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /* renamed from: a */
    public void m8399a(Object obj, Type type, C1681c c1681c) {
        C1670w a = m8387a(C1748a.m8356a(type));
        boolean g = c1681c.m8182g();
        c1681c.m8174b(true);
        boolean h = c1681c.m8183h();
        c1681c.m8177c(this.f5371h);
        boolean i = c1681c.m8184i();
        c1681c.m8179d(this.f5370g);
        try {
            a.write(c1681c, obj);
            c1681c.m8174b(g);
            c1681c.m8177c(h);
            c1681c.m8179d(i);
        } catch (Throwable e) {
            throw new C1774m(e);
        } catch (Throwable th) {
            c1681c.m8174b(g);
            c1681c.m8177c(h);
            c1681c.m8179d(i);
        }
    }

    /* renamed from: a */
    public void m8400a(Object obj, Type type, Appendable appendable) {
        try {
            m8399a(obj, type, m8380a(C1741j.m8346a(appendable)));
        } catch (Throwable e) {
            throw new C1774m(e);
        }
    }

    public String toString() {
        return "{serializeNulls:" + this.f5370g + "factories:" + this.f5368e + ",instanceCreators:" + this.f5369f + "}";
    }
}
