package com.google.firebase.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* renamed from: com.google.firebase.components.m */
final class C1915m {

    /* renamed from: com.google.firebase.components.m$a */
    static class C1914a {
        /* renamed from: a */
        private final C1902a<?> f5621a;
        /* renamed from: b */
        private final Set<C1914a> f5622b = new HashSet();
        /* renamed from: c */
        private final Set<C1914a> f5623c = new HashSet();

        C1914a(C1902a<?> c1902a) {
            this.f5621a = c1902a;
        }

        /* renamed from: a */
        final Set<C1914a> m8733a() {
            return this.f5622b;
        }

        /* renamed from: a */
        final void m8734a(C1914a c1914a) {
            this.f5622b.add(c1914a);
        }

        /* renamed from: b */
        final C1902a<?> m8735b() {
            return this.f5621a;
        }

        /* renamed from: b */
        final void m8736b(C1914a c1914a) {
            this.f5623c.add(c1914a);
        }

        /* renamed from: c */
        final void m8737c(C1914a c1914a) {
            this.f5623c.remove(c1914a);
        }

        /* renamed from: c */
        final boolean m8738c() {
            return this.f5623c.isEmpty();
        }

        /* renamed from: d */
        final boolean m8739d() {
            return this.f5622b.isEmpty();
        }
    }

    /* renamed from: a */
    static List<C1902a<?>> m8740a(List<C1902a<?>> list) {
        Map hashMap = new HashMap(list.size());
        for (C1902a c1902a : list) {
            C1914a c1914a = new C1914a(c1902a);
            for (Class put : c1902a.m8709a()) {
                if (hashMap.put(put, c1914a) != null) {
                    throw new IllegalArgumentException(String.format("Multiple components provide %s.", new Object[]{(Class) r4.next()}));
                }
            }
        }
        for (C1914a c1914a2 : hashMap.values()) {
            for (C1905f c1905f : c1914a2.m8735b().m8710b()) {
                C1914a c1914a3;
                if (c1905f.m8721c()) {
                    c1914a3 = (C1914a) hashMap.get(c1905f.m8719a());
                    if (c1914a3 != null) {
                        C1914a c1914a22;
                        c1914a22.m8734a(c1914a3);
                        c1914a3.m8736b(c1914a22);
                    }
                }
            }
        }
        Set<C1914a> hashSet = new HashSet(hashMap.values());
        Set a = C1915m.m8741a((Set) hashSet);
        List<C1902a<?>> arrayList = new ArrayList();
        while (!a.isEmpty()) {
            c1914a22 = (C1914a) a.iterator().next();
            a.remove(c1914a22);
            arrayList.add(c1914a22.m8735b());
            for (C1914a c1914a32 : c1914a22.m8733a()) {
                c1914a32.m8737c(c1914a22);
                if (c1914a32.m8738c()) {
                    a.add(c1914a32);
                }
            }
        }
        if (arrayList.size() == list.size()) {
            Collections.reverse(arrayList);
            return arrayList;
        }
        List arrayList2 = new ArrayList();
        for (C1914a c1914a222 : hashSet) {
            if (!(c1914a222.m8738c() || c1914a222.m8739d())) {
                arrayList2.add(c1914a222.m8735b());
            }
        }
        throw new C1907g(arrayList2);
    }

    /* renamed from: a */
    private static Set<C1914a> m8741a(Set<C1914a> set) {
        Set<C1914a> hashSet = new HashSet();
        for (C1914a c1914a : set) {
            if (c1914a.m8738c()) {
                hashSet.add(c1914a);
            }
        }
        return hashSet;
    }
}
