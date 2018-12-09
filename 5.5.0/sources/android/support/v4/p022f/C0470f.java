package android.support.v4.p022f;

/* renamed from: android.support.v4.f.f */
public class C0470f<E> implements Cloneable {
    /* renamed from: a */
    private static final Object f1247a = new Object();
    /* renamed from: b */
    private boolean f1248b;
    /* renamed from: c */
    private long[] f1249c;
    /* renamed from: d */
    private Object[] f1250d;
    /* renamed from: e */
    private int f1251e;

    public C0470f() {
        this(10);
    }

    public C0470f(int i) {
        this.f1248b = false;
        if (i == 0) {
            this.f1249c = C0467c.f1243b;
            this.f1250d = C0467c.f1244c;
        } else {
            int b = C0467c.m2012b(i);
            this.f1249c = new long[b];
            this.f1250d = new Object[b];
        }
        this.f1251e = 0;
    }

    /* renamed from: d */
    private void m2016d() {
        int i = this.f1251e;
        long[] jArr = this.f1249c;
        Object[] objArr = this.f1250d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != f1247a) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.f1248b = false;
        this.f1251e = i2;
    }

    /* renamed from: a */
    public C0470f<E> m2017a() {
        try {
            C0470f<E> c0470f = (C0470f) super.clone();
            try {
                c0470f.f1249c = (long[]) this.f1249c.clone();
                c0470f.f1250d = (Object[]) this.f1250d.clone();
                return c0470f;
            } catch (CloneNotSupportedException e) {
                return c0470f;
            }
        } catch (CloneNotSupportedException e2) {
            return null;
        }
    }

    /* renamed from: a */
    public E m2018a(long j) {
        return m2019a(j, null);
    }

    /* renamed from: a */
    public E m2019a(long j, E e) {
        int a = C0467c.m2010a(this.f1249c, this.f1251e, j);
        return (a < 0 || this.f1250d[a] == f1247a) ? e : this.f1250d[a];
    }

    /* renamed from: a */
    public void m2020a(int i) {
        if (this.f1250d[i] != f1247a) {
            this.f1250d[i] = f1247a;
            this.f1248b = true;
        }
    }

    /* renamed from: b */
    public int m2021b() {
        if (this.f1248b) {
            m2016d();
        }
        return this.f1251e;
    }

    /* renamed from: b */
    public long m2022b(int i) {
        if (this.f1248b) {
            m2016d();
        }
        return this.f1249c[i];
    }

    /* renamed from: b */
    public void m2023b(long j) {
        int a = C0467c.m2010a(this.f1249c, this.f1251e, j);
        if (a >= 0 && this.f1250d[a] != f1247a) {
            this.f1250d[a] = f1247a;
            this.f1248b = true;
        }
    }

    /* renamed from: b */
    public void m2024b(long j, E e) {
        int a = C0467c.m2010a(this.f1249c, this.f1251e, j);
        if (a >= 0) {
            this.f1250d[a] = e;
            return;
        }
        a ^= -1;
        if (a >= this.f1251e || this.f1250d[a] != f1247a) {
            if (this.f1248b && this.f1251e >= this.f1249c.length) {
                m2016d();
                a = C0467c.m2010a(this.f1249c, this.f1251e, j) ^ -1;
            }
            if (this.f1251e >= this.f1249c.length) {
                int b = C0467c.m2012b(this.f1251e + 1);
                Object obj = new long[b];
                Object obj2 = new Object[b];
                System.arraycopy(this.f1249c, 0, obj, 0, this.f1249c.length);
                System.arraycopy(this.f1250d, 0, obj2, 0, this.f1250d.length);
                this.f1249c = obj;
                this.f1250d = obj2;
            }
            if (this.f1251e - a != 0) {
                System.arraycopy(this.f1249c, a, this.f1249c, a + 1, this.f1251e - a);
                System.arraycopy(this.f1250d, a, this.f1250d, a + 1, this.f1251e - a);
            }
            this.f1249c[a] = j;
            this.f1250d[a] = e;
            this.f1251e++;
            return;
        }
        this.f1249c[a] = j;
        this.f1250d[a] = e;
    }

    /* renamed from: c */
    public E m2025c(int i) {
        if (this.f1248b) {
            m2016d();
        }
        return this.f1250d[i];
    }

    /* renamed from: c */
    public void m2026c() {
        int i = this.f1251e;
        Object[] objArr = this.f1250d;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.f1251e = 0;
        this.f1248b = false;
    }

    public /* synthetic */ Object clone() {
        return m2017a();
    }

    public String toString() {
        if (m2021b() <= 0) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.f1251e * 28);
        stringBuilder.append('{');
        for (int i = 0; i < this.f1251e; i++) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(m2022b(i));
            stringBuilder.append('=');
            C0470f c = m2025c(i);
            if (c != this) {
                stringBuilder.append(c);
            } else {
                stringBuilder.append("(this Map)");
            }
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
