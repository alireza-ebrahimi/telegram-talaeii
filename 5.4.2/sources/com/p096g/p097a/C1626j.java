package com.p096g.p097a;

/* renamed from: com.g.a.j */
public enum C1626j {
    NO_CACHE(1),
    NO_STORE(2);
    
    /* renamed from: c */
    final int f4970c;

    private C1626j(int i) {
        this.f4970c = i;
    }

    /* renamed from: a */
    static boolean m8004a(int i) {
        return (NO_CACHE.f4970c & i) == 0;
    }
}
