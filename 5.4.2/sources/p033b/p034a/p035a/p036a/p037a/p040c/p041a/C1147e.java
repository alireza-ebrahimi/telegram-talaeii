package p033b.p034a.p035a.p036a.p037a.p040c.p041a;

/* renamed from: b.a.a.a.a.c.a.e */
public class C1147e {
    /* renamed from: a */
    private final int f3350a;
    /* renamed from: b */
    private final C1143a f3351b;
    /* renamed from: c */
    private final C1144d f3352c;

    public C1147e(int i, C1143a c1143a, C1144d c1144d) {
        this.f3350a = i;
        this.f3351b = c1143a;
        this.f3352c = c1144d;
    }

    public C1147e(C1143a c1143a, C1144d c1144d) {
        this(0, c1143a, c1144d);
    }

    /* renamed from: a */
    public long m6111a() {
        return this.f3351b.mo1025a(this.f3350a);
    }

    /* renamed from: b */
    public C1147e m6112b() {
        return new C1147e(this.f3350a + 1, this.f3351b, this.f3352c);
    }

    /* renamed from: c */
    public C1147e m6113c() {
        return new C1147e(this.f3351b, this.f3352c);
    }
}
