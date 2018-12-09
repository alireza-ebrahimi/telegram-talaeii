package com.google.p098a.p103c;

import com.google.p098a.p100b.C1709a;
import com.google.p098a.p100b.C1713b;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* renamed from: com.google.a.c.a */
public class C1748a<T> {
    /* renamed from: a */
    final Class<? super T> f5337a;
    /* renamed from: b */
    final Type f5338b;
    /* renamed from: c */
    final int f5339c;

    protected C1748a() {
        this.f5338b = C1748a.m8357a(getClass());
        this.f5337a = C1713b.m8294e(this.f5338b);
        this.f5339c = this.f5338b.hashCode();
    }

    C1748a(Type type) {
        this.f5338b = C1713b.m8293d((Type) C1709a.m8275a((Object) type));
        this.f5337a = C1713b.m8294e(this.f5338b);
        this.f5339c = this.f5338b.hashCode();
    }

    /* renamed from: a */
    public static C1748a<?> m8356a(Type type) {
        return new C1748a(type);
    }

    /* renamed from: a */
    static Type m8357a(Class<?> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof Class)) {
            return C1713b.m8293d(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        }
        throw new RuntimeException("Missing type parameter.");
    }

    /* renamed from: b */
    public static <T> C1748a<T> m8358b(Class<T> cls) {
        return new C1748a(cls);
    }

    /* renamed from: a */
    public final Class<? super T> m8359a() {
        return this.f5337a;
    }

    /* renamed from: b */
    public final Type m8360b() {
        return this.f5338b;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C1748a) && C1713b.m8287a(this.f5338b, ((C1748a) obj).f5338b);
    }

    public final int hashCode() {
        return this.f5339c;
    }

    public final String toString() {
        return C1713b.m8295f(this.f5338b);
    }
}
