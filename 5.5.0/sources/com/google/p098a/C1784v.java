package com.google.p098a;

import com.google.p098a.p100b.C1709a;
import com.google.p098a.p100b.C1741j;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p103c.C1748a;

/* renamed from: com.google.a.v */
final class C1784v<T> extends C1670w<T> {
    /* renamed from: a */
    private final C1666s<T> f5402a;
    /* renamed from: b */
    private final C1665k<T> f5403b;
    /* renamed from: c */
    private final C1768f f5404c;
    /* renamed from: d */
    private final C1748a<T> f5405d;
    /* renamed from: e */
    private final C1668x f5406e;
    /* renamed from: f */
    private C1670w<T> f5407f;

    /* renamed from: com.google.a.v$a */
    private static class C1783a implements C1668x {
        /* renamed from: a */
        private final C1748a<?> f5397a;
        /* renamed from: b */
        private final boolean f5398b;
        /* renamed from: c */
        private final Class<?> f5399c;
        /* renamed from: d */
        private final C1666s<?> f5400d;
        /* renamed from: e */
        private final C1665k<?> f5401e;

        private C1783a(Object obj, C1748a<?> c1748a, boolean z, Class<?> cls) {
            this.f5400d = obj instanceof C1666s ? (C1666s) obj : null;
            this.f5401e = obj instanceof C1665k ? (C1665k) obj : null;
            boolean z2 = (this.f5400d == null && this.f5401e == null) ? false : true;
            C1709a.m8276a(z2);
            this.f5397a = c1748a;
            this.f5398b = z;
            this.f5399c = cls;
        }

        public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
            boolean isAssignableFrom = this.f5397a != null ? this.f5397a.equals(c1748a) || (this.f5398b && this.f5397a.m8360b() == c1748a.m8359a()) : this.f5399c.isAssignableFrom(c1748a.m8359a());
            return isAssignableFrom ? new C1784v(this.f5400d, this.f5401e, c1768f, c1748a, this) : null;
        }
    }

    private C1784v(C1666s<T> c1666s, C1665k<T> c1665k, C1768f c1768f, C1748a<T> c1748a, C1668x c1668x) {
        this.f5402a = c1666s;
        this.f5403b = c1665k;
        this.f5404c = c1768f;
        this.f5405d = c1748a;
        this.f5406e = c1668x;
    }

    /* renamed from: a */
    private C1670w<T> m8442a() {
        C1670w<T> c1670w = this.f5407f;
        if (c1670w != null) {
            return c1670w;
        }
        c1670w = this.f5404c.m8388a(this.f5406e, this.f5405d);
        this.f5407f = c1670w;
        return c1670w;
    }

    /* renamed from: a */
    public static C1668x m8443a(C1748a<?> c1748a, Object obj) {
        return new C1783a(obj, c1748a, false, null);
    }

    public T read(C1678a c1678a) {
        if (this.f5403b == null) {
            return m8442a().read(c1678a);
        }
        C1771l a = C1741j.m8345a(c1678a);
        return a.m8414j() ? null : this.f5403b.mo1252b(a, this.f5405d.m8360b(), this.f5404c.f5364a);
    }

    public void write(C1681c c1681c, T t) {
        if (this.f5402a == null) {
            m8442a().write(c1681c, t);
        } else if (t == null) {
            c1681c.mo1284f();
        } else {
            C1741j.m8347a(this.f5402a.mo1251a(t, this.f5405d.m8360b(), this.f5404c.f5365b), c1681c);
        }
    }
}
