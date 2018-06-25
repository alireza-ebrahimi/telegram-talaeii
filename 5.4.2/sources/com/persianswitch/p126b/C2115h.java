package com.persianswitch.p126b;

/* renamed from: com.persianswitch.b.h */
public abstract class C2115h implements C2096s {
    /* renamed from: a */
    private final C2096s f6424a;

    public C2115h(C2096s c2096s) {
        if (c2096s == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.f6424a = c2096s;
    }

    /* renamed from: a */
    public long mo3105a(C2244c c2244c, long j) {
        return this.f6424a.mo3105a(c2244c, j);
    }

    /* renamed from: a */
    public C2098t mo3106a() {
        return this.f6424a.mo3106a();
    }

    public void close() {
        this.f6424a.close();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.f6424a.toString() + ")";
    }
}
