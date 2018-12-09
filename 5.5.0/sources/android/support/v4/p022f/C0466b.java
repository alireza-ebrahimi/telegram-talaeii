package android.support.v4.p022f;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* renamed from: android.support.v4.f.b */
public final class C0466b<E> implements Collection<E>, Set<E> {
    /* renamed from: a */
    static Object[] f1231a;
    /* renamed from: b */
    static int f1232b;
    /* renamed from: c */
    static Object[] f1233c;
    /* renamed from: d */
    static int f1234d;
    /* renamed from: j */
    private static final int[] f1235j = new int[0];
    /* renamed from: k */
    private static final Object[] f1236k = new Object[0];
    /* renamed from: e */
    final boolean f1237e;
    /* renamed from: f */
    int[] f1238f;
    /* renamed from: g */
    Object[] f1239g;
    /* renamed from: h */
    int f1240h;
    /* renamed from: i */
    C0461h<E, E> f1241i;

    /* renamed from: android.support.v4.f.b$1 */
    class C04651 extends C0461h<E, E> {
        /* renamed from: a */
        final /* synthetic */ C0466b f1230a;

        C04651(C0466b c0466b) {
            this.f1230a = c0466b;
        }

        /* renamed from: a */
        protected int mo323a() {
            return this.f1230a.f1240h;
        }

        /* renamed from: a */
        protected int mo324a(Object obj) {
            return this.f1230a.m2004a(obj);
        }

        /* renamed from: a */
        protected Object mo325a(int i, int i2) {
            return this.f1230a.f1239g[i];
        }

        /* renamed from: a */
        protected E mo326a(int i, E e) {
            throw new UnsupportedOperationException("not a map");
        }

        /* renamed from: a */
        protected void mo327a(int i) {
            this.f1230a.m2007c(i);
        }

        /* renamed from: a */
        protected void mo328a(E e, E e2) {
            this.f1230a.add(e);
        }

        /* renamed from: b */
        protected int mo329b(Object obj) {
            return this.f1230a.m2004a(obj);
        }

        /* renamed from: b */
        protected Map<E, E> mo330b() {
            throw new UnsupportedOperationException("not a map");
        }

        /* renamed from: c */
        protected void mo331c() {
            this.f1230a.clear();
        }
    }

    public C0466b() {
        this(0, false);
    }

    public C0466b(int i) {
        this(i, false);
    }

    public C0466b(int i, boolean z) {
        this.f1237e = z;
        if (i == 0) {
            this.f1238f = f1235j;
            this.f1239g = f1236k;
        } else {
            m2003d(i);
        }
        this.f1240h = 0;
    }

    /* renamed from: a */
    private int m1999a() {
        int i = this.f1240h;
        if (i == 0) {
            return -1;
        }
        int a = C0467c.m2009a(this.f1238f, i, 0);
        if (a < 0 || this.f1239g[a] == null) {
            return a;
        }
        int i2 = a + 1;
        while (i2 < i && this.f1238f[i2] == 0) {
            if (this.f1239g[i2] == null) {
                return i2;
            }
            i2++;
        }
        a--;
        while (a >= 0 && this.f1238f[a] == 0) {
            if (this.f1239g[a] == null) {
                return a;
            }
            a--;
        }
        return i2 ^ -1;
    }

    /* renamed from: a */
    private int m2000a(Object obj, int i) {
        int i2 = this.f1240h;
        if (i2 == 0) {
            return -1;
        }
        int a = C0467c.m2009a(this.f1238f, i2, i);
        if (a < 0 || obj.equals(this.f1239g[a])) {
            return a;
        }
        int i3 = a + 1;
        while (i3 < i2 && this.f1238f[i3] == i) {
            if (obj.equals(this.f1239g[i3])) {
                return i3;
            }
            i3++;
        }
        a--;
        while (a >= 0 && this.f1238f[a] == i) {
            if (obj.equals(this.f1239g[a])) {
                return a;
            }
            a--;
        }
        return i3 ^ -1;
    }

    /* renamed from: a */
    private static void m2001a(int[] iArr, Object[] objArr, int i) {
        int i2;
        if (iArr.length == 8) {
            synchronized (C0466b.class) {
                if (f1234d < 10) {
                    objArr[0] = f1233c;
                    objArr[1] = iArr;
                    for (i2 = i - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    f1233c = objArr;
                    f1234d++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (C0466b.class) {
                if (f1232b < 10) {
                    objArr[0] = f1231a;
                    objArr[1] = iArr;
                    for (i2 = i - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    f1231a = objArr;
                    f1232b++;
                }
            }
        }
    }

    /* renamed from: b */
    private C0461h<E, E> m2002b() {
        if (this.f1241i == null) {
            this.f1241i = new C04651(this);
        }
        return this.f1241i;
    }

    /* renamed from: d */
    private void m2003d(int i) {
        Object[] objArr;
        if (i == 8) {
            synchronized (C0466b.class) {
                if (f1233c != null) {
                    objArr = f1233c;
                    this.f1239g = objArr;
                    f1233c = (Object[]) objArr[0];
                    this.f1238f = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    f1234d--;
                    return;
                }
            }
        } else if (i == 4) {
            synchronized (C0466b.class) {
                if (f1231a != null) {
                    objArr = f1231a;
                    this.f1239g = objArr;
                    f1231a = (Object[]) objArr[0];
                    this.f1238f = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    f1232b--;
                    return;
                }
            }
        }
        this.f1238f = new int[i];
        this.f1239g = new Object[i];
    }

    /* renamed from: a */
    public int m2004a(Object obj) {
        if (obj == null) {
            return m1999a();
        }
        return m2000a(obj, this.f1237e ? System.identityHashCode(obj) : obj.hashCode());
    }

    /* renamed from: a */
    public void m2005a(int i) {
        if (this.f1238f.length < i) {
            Object obj = this.f1238f;
            Object obj2 = this.f1239g;
            m2003d(i);
            if (this.f1240h > 0) {
                System.arraycopy(obj, 0, this.f1238f, 0, this.f1240h);
                System.arraycopy(obj2, 0, this.f1239g, 0, this.f1240h);
            }
            C0466b.m2001a(obj, obj2, this.f1240h);
        }
    }

    public boolean add(E e) {
        int a;
        int i;
        if (e == null) {
            a = m1999a();
            i = 0;
        } else {
            a = this.f1237e ? System.identityHashCode(e) : e.hashCode();
            i = a;
            a = m2000a(e, a);
        }
        if (a >= 0) {
            return false;
        }
        int i2 = a ^ -1;
        if (this.f1240h >= this.f1238f.length) {
            a = this.f1240h >= 8 ? this.f1240h + (this.f1240h >> 1) : this.f1240h >= 4 ? 8 : 4;
            Object obj = this.f1238f;
            Object obj2 = this.f1239g;
            m2003d(a);
            if (this.f1238f.length > 0) {
                System.arraycopy(obj, 0, this.f1238f, 0, obj.length);
                System.arraycopy(obj2, 0, this.f1239g, 0, obj2.length);
            }
            C0466b.m2001a(obj, obj2, this.f1240h);
        }
        if (i2 < this.f1240h) {
            System.arraycopy(this.f1238f, i2, this.f1238f, i2 + 1, this.f1240h - i2);
            System.arraycopy(this.f1239g, i2, this.f1239g, i2 + 1, this.f1240h - i2);
        }
        this.f1238f[i2] = i;
        this.f1239g[i2] = e;
        this.f1240h++;
        return true;
    }

    public boolean addAll(Collection<? extends E> collection) {
        m2005a(this.f1240h + collection.size());
        boolean z = false;
        for (Object add : collection) {
            z |= add(add);
        }
        return z;
    }

    /* renamed from: b */
    public E m2006b(int i) {
        return this.f1239g[i];
    }

    /* renamed from: c */
    public E m2007c(int i) {
        int i2 = 8;
        E e = this.f1239g[i];
        if (this.f1240h <= 1) {
            C0466b.m2001a(this.f1238f, this.f1239g, this.f1240h);
            this.f1238f = f1235j;
            this.f1239g = f1236k;
            this.f1240h = 0;
        } else if (this.f1238f.length <= 8 || this.f1240h >= this.f1238f.length / 3) {
            this.f1240h--;
            if (i < this.f1240h) {
                System.arraycopy(this.f1238f, i + 1, this.f1238f, i, this.f1240h - i);
                System.arraycopy(this.f1239g, i + 1, this.f1239g, i, this.f1240h - i);
            }
            this.f1239g[this.f1240h] = null;
        } else {
            if (this.f1240h > 8) {
                i2 = this.f1240h + (this.f1240h >> 1);
            }
            Object obj = this.f1238f;
            Object obj2 = this.f1239g;
            m2003d(i2);
            this.f1240h--;
            if (i > 0) {
                System.arraycopy(obj, 0, this.f1238f, 0, i);
                System.arraycopy(obj2, 0, this.f1239g, 0, i);
            }
            if (i < this.f1240h) {
                System.arraycopy(obj, i + 1, this.f1238f, i, this.f1240h - i);
                System.arraycopy(obj2, i + 1, this.f1239g, i, this.f1240h - i);
            }
        }
        return e;
    }

    public void clear() {
        if (this.f1240h != 0) {
            C0466b.m2001a(this.f1238f, this.f1239g, this.f1240h);
            this.f1238f = f1235j;
            this.f1239g = f1236k;
            this.f1240h = 0;
        }
    }

    public boolean contains(Object obj) {
        return m2004a(obj) >= 0;
    }

    public boolean containsAll(Collection<?> collection) {
        for (Object contains : collection) {
            if (!contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (size() != set.size()) {
            return false;
        }
        int i = 0;
        while (i < this.f1240h) {
            try {
                if (!set.contains(m2006b(i))) {
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
    }

    public int hashCode() {
        int i = 0;
        int[] iArr = this.f1238f;
        int i2 = 0;
        while (i < this.f1240h) {
            i2 += iArr[i];
            i++;
        }
        return i2;
    }

    public boolean isEmpty() {
        return this.f1240h <= 0;
    }

    public Iterator<E> iterator() {
        return m2002b().m1966e().iterator();
    }

    public boolean remove(Object obj) {
        int a = m2004a(obj);
        if (a < 0) {
            return false;
        }
        m2007c(a);
        return true;
    }

    public boolean removeAll(Collection<?> collection) {
        boolean z = false;
        for (Object remove : collection) {
            z |= remove(remove);
        }
        return z;
    }

    public boolean retainAll(Collection<?> collection) {
        boolean z = false;
        for (int i = this.f1240h - 1; i >= 0; i--) {
            if (!collection.contains(this.f1239g[i])) {
                m2007c(i);
                z = true;
            }
        }
        return z;
    }

    public int size() {
        return this.f1240h;
    }

    public Object[] toArray() {
        Object obj = new Object[this.f1240h];
        System.arraycopy(this.f1239g, 0, obj, 0, this.f1240h);
        return obj;
    }

    public <T> T[] toArray(T[] tArr) {
        Object obj = tArr.length < this.f1240h ? (Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.f1240h) : tArr;
        System.arraycopy(this.f1239g, 0, obj, 0, this.f1240h);
        if (obj.length > this.f1240h) {
            obj[this.f1240h] = null;
        }
        return obj;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.f1240h * 14);
        stringBuilder.append('{');
        for (int i = 0; i < this.f1240h; i++) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            C0466b b = m2006b(i);
            if (b != this) {
                stringBuilder.append(b);
            } else {
                stringBuilder.append("(this Set)");
            }
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
