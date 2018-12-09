package com.p072e.p074b;

/* renamed from: com.e.b.c */
public abstract class C1494c<T, V> {
    /* renamed from: a */
    private final String f4551a;
    /* renamed from: b */
    private final Class<V> f4552b;

    public C1494c(Class<V> cls, String str) {
        this.f4551a = str;
        this.f4552b = cls;
    }

    /* renamed from: a */
    public abstract V mo1202a(T t);

    /* renamed from: a */
    public String m7427a() {
        return this.f4551a;
    }

    /* renamed from: a */
    public void mo1201a(T t, V v) {
        throw new UnsupportedOperationException("Property " + m7427a() + " is read-only");
    }
}
