package com.p077f.p078a.p086b.p087a;

/* renamed from: com.f.a.b.a.b */
public class C1550b {
    /* renamed from: a */
    private final C1549a f4700a;
    /* renamed from: b */
    private final Throwable f4701b;

    /* renamed from: com.f.a.b.a.b$a */
    public enum C1549a {
        IO_ERROR,
        DECODING_ERROR,
        NETWORK_DENIED,
        OUT_OF_MEMORY,
        UNKNOWN
    }

    public C1550b(C1549a c1549a, Throwable th) {
        this.f4700a = c1549a;
        this.f4701b = th;
    }

    /* renamed from: a */
    public Throwable m7672a() {
        return this.f4701b;
    }
}
