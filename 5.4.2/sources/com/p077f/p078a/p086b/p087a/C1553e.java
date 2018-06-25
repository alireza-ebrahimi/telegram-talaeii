package com.p077f.p078a.p086b.p087a;

/* renamed from: com.f.a.b.a.e */
public class C1553e {
    /* renamed from: a */
    private final int f4709a;
    /* renamed from: b */
    private final int f4710b;

    public C1553e(int i, int i2) {
        this.f4709a = i;
        this.f4710b = i2;
    }

    public C1553e(int i, int i2, int i3) {
        if (i3 % 180 == 0) {
            this.f4709a = i;
            this.f4710b = i2;
            return;
        }
        this.f4709a = i2;
        this.f4710b = i;
    }

    /* renamed from: a */
    public int m7673a() {
        return this.f4709a;
    }

    /* renamed from: a */
    public C1553e m7674a(float f) {
        return new C1553e((int) (((float) this.f4709a) * f), (int) (((float) this.f4710b) * f));
    }

    /* renamed from: a */
    public C1553e m7675a(int i) {
        return new C1553e(this.f4709a / i, this.f4710b / i);
    }

    /* renamed from: b */
    public int m7676b() {
        return this.f4710b;
    }

    public String toString() {
        return this.f4709a + "x" + this.f4710b;
    }
}
