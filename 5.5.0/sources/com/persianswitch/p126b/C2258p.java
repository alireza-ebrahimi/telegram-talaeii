package com.persianswitch.p126b;

/* renamed from: com.persianswitch.b.p */
final class C2258p {
    /* renamed from: a */
    static C2257o f6975a;
    /* renamed from: b */
    static long f6976b;

    private C2258p() {
    }

    /* renamed from: a */
    static C2257o m10403a() {
        synchronized (C2258p.class) {
            if (f6975a != null) {
                C2257o c2257o = f6975a;
                f6975a = c2257o.f6973f;
                c2257o.f6973f = null;
                f6976b -= 8192;
                return c2257o;
            }
            return new C2257o();
        }
    }

    /* renamed from: a */
    static void m10404a(C2257o c2257o) {
        if (c2257o.f6973f != null || c2257o.f6974g != null) {
            throw new IllegalArgumentException();
        } else if (!c2257o.f6971d) {
            synchronized (C2258p.class) {
                if (f6976b + 8192 > 65536) {
                    return;
                }
                f6976b += 8192;
                c2257o.f6973f = f6975a;
                c2257o.f6970c = 0;
                c2257o.f6969b = 0;
                f6975a = c2257o;
            }
        }
    }
}
