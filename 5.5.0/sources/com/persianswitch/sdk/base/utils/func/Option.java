package com.persianswitch.sdk.base.utils.func;

public class Option<T> implements Functional {
    /* renamed from: a */
    T f7103a;

    public Option(T t) {
        this.f7103a = t;
    }

    /* renamed from: a */
    public static <E> E m10775a(E e, E e2) {
        return new Option(e).m10776a(e2);
    }

    /* renamed from: a */
    public T m10776a(T t) {
        return this.f7103a == null ? t : this.f7103a;
    }
}
