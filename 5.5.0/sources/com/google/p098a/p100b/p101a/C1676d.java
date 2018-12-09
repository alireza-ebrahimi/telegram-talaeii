package com.google.p098a.p100b.p101a;

import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.p099a.C1661b;
import com.google.p098a.p100b.C1724c;
import com.google.p098a.p103c.C1748a;

/* renamed from: com.google.a.b.a.d */
public final class C1676d implements C1668x {
    /* renamed from: a */
    private final C1724c f5133a;

    public C1676d(C1724c c1724c) {
        this.f5133a = c1724c;
    }

    /* renamed from: a */
    static C1670w<?> m8098a(C1724c c1724c, C1768f c1768f, C1748a<?> c1748a, C1661b c1661b) {
        Class a = c1661b.m8081a();
        if (C1670w.class.isAssignableFrom(a)) {
            return (C1670w) c1724c.m8315a(C1748a.m8358b(a)).mo1288a();
        }
        if (C1668x.class.isAssignableFrom(a)) {
            return ((C1668x) c1724c.m8315a(C1748a.m8358b(a)).mo1288a()).create(c1768f, c1748a);
        }
        throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
    }

    public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
        C1661b c1661b = (C1661b) c1748a.m8359a().getAnnotation(C1661b.class);
        return c1661b == null ? null : C1676d.m8098a(this.f5133a, c1768f, c1748a, c1661b);
    }
}
