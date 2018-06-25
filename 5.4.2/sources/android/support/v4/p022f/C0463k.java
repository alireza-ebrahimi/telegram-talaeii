package android.support.v4.p022f;

import java.util.Map;

/* renamed from: android.support.v4.f.k */
public class C0463k<K, V> {
    /* renamed from: b */
    static Object[] f1222b;
    /* renamed from: c */
    static int f1223c;
    /* renamed from: d */
    static Object[] f1224d;
    /* renamed from: e */
    static int f1225e;
    /* renamed from: f */
    int[] f1226f;
    /* renamed from: g */
    Object[] f1227g;
    /* renamed from: h */
    int f1228h;

    public C0463k() {
        this.f1226f = C0467c.f1242a;
        this.f1227g = C0467c.f1244c;
        this.f1228h = 0;
    }

    public C0463k(int i) {
        if (i == 0) {
            this.f1226f = C0467c.f1242a;
            this.f1227g = C0467c.f1244c;
        } else {
            m1978e(i);
        }
        this.f1228h = 0;
    }

    /* renamed from: a */
    private static void m1977a(int[] iArr, Object[] objArr, int i) {
        int i2;
        if (iArr.length == 8) {
            synchronized (C0464a.class) {
                if (f1225e < 10) {
                    objArr[0] = f1224d;
                    objArr[1] = iArr;
                    for (i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    f1224d = objArr;
                    f1225e++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (C0464a.class) {
                if (f1223c < 10) {
                    objArr[0] = f1222b;
                    objArr[1] = iArr;
                    for (i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    f1222b = objArr;
                    f1223c++;
                }
            }
        }
    }

    /* renamed from: e */
    private void m1978e(int i) {
        Object[] objArr;
        if (i == 8) {
            synchronized (C0464a.class) {
                if (f1224d != null) {
                    objArr = f1224d;
                    this.f1227g = objArr;
                    f1224d = (Object[]) objArr[0];
                    this.f1226f = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    f1225e--;
                    return;
                }
            }
        } else if (i == 4) {
            synchronized (C0464a.class) {
                if (f1222b != null) {
                    objArr = f1222b;
                    this.f1227g = objArr;
                    f1222b = (Object[]) objArr[0];
                    this.f1226f = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    f1223c--;
                    return;
                }
            }
        }
        this.f1226f = new int[i];
        this.f1227g = new Object[(i << 1)];
    }

    /* renamed from: a */
    int m1979a() {
        int i = this.f1228h;
        if (i == 0) {
            return -1;
        }
        int a = C0467c.m2009a(this.f1226f, i, 0);
        if (a < 0 || this.f1227g[a << 1] == null) {
            return a;
        }
        int i2 = a + 1;
        while (i2 < i && this.f1226f[i2] == 0) {
            if (this.f1227g[i2 << 1] == null) {
                return i2;
            }
            i2++;
        }
        a--;
        while (a >= 0 && this.f1226f[a] == 0) {
            if (this.f1227g[a << 1] == null) {
                return a;
            }
            a--;
        }
        return i2 ^ -1;
    }

    /* renamed from: a */
    public int m1980a(Object obj) {
        return obj == null ? m1979a() : m1981a(obj, obj.hashCode());
    }

    /* renamed from: a */
    int m1981a(Object obj, int i) {
        int i2 = this.f1228h;
        if (i2 == 0) {
            return -1;
        }
        int a = C0467c.m2009a(this.f1226f, i2, i);
        if (a < 0 || obj.equals(this.f1227g[a << 1])) {
            return a;
        }
        int i3 = a + 1;
        while (i3 < i2 && this.f1226f[i3] == i) {
            if (obj.equals(this.f1227g[i3 << 1])) {
                return i3;
            }
            i3++;
        }
        a--;
        while (a >= 0 && this.f1226f[a] == i) {
            if (obj.equals(this.f1227g[a << 1])) {
                return a;
            }
            a--;
        }
        return i3 ^ -1;
    }

    /* renamed from: a */
    public V m1982a(int i, V v) {
        int i2 = (i << 1) + 1;
        V v2 = this.f1227g[i2];
        this.f1227g[i2] = v;
        return v2;
    }

    /* renamed from: a */
    public void m1983a(int i) {
        if (this.f1226f.length < i) {
            Object obj = this.f1226f;
            Object obj2 = this.f1227g;
            m1978e(i);
            if (this.f1228h > 0) {
                System.arraycopy(obj, 0, this.f1226f, 0, this.f1228h);
                System.arraycopy(obj2, 0, this.f1227g, 0, this.f1228h << 1);
            }
            C0463k.m1977a(obj, obj2, this.f1228h);
        }
    }

    /* renamed from: b */
    int m1984b(Object obj) {
        int i = 1;
        int i2 = this.f1228h * 2;
        Object[] objArr = this.f1227g;
        if (obj == null) {
            while (i < i2) {
                if (objArr[i] == null) {
                    return i >> 1;
                }
                i += 2;
            }
        } else {
            while (i < i2) {
                if (obj.equals(objArr[i])) {
                    return i >> 1;
                }
                i += 2;
            }
        }
        return -1;
    }

    /* renamed from: b */
    public K m1985b(int i) {
        return this.f1227g[i << 1];
    }

    /* renamed from: c */
    public V m1986c(int i) {
        return this.f1227g[(i << 1) + 1];
    }

    public void clear() {
        if (this.f1228h != 0) {
            C0463k.m1977a(this.f1226f, this.f1227g, this.f1228h);
            this.f1226f = C0467c.f1242a;
            this.f1227g = C0467c.f1244c;
            this.f1228h = 0;
        }
    }

    public boolean containsKey(Object obj) {
        return m1980a(obj) >= 0;
    }

    public boolean containsValue(Object obj) {
        return m1984b(obj) >= 0;
    }

    /* renamed from: d */
    public V m1987d(int i) {
        int i2 = 8;
        V v = this.f1227g[(i << 1) + 1];
        if (this.f1228h <= 1) {
            C0463k.m1977a(this.f1226f, this.f1227g, this.f1228h);
            this.f1226f = C0467c.f1242a;
            this.f1227g = C0467c.f1244c;
            this.f1228h = 0;
        } else if (this.f1226f.length <= 8 || this.f1228h >= this.f1226f.length / 3) {
            this.f1228h--;
            if (i < this.f1228h) {
                System.arraycopy(this.f1226f, i + 1, this.f1226f, i, this.f1228h - i);
                System.arraycopy(this.f1227g, (i + 1) << 1, this.f1227g, i << 1, (this.f1228h - i) << 1);
            }
            this.f1227g[this.f1228h << 1] = null;
            this.f1227g[(this.f1228h << 1) + 1] = null;
        } else {
            if (this.f1228h > 8) {
                i2 = this.f1228h + (this.f1228h >> 1);
            }
            Object obj = this.f1226f;
            Object obj2 = this.f1227g;
            m1978e(i2);
            this.f1228h--;
            if (i > 0) {
                System.arraycopy(obj, 0, this.f1226f, 0, i);
                System.arraycopy(obj2, 0, this.f1227g, 0, i << 1);
            }
            if (i < this.f1228h) {
                System.arraycopy(obj, i + 1, this.f1226f, i, this.f1228h - i);
                System.arraycopy(obj2, (i + 1) << 1, this.f1227g, i << 1, (this.f1228h - i) << 1);
            }
        }
        return v;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        int i;
        Object b;
        Object c;
        Object obj2;
        if (obj instanceof C0463k) {
            C0463k c0463k = (C0463k) obj;
            if (size() != c0463k.size()) {
                return false;
            }
            i = 0;
            while (i < this.f1228h) {
                try {
                    b = m1985b(i);
                    c = m1986c(i);
                    obj2 = c0463k.get(b);
                    if (c == null) {
                        if (obj2 != null || !c0463k.containsKey(b)) {
                            return false;
                        }
                    } else if (!c.equals(obj2)) {
                        return false;
                    }
                    i++;
                } catch (NullPointerException e) {
                    return false;
                } catch (ClassCastException e2) {
                    return false;
                }
            }
            return true;
        } else if (!(obj instanceof Map)) {
            return false;
        } else {
            Map map = (Map) obj;
            if (size() != map.size()) {
                return false;
            }
            i = 0;
            while (i < this.f1228h) {
                try {
                    b = m1985b(i);
                    c = m1986c(i);
                    obj2 = map.get(b);
                    if (c == null) {
                        if (obj2 != null || !map.containsKey(b)) {
                            return false;
                        }
                    } else if (!c.equals(obj2)) {
                        return false;
                    }
                    i++;
                } catch (NullPointerException e3) {
                    return false;
                } catch (ClassCastException e4) {
                    return false;
                }
            }
            return true;
        }
    }

    public V get(Object obj) {
        int a = m1980a(obj);
        return a >= 0 ? this.f1227g[(a << 1) + 1] : null;
    }

    public int hashCode() {
        int[] iArr = this.f1226f;
        Object[] objArr = this.f1227g;
        int i = this.f1228h;
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            Object obj = objArr[i2];
            i4 += (obj == null ? 0 : obj.hashCode()) ^ iArr[i3];
            i3++;
            i2 += 2;
        }
        return i4;
    }

    public boolean isEmpty() {
        return this.f1228h <= 0;
    }

    public V put(K k, V v) {
        int a;
        int i;
        int i2 = 8;
        if (k == null) {
            a = m1979a();
            i = 0;
        } else {
            i = k.hashCode();
            a = m1981a((Object) k, i);
        }
        if (a >= 0) {
            int i3 = (a << 1) + 1;
            V v2 = this.f1227g[i3];
            this.f1227g[i3] = v;
            return v2;
        }
        a ^= -1;
        if (this.f1228h >= this.f1226f.length) {
            if (this.f1228h >= 8) {
                i2 = this.f1228h + (this.f1228h >> 1);
            } else if (this.f1228h < 4) {
                i2 = 4;
            }
            Object obj = this.f1226f;
            Object obj2 = this.f1227g;
            m1978e(i2);
            if (this.f1226f.length > 0) {
                System.arraycopy(obj, 0, this.f1226f, 0, obj.length);
                System.arraycopy(obj2, 0, this.f1227g, 0, obj2.length);
            }
            C0463k.m1977a(obj, obj2, this.f1228h);
        }
        if (a < this.f1228h) {
            System.arraycopy(this.f1226f, a, this.f1226f, a + 1, this.f1228h - a);
            System.arraycopy(this.f1227g, a << 1, this.f1227g, (a + 1) << 1, (this.f1228h - a) << 1);
        }
        this.f1226f[a] = i;
        this.f1227g[a << 1] = k;
        this.f1227g[(a << 1) + 1] = v;
        this.f1228h++;
        return null;
    }

    public V remove(Object obj) {
        int a = m1980a(obj);
        return a >= 0 ? m1987d(a) : null;
    }

    public int size() {
        return this.f1228h;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.f1228h * 28);
        stringBuilder.append('{');
        for (int i = 0; i < this.f1228h; i++) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            C0463k b = m1985b(i);
            if (b != this) {
                stringBuilder.append(b);
            } else {
                stringBuilder.append("(this Map)");
            }
            stringBuilder.append('=');
            b = m1986c(i);
            if (b != this) {
                stringBuilder.append(b);
            } else {
                stringBuilder.append("(this Map)");
            }
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
