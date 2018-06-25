package android.support.v4.p022f;

/* renamed from: android.support.v4.f.i */
public class C0477i<F, S> {
    /* renamed from: a */
    public final F f1264a;
    /* renamed from: b */
    public final S f1265b;

    public C0477i(F f, S s) {
        this.f1264a = f;
        this.f1265b = s;
    }

    /* renamed from: a */
    public static <A, B> C0477i<A, B> m2029a(A a, B b) {
        return new C0477i(a, b);
    }

    /* renamed from: b */
    private static boolean m2030b(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C0477i)) {
            return false;
        }
        C0477i c0477i = (C0477i) obj;
        return C0477i.m2030b(c0477i.f1264a, this.f1264a) && C0477i.m2030b(c0477i.f1265b, this.f1265b);
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.f1264a == null ? 0 : this.f1264a.hashCode();
        if (this.f1265b != null) {
            i = this.f1265b.hashCode();
        }
        return hashCode ^ i;
    }

    public String toString() {
        return "Pair{" + String.valueOf(this.f1264a) + " " + String.valueOf(this.f1265b) + "}";
    }
}
