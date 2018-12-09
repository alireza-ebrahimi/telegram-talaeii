package android.support.v4.p022f;

/* renamed from: android.support.v4.f.j */
public final class C0481j {

    /* renamed from: android.support.v4.f.j$a */
    public interface C0478a<T> {
        /* renamed from: a */
        T mo332a();

        /* renamed from: a */
        boolean mo333a(T t);
    }

    /* renamed from: android.support.v4.f.j$b */
    public static class C0479b<T> implements C0478a<T> {
        /* renamed from: a */
        private final Object[] f1266a;
        /* renamed from: b */
        private int f1267b;

        public C0479b(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("The max pool size must be > 0");
            }
            this.f1266a = new Object[i];
        }

        /* renamed from: b */
        private boolean m2033b(T t) {
            for (int i = 0; i < this.f1267b; i++) {
                if (this.f1266a[i] == t) {
                    return true;
                }
            }
            return false;
        }

        /* renamed from: a */
        public T mo332a() {
            if (this.f1267b <= 0) {
                return null;
            }
            int i = this.f1267b - 1;
            T t = this.f1266a[i];
            this.f1266a[i] = null;
            this.f1267b--;
            return t;
        }

        /* renamed from: a */
        public boolean mo333a(T t) {
            if (m2033b(t)) {
                throw new IllegalStateException("Already in the pool!");
            } else if (this.f1267b >= this.f1266a.length) {
                return false;
            } else {
                this.f1266a[this.f1267b] = t;
                this.f1267b++;
                return true;
            }
        }
    }

    /* renamed from: android.support.v4.f.j$c */
    public static class C0480c<T> extends C0479b<T> {
        /* renamed from: a */
        private final Object f1268a = new Object();

        public C0480c(int i) {
            super(i);
        }

        /* renamed from: a */
        public T mo332a() {
            T a;
            synchronized (this.f1268a) {
                a = super.mo332a();
            }
            return a;
        }

        /* renamed from: a */
        public boolean mo333a(T t) {
            boolean a;
            synchronized (this.f1268a) {
                a = super.mo333a(t);
            }
            return a;
        }
    }
}
