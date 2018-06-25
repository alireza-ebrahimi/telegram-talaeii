package p033b.p034a.p035a.p036a.p037a.p039b;

/* renamed from: b.a.a.a.a.b.b */
class C1097b {
    /* renamed from: a */
    public final String f3242a;
    /* renamed from: b */
    public final boolean f3243b;

    C1097b(String str, boolean z) {
        this.f3242a = str;
        this.f3243b = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C1097b c1097b = (C1097b) obj;
        if (this.f3243b != c1097b.f3243b) {
            return false;
        }
        if (this.f3242a != null) {
            if (this.f3242a.equals(c1097b.f3242a)) {
                return true;
            }
        } else if (c1097b.f3242a == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (this.f3242a != null ? this.f3242a.hashCode() : 0) * 31;
        if (this.f3243b) {
            i = 1;
        }
        return hashCode + i;
    }
}
