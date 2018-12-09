package p033b.p034a.p035a.p036a.p037a.p039b;

/* renamed from: b.a.a.a.a.b.l */
public enum C1114l {
    DEVELOPER(1),
    USER_SIDELOAD(2),
    TEST_DISTRIBUTION(3),
    APP_STORE(4);
    
    /* renamed from: e */
    private final int f3276e;

    private C1114l(int i) {
        this.f3276e = i;
    }

    /* renamed from: a */
    public static C1114l m6039a(String str) {
        return "io.crash.air".equals(str) ? TEST_DISTRIBUTION : str != null ? APP_STORE : DEVELOPER;
    }

    /* renamed from: a */
    public int m6040a() {
        return this.f3276e;
    }

    public String toString() {
        return Integer.toString(this.f3276e);
    }
}
