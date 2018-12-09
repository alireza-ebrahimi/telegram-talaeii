package android.support.v4.view;

import android.os.Build.VERSION;

public class be {
    /* renamed from: a */
    private static final C0608d f1356a;
    /* renamed from: b */
    private final Object f1357b;

    /* renamed from: android.support.v4.view.be$d */
    private interface C0608d {
        /* renamed from: a */
        int mo543a(Object obj);

        /* renamed from: a */
        be mo544a(Object obj, int i, int i2, int i3, int i4);

        /* renamed from: b */
        int mo545b(Object obj);

        /* renamed from: c */
        int mo546c(Object obj);

        /* renamed from: d */
        int mo547d(Object obj);

        /* renamed from: e */
        boolean mo548e(Object obj);
    }

    /* renamed from: android.support.v4.view.be$c */
    private static class C0609c implements C0608d {
        C0609c() {
        }

        /* renamed from: a */
        public int mo543a(Object obj) {
            return 0;
        }

        /* renamed from: a */
        public be mo544a(Object obj, int i, int i2, int i3, int i4) {
            return null;
        }

        /* renamed from: b */
        public int mo545b(Object obj) {
            return 0;
        }

        /* renamed from: c */
        public int mo546c(Object obj) {
            return 0;
        }

        /* renamed from: d */
        public int mo547d(Object obj) {
            return 0;
        }

        /* renamed from: e */
        public boolean mo548e(Object obj) {
            return false;
        }
    }

    /* renamed from: android.support.v4.view.be$a */
    private static class C0610a extends C0609c {
        C0610a() {
        }

        /* renamed from: a */
        public int mo543a(Object obj) {
            return bf.m3082a(obj);
        }

        /* renamed from: a */
        public be mo544a(Object obj, int i, int i2, int i3, int i4) {
            return new be(bf.m3083a(obj, i, i2, i3, i4));
        }

        /* renamed from: b */
        public int mo545b(Object obj) {
            return bf.m3084b(obj);
        }

        /* renamed from: c */
        public int mo546c(Object obj) {
            return bf.m3085c(obj);
        }

        /* renamed from: d */
        public int mo547d(Object obj) {
            return bf.m3086d(obj);
        }
    }

    /* renamed from: android.support.v4.view.be$b */
    private static class C0611b extends C0610a {
        C0611b() {
        }

        /* renamed from: e */
        public boolean mo548e(Object obj) {
            return bg.m3087a(obj);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 21) {
            f1356a = new C0611b();
        } else if (i >= 20) {
            f1356a = new C0610a();
        } else {
            f1356a = new C0609c();
        }
    }

    be(Object obj) {
        this.f1357b = obj;
    }

    /* renamed from: a */
    static be m3074a(Object obj) {
        return obj == null ? null : new be(obj);
    }

    /* renamed from: a */
    static Object m3075a(be beVar) {
        return beVar == null ? null : beVar.f1357b;
    }

    /* renamed from: a */
    public int m3076a() {
        return f1356a.mo545b(this.f1357b);
    }

    /* renamed from: a */
    public be m3077a(int i, int i2, int i3, int i4) {
        return f1356a.mo544a(this.f1357b, i, i2, i3, i4);
    }

    /* renamed from: b */
    public int m3078b() {
        return f1356a.mo547d(this.f1357b);
    }

    /* renamed from: c */
    public int m3079c() {
        return f1356a.mo546c(this.f1357b);
    }

    /* renamed from: d */
    public int m3080d() {
        return f1356a.mo543a(this.f1357b);
    }

    /* renamed from: e */
    public boolean m3081e() {
        return f1356a.mo548e(this.f1357b);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        be beVar = (be) obj;
        return this.f1357b == null ? beVar.f1357b == null : this.f1357b.equals(beVar.f1357b);
    }

    public int hashCode() {
        return this.f1357b == null ? 0 : this.f1357b.hashCode();
    }
}
