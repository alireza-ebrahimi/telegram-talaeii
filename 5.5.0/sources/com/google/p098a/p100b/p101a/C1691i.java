package com.google.p098a.p100b.p101a;

import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1750e;
import com.google.p098a.C1768f;
import com.google.p098a.C1778t;
import com.google.p098a.p099a.C1661b;
import com.google.p098a.p099a.C1662c;
import com.google.p098a.p100b.C1713b;
import com.google.p098a.p100b.C1714h;
import com.google.p098a.p100b.C1724c;
import com.google.p098a.p100b.C1726d;
import com.google.p098a.p100b.C1737i;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p102d.C1758b;
import com.google.p098a.p103c.C1748a;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/* renamed from: com.google.a.b.a.i */
public final class C1691i implements C1668x {
    /* renamed from: a */
    private final C1724c f5189a;
    /* renamed from: b */
    private final C1750e f5190b;
    /* renamed from: c */
    private final C1726d f5191c;

    /* renamed from: com.google.a.b.a.i$b */
    static abstract class C1688b {
        /* renamed from: g */
        final String f5178g;
        /* renamed from: h */
        final boolean f5179h;
        /* renamed from: i */
        final boolean f5180i;

        protected C1688b(String str, boolean z, boolean z2) {
            this.f5178g = str;
            this.f5179h = z;
            this.f5180i = z2;
        }

        /* renamed from: a */
        abstract void mo1286a(C1678a c1678a, Object obj);

        /* renamed from: a */
        abstract void mo1287a(C1681c c1681c, Object obj);
    }

    /* renamed from: com.google.a.b.a.i$a */
    public static final class C1690a<T> extends C1670w<T> {
        /* renamed from: a */
        private final C1714h<T> f5187a;
        /* renamed from: b */
        private final Map<String, C1688b> f5188b;

        private C1690a(C1714h<T> c1714h, Map<String, C1688b> map) {
            this.f5187a = c1714h;
            this.f5188b = map;
        }

        public T read(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            T a = this.f5187a.mo1288a();
            try {
                c1678a.mo1258c();
                while (c1678a.mo1261e()) {
                    C1688b c1688b = (C1688b) this.f5188b.get(c1678a.mo1263g());
                    if (c1688b == null || !c1688b.f5180i) {
                        c1678a.mo1270n();
                    } else {
                        c1688b.mo1286a(c1678a, (Object) a);
                    }
                }
                c1678a.mo1260d();
                return a;
            } catch (Throwable e) {
                throw new C1778t(e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        public void write(C1681c c1681c, T t) {
            if (t == null) {
                c1681c.mo1284f();
                return;
            }
            c1681c.mo1282d();
            try {
                for (C1688b c1688b : this.f5188b.values()) {
                    if (c1688b.f5179h) {
                        c1681c.mo1275a(c1688b.f5178g);
                        c1688b.mo1287a(c1681c, (Object) t);
                    }
                }
                c1681c.mo1283e();
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        }
    }

    public C1691i(C1724c c1724c, C1750e c1750e, C1726d c1726d) {
        this.f5189a = c1724c;
        this.f5190b = c1750e;
        this.f5191c = c1726d;
    }

    /* renamed from: a */
    private C1688b m8207a(C1768f c1768f, Field field, String str, C1748a<?> c1748a, boolean z, boolean z2) {
        final boolean a = C1737i.m8344a(c1748a.m8359a());
        final C1768f c1768f2 = c1768f;
        final Field field2 = field;
        final C1748a<?> c1748a2 = c1748a;
        return new C1688b(this, str, z, z2) {
            /* renamed from: a */
            final C1670w<?> f5181a = this.f5186f.m8209a(c1768f2, field2, c1748a2);
            /* renamed from: f */
            final /* synthetic */ C1691i f5186f;

            /* renamed from: a */
            void mo1286a(C1678a c1678a, Object obj) {
                Object read = this.f5181a.read(c1678a);
                if (read != null || !a) {
                    field2.set(obj, read);
                }
            }

            /* renamed from: a */
            void mo1287a(C1681c c1681c, Object obj) {
                new C1696l(c1768f2, this.f5181a, c1748a2.m8360b()).write(c1681c, field2.get(obj));
            }
        };
    }

    /* renamed from: a */
    private C1670w<?> m8209a(C1768f c1768f, Field field, C1748a<?> c1748a) {
        C1661b c1661b = (C1661b) field.getAnnotation(C1661b.class);
        if (c1661b != null) {
            C1670w<?> a = C1676d.m8098a(this.f5189a, c1768f, c1748a, c1661b);
            if (a != null) {
                return a;
            }
        }
        return c1768f.m8387a((C1748a) c1748a);
    }

    /* renamed from: a */
    private String m8210a(Field field) {
        C1662c c1662c = (C1662c) field.getAnnotation(C1662c.class);
        return c1662c == null ? this.f5190b.mo1290a(field) : c1662c.m8082a();
    }

    /* renamed from: a */
    private Map<String, C1688b> m8211a(C1768f c1768f, C1748a<?> c1748a, Class<?> cls) {
        Map<String, C1688b> linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        Type b = c1748a.m8360b();
        Class a;
        while (a != Object.class) {
            for (Field field : a.getDeclaredFields()) {
                boolean a2 = m8212a(field, true);
                boolean a3 = m8212a(field, false);
                if (a2 || a3) {
                    field.setAccessible(true);
                    C1688b a4 = m8207a(c1768f, field, m8210a(field), C1748a.m8356a(C1713b.m8284a(r14.m8360b(), a, field.getGenericType())), a2, a3);
                    a4 = (C1688b) linkedHashMap.put(a4.f5178g, a4);
                    if (a4 != null) {
                        throw new IllegalArgumentException(b + " declares multiple JSON fields named " + a4.f5178g);
                    }
                }
            }
            C1748a a5 = C1748a.m8356a(C1713b.m8284a(a5.m8360b(), a, a.getGenericSuperclass()));
            a = a5.m8359a();
        }
        return linkedHashMap;
    }

    /* renamed from: a */
    public boolean m8212a(Field field, boolean z) {
        return (this.f5191c.m8324a(field.getType(), z) || this.f5191c.m8325a(field, z)) ? false : true;
    }

    public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
        Class a = c1748a.m8359a();
        return !Object.class.isAssignableFrom(a) ? null : new C1690a(this.f5189a.m8315a((C1748a) c1748a), m8211a(c1768f, (C1748a) c1748a, a));
    }
}
