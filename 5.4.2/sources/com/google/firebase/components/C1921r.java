package com.google.firebase.components;

import com.google.firebase.p106a.C1810c;
import com.google.firebase.p108b.C1896a;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.google.firebase.components.r */
final class C1921r implements C1903b {
    /* renamed from: a */
    private final Set<Class<?>> f5636a;
    /* renamed from: b */
    private final Set<Class<?>> f5637b;
    /* renamed from: c */
    private final Set<Class<?>> f5638c;
    /* renamed from: d */
    private final C1903b f5639d;

    /* renamed from: com.google.firebase.components.r$a */
    static class C1920a implements C1810c {
        /* renamed from: a */
        private final Set<Class<?>> f5634a;
        /* renamed from: b */
        private final C1810c f5635b;

        public C1920a(Set<Class<?>> set, C1810c c1810c) {
            this.f5634a = set;
            this.f5635b = c1810c;
        }
    }

    C1921r(C1902a<?> c1902a, C1903b c1903b) {
        Set hashSet = new HashSet();
        Set hashSet2 = new HashSet();
        for (C1905f c1905f : c1902a.m8710b()) {
            if (c1905f.m8721c()) {
                hashSet.add(c1905f.m8719a());
            } else {
                hashSet2.add(c1905f.m8719a());
            }
        }
        if (!c1902a.m8712d().isEmpty()) {
            hashSet.add(C1810c.class);
        }
        this.f5636a = Collections.unmodifiableSet(hashSet);
        this.f5637b = Collections.unmodifiableSet(hashSet2);
        this.f5638c = c1902a.m8712d();
        this.f5639d = c1903b;
    }

    /* renamed from: a */
    public final <T> T mo3042a(Class<T> cls) {
        if (this.f5636a.contains(cls)) {
            T a = this.f5639d.mo3042a(cls);
            return !cls.equals(C1810c.class) ? a : new C1920a(this.f5638c, (C1810c) a);
        } else {
            throw new IllegalArgumentException(String.format("Requesting %s is not allowed.", new Object[]{cls}));
        }
    }

    /* renamed from: b */
    public final <T> C1896a<T> mo3043b(Class<T> cls) {
        if (this.f5637b.contains(cls)) {
            return this.f5639d.mo3043b(cls);
        }
        throw new IllegalArgumentException(String.format("Requesting Provider<%s> is not allowed.", new Object[]{cls}));
    }
}
