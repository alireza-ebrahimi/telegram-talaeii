package android.support.v4.p022f;

/* renamed from: android.support.v4.f.l */
public class C0482l<E> implements Cloneable {
    /* renamed from: a */
    private static final Object f1269a = new Object();
    /* renamed from: b */
    private boolean f1270b;
    /* renamed from: c */
    private int[] f1271c;
    /* renamed from: d */
    private Object[] f1272d;
    /* renamed from: e */
    private int f1273e;

    public C0482l() {
        this(10);
    }

    public C0482l(int i) {
        this.f1270b = false;
        if (i == 0) {
            this.f1271c = C0467c.f1242a;
            this.f1272d = C0467c.f1244c;
        } else {
            int a = C0467c.m2008a(i);
            this.f1271c = new int[a];
            this.f1272d = new Object[a];
        }
        this.f1273e = 0;
    }

    /* renamed from: d */
    private void m2038d() {
        int i = this.f1273e;
        int[] iArr = this.f1271c;
        Object[] objArr = this.f1272d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != f1269a) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.f1270b = false;
        this.f1273e = i2;
    }

    /* renamed from: a */
    public C0482l<E> m2039a() {
        try {
            C0482l<E> c0482l = (C0482l) super.clone();
            try {
                c0482l.f1271c = (int[]) this.f1271c.clone();
                c0482l.f1272d = (Object[]) this.f1272d.clone();
                return c0482l;
            } catch (CloneNotSupportedException e) {
                return c0482l;
            }
        } catch (CloneNotSupportedException e2) {
            return null;
        }
    }

    /* renamed from: a */
    public E m2040a(int i) {
        return m2041a(i, null);
    }

    /* renamed from: a */
    public E m2041a(int i, E e) {
        int a = C0467c.m2009a(this.f1271c, this.f1273e, i);
        return (a < 0 || this.f1272d[a] == f1269a) ? e : this.f1272d[a];
    }

    /* renamed from: b */
    public int m2042b() {
        if (this.f1270b) {
            m2038d();
        }
        return this.f1273e;
    }

    /* renamed from: b */
    public void m2043b(int i) {
        int a = C0467c.m2009a(this.f1271c, this.f1273e, i);
        if (a >= 0 && this.f1272d[a] != f1269a) {
            this.f1272d[a] = f1269a;
            this.f1270b = true;
        }
    }

    /* renamed from: b */
    public void m2044b(int i, E e) {
        int a = C0467c.m2009a(this.f1271c, this.f1273e, i);
        if (a >= 0) {
            this.f1272d[a] = e;
            return;
        }
        a ^= -1;
        if (a >= this.f1273e || this.f1272d[a] != f1269a) {
            if (this.f1270b && this.f1273e >= this.f1271c.length) {
                m2038d();
                a = C0467c.m2009a(this.f1271c, this.f1273e, i) ^ -1;
            }
            if (this.f1273e >= this.f1271c.length) {
                int a2 = C0467c.m2008a(this.f1273e + 1);
                Object obj = new int[a2];
                Object obj2 = new Object[a2];
                System.arraycopy(this.f1271c, 0, obj, 0, this.f1271c.length);
                System.arraycopy(this.f1272d, 0, obj2, 0, this.f1272d.length);
                this.f1271c = obj;
                this.f1272d = obj2;
            }
            if (this.f1273e - a != 0) {
                System.arraycopy(this.f1271c, a, this.f1271c, a + 1, this.f1273e - a);
                System.arraycopy(this.f1272d, a, this.f1272d, a + 1, this.f1273e - a);
            }
            this.f1271c[a] = i;
            this.f1272d[a] = e;
            this.f1273e++;
            return;
        }
        this.f1271c[a] = i;
        this.f1272d[a] = e;
    }

    /* renamed from: c */
    public void m2045c() {
        int i = this.f1273e;
        Object[] objArr = this.f1272d;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.f1273e = 0;
        this.f1270b = false;
    }

    /* renamed from: c */
    public void m2046c(int i) {
        m2043b(i);
    }

    /* renamed from: c */
    public void m2047c(int i, E e) {
        if (this.f1273e == 0 || i > this.f1271c[this.f1273e - 1]) {
            if (this.f1270b && this.f1273e >= this.f1271c.length) {
                m2038d();
            }
            int i2 = this.f1273e;
            if (i2 >= this.f1271c.length) {
                int a = C0467c.m2008a(i2 + 1);
                Object obj = new int[a];
                Object obj2 = new Object[a];
                System.arraycopy(this.f1271c, 0, obj, 0, this.f1271c.length);
                System.arraycopy(this.f1272d, 0, obj2, 0, this.f1272d.length);
                this.f1271c = obj;
                this.f1272d = obj2;
            }
            this.f1271c[i2] = i;
            this.f1272d[i2] = e;
            this.f1273e = i2 + 1;
            return;
        }
        m2044b(i, e);
    }

    public /* synthetic */ Object clone() {
        return m2039a();
    }

    /* renamed from: d */
    public int m2048d(int i) {
        if (this.f1270b) {
            m2038d();
        }
        return this.f1271c[i];
    }

    /* renamed from: e */
    public E m2049e(int i) {
        if (this.f1270b) {
            m2038d();
        }
        return this.f1272d[i];
    }

    /* renamed from: f */
    public int m2050f(int i) {
        if (this.f1270b) {
            m2038d();
        }
        return C0467c.m2009a(this.f1271c, this.f1273e, i);
    }

    public String toString() {
        if (m2042b() <= 0) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.f1273e * 28);
        stringBuilder.append('{');
        for (int i = 0; i < this.f1273e; i++) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(m2048d(i));
            stringBuilder.append('=');
            C0482l e = m2049e(i);
            if (e != this) {
                stringBuilder.append(e);
            } else {
                stringBuilder.append("(this Map)");
            }
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
