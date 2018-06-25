package com.google.p098a.p100b;

import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1747b;
import com.google.p098a.C1749c;
import com.google.p098a.C1768f;
import com.google.p098a.p099a.C1660a;
import com.google.p098a.p099a.C1663d;
import com.google.p098a.p099a.C1664e;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p103c.C1748a;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/* renamed from: com.google.a.b.d */
public final class C1726d implements C1668x, Cloneable {
    /* renamed from: a */
    public static final C1726d f5293a = new C1726d();
    /* renamed from: b */
    private double f5294b = -1.0d;
    /* renamed from: c */
    private int f5295c = 136;
    /* renamed from: d */
    private boolean f5296d = true;
    /* renamed from: e */
    private boolean f5297e;
    /* renamed from: f */
    private List<C1747b> f5298f = Collections.emptyList();
    /* renamed from: g */
    private List<C1747b> f5299g = Collections.emptyList();

    /* renamed from: a */
    private boolean m8317a(C1663d c1663d) {
        return c1663d == null || c1663d.m8083a() <= this.f5294b;
    }

    /* renamed from: a */
    private boolean m8318a(C1663d c1663d, C1664e c1664e) {
        return m8317a(c1663d) && m8319a(c1664e);
    }

    /* renamed from: a */
    private boolean m8319a(C1664e c1664e) {
        return c1664e == null || c1664e.m8084a() > this.f5294b;
    }

    /* renamed from: a */
    private boolean m8320a(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    /* renamed from: b */
    private boolean m8321b(Class<?> cls) {
        return cls.isMemberClass() && !m8322c(cls);
    }

    /* renamed from: c */
    private boolean m8322c(Class<?> cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    /* renamed from: a */
    protected C1726d m8323a() {
        try {
            return (C1726d) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /* renamed from: a */
    public boolean m8324a(Class<?> cls, boolean z) {
        if (this.f5294b != -1.0d && !m8318a((C1663d) cls.getAnnotation(C1663d.class), (C1664e) cls.getAnnotation(C1664e.class))) {
            return true;
        }
        if (!this.f5296d && m8321b(cls)) {
            return true;
        }
        if (m8320a((Class) cls)) {
            return true;
        }
        for (C1747b a : z ? this.f5298f : this.f5299g) {
            if (a.m8355a((Class) cls)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    public boolean m8325a(Field field, boolean z) {
        if ((this.f5295c & field.getModifiers()) != 0) {
            return true;
        }
        if (this.f5294b != -1.0d && !m8318a((C1663d) field.getAnnotation(C1663d.class), (C1664e) field.getAnnotation(C1664e.class))) {
            return true;
        }
        if (field.isSynthetic()) {
            return true;
        }
        if (this.f5297e) {
            C1660a c1660a = (C1660a) field.getAnnotation(C1660a.class);
            if (c1660a == null || (z ? !c1660a.m8079a() : !c1660a.m8080b())) {
                return true;
            }
        }
        if (!this.f5296d && m8321b(field.getType())) {
            return true;
        }
        if (m8320a(field.getType())) {
            return true;
        }
        List<C1747b> list = z ? this.f5298f : this.f5299g;
        if (!list.isEmpty()) {
            C1749c c1749c = new C1749c(field);
            for (C1747b a : list) {
                if (a.m8354a(c1749c)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected /* synthetic */ Object clone() {
        return m8323a();
    }

    public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
        Class a = c1748a.m8359a();
        final boolean a2 = m8324a(a, true);
        final boolean a3 = m8324a(a, false);
        if (!a2 && !a3) {
            return null;
        }
        final C1768f c1768f2 = c1768f;
        final C1748a<T> c1748a2 = c1748a;
        return new C1670w<T>(this) {
            /* renamed from: e */
            final /* synthetic */ C1726d f5291e;
            /* renamed from: f */
            private C1670w<T> f5292f;

            /* renamed from: a */
            private C1670w<T> m8316a() {
                C1670w<T> c1670w = this.f5292f;
                if (c1670w != null) {
                    return c1670w;
                }
                c1670w = c1768f2.m8388a(this.f5291e, c1748a2);
                this.f5292f = c1670w;
                return c1670w;
            }

            public T read(C1678a c1678a) {
                if (!a3) {
                    return m8316a().read(c1678a);
                }
                c1678a.mo1270n();
                return null;
            }

            public void write(C1681c c1681c, T t) {
                if (a2) {
                    c1681c.mo1284f();
                } else {
                    m8316a().write(c1681c, t);
                }
            }
        };
    }
}
