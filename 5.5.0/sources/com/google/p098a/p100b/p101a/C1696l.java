package com.google.p098a.p100b.p101a;

import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.p100b.p101a.C1691i.C1690a;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p103c.C1748a;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* renamed from: com.google.a.b.a.l */
final class C1696l<T> extends C1670w<T> {
    /* renamed from: a */
    private final C1768f f5196a;
    /* renamed from: b */
    private final C1670w<T> f5197b;
    /* renamed from: c */
    private final Type f5198c;

    C1696l(C1768f c1768f, C1670w<T> c1670w, Type type) {
        this.f5196a = c1768f;
        this.f5197b = c1670w;
        this.f5198c = type;
    }

    /* renamed from: a */
    private Type m8217a(Type type, Object obj) {
        return obj != null ? (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? obj.getClass() : type : type;
    }

    public T read(C1678a c1678a) {
        return this.f5197b.read(c1678a);
    }

    public void write(C1681c c1681c, T t) {
        C1670w c1670w = this.f5197b;
        Type a = m8217a(this.f5198c, t);
        if (a != this.f5198c) {
            c1670w = this.f5196a.m8387a(C1748a.m8356a(a));
            if ((c1670w instanceof C1690a) && !(this.f5197b instanceof C1690a)) {
                c1670w = this.f5197b;
            }
        }
        c1670w.write(c1681c, t);
    }
}
