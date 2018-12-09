package com.google.firebase.components;

import com.google.firebase.p108b.C1896a;

/* renamed from: com.google.firebase.components.p */
final class C1918p<T> implements C1896a<T> {
    /* renamed from: a */
    private static final Object f5629a = new Object();
    /* renamed from: b */
    private volatile Object f5630b = f5629a;
    /* renamed from: c */
    private volatile C1896a<T> f5631c;

    C1918p(C1817d<T> c1817d, C1903b c1903b) {
        this.f5631c = new C1919q(c1817d, c1903b);
    }

    /* renamed from: a */
    public final T mo3044a() {
        T t = this.f5630b;
        if (t == f5629a) {
            synchronized (this) {
                t = this.f5630b;
                if (t == f5629a) {
                    t = this.f5631c.mo3044a();
                    this.f5630b = t;
                    this.f5631c = null;
                }
            }
        }
        return t;
    }
}
