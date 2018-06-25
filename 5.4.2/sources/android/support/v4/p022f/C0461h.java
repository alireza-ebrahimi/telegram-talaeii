package android.support.v4.p022f;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: android.support.v4.f.h */
abstract class C0461h<K, V> {
    /* renamed from: b */
    C0473b f1218b;
    /* renamed from: c */
    C0474c f1219c;
    /* renamed from: d */
    C0476e f1220d;

    /* renamed from: android.support.v4.f.h$a */
    final class C0472a<T> implements Iterator<T> {
        /* renamed from: a */
        final int f1252a;
        /* renamed from: b */
        int f1253b;
        /* renamed from: c */
        int f1254c;
        /* renamed from: d */
        boolean f1255d = false;
        /* renamed from: e */
        final /* synthetic */ C0461h f1256e;

        C0472a(C0461h c0461h, int i) {
            this.f1256e = c0461h;
            this.f1252a = i;
            this.f1253b = c0461h.mo323a();
        }

        public boolean hasNext() {
            return this.f1254c < this.f1253b;
        }

        public T next() {
            T a = this.f1256e.mo325a(this.f1254c, this.f1252a);
            this.f1254c++;
            this.f1255d = true;
            return a;
        }

        public void remove() {
            if (this.f1255d) {
                this.f1254c--;
                this.f1253b--;
                this.f1255d = false;
                this.f1256e.mo327a(this.f1254c);
                return;
            }
            throw new IllegalStateException();
        }
    }

    /* renamed from: android.support.v4.f.h$b */
    final class C0473b implements Set<Entry<K, V>> {
        /* renamed from: a */
        final /* synthetic */ C0461h f1257a;

        C0473b(C0461h c0461h) {
            this.f1257a = c0461h;
        }

        /* renamed from: a */
        public boolean m2027a(Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        public /* synthetic */ boolean add(Object obj) {
            return m2027a((Entry) obj);
        }

        public boolean addAll(Collection<? extends Entry<K, V>> collection) {
            int a = this.f1257a.mo323a();
            for (Entry entry : collection) {
                this.f1257a.mo328a(entry.getKey(), entry.getValue());
            }
            return a != this.f1257a.mo323a();
        }

        public void clear() {
            this.f1257a.mo331c();
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            int a = this.f1257a.mo324a(entry.getKey());
            return a >= 0 ? C0467c.m2011a(this.f1257a.mo325a(a, 1), entry.getValue()) : false;
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
            return C0461h.m1951a((Set) this, obj);
        }

        public int hashCode() {
            int a = this.f1257a.mo323a() - 1;
            int i = 0;
            while (a >= 0) {
                Object a2 = this.f1257a.mo325a(a, 0);
                Object a3 = this.f1257a.mo325a(a, 1);
                a--;
                i += (a3 == null ? 0 : a3.hashCode()) ^ (a2 == null ? 0 : a2.hashCode());
            }
            return i;
        }

        public boolean isEmpty() {
            return this.f1257a.mo323a() == 0;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new C0475d(this.f1257a);
        }

        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            return this.f1257a.mo323a();
        }

        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        public <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }
    }

    /* renamed from: android.support.v4.f.h$c */
    final class C0474c implements Set<K> {
        /* renamed from: a */
        final /* synthetic */ C0461h f1258a;

        C0474c(C0461h c0461h) {
            this.f1258a = c0461h;
        }

        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            this.f1258a.mo331c();
        }

        public boolean contains(Object obj) {
            return this.f1258a.mo324a(obj) >= 0;
        }

        public boolean containsAll(Collection<?> collection) {
            return C0461h.m1950a(this.f1258a.mo330b(), (Collection) collection);
        }

        public boolean equals(Object obj) {
            return C0461h.m1951a((Set) this, obj);
        }

        public int hashCode() {
            int i = 0;
            for (int a = this.f1258a.mo323a() - 1; a >= 0; a--) {
                Object a2 = this.f1258a.mo325a(a, 0);
                i += a2 == null ? 0 : a2.hashCode();
            }
            return i;
        }

        public boolean isEmpty() {
            return this.f1258a.mo323a() == 0;
        }

        public Iterator<K> iterator() {
            return new C0472a(this.f1258a, 0);
        }

        public boolean remove(Object obj) {
            int a = this.f1258a.mo324a(obj);
            if (a < 0) {
                return false;
            }
            this.f1258a.mo327a(a);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            return C0461h.m1952b(this.f1258a.mo330b(), collection);
        }

        public boolean retainAll(Collection<?> collection) {
            return C0461h.m1953c(this.f1258a.mo330b(), collection);
        }

        public int size() {
            return this.f1258a.mo323a();
        }

        public Object[] toArray() {
            return this.f1258a.m1963b(0);
        }

        public <T> T[] toArray(T[] tArr) {
            return this.f1258a.m1960a((Object[]) tArr, 0);
        }
    }

    /* renamed from: android.support.v4.f.h$d */
    final class C0475d implements Iterator<Entry<K, V>>, Entry<K, V> {
        /* renamed from: a */
        int f1259a;
        /* renamed from: b */
        int f1260b;
        /* renamed from: c */
        boolean f1261c = false;
        /* renamed from: d */
        final /* synthetic */ C0461h f1262d;

        C0475d(C0461h c0461h) {
            this.f1262d = c0461h;
            this.f1259a = c0461h.mo323a() - 1;
            this.f1260b = -1;
        }

        /* renamed from: a */
        public Entry<K, V> m2028a() {
            this.f1260b++;
            this.f1261c = true;
            return this;
        }

        public final boolean equals(Object obj) {
            boolean z = true;
            if (!this.f1261c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            } else if (!(obj instanceof Entry)) {
                return false;
            } else {
                Entry entry = (Entry) obj;
                if (!(C0467c.m2011a(entry.getKey(), this.f1262d.mo325a(this.f1260b, 0)) && C0467c.m2011a(entry.getValue(), this.f1262d.mo325a(this.f1260b, 1)))) {
                    z = false;
                }
                return z;
            }
        }

        public K getKey() {
            if (this.f1261c) {
                return this.f1262d.mo325a(this.f1260b, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public V getValue() {
            if (this.f1261c) {
                return this.f1262d.mo325a(this.f1260b, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public boolean hasNext() {
            return this.f1260b < this.f1259a;
        }

        public final int hashCode() {
            int i = 0;
            if (this.f1261c) {
                Object a = this.f1262d.mo325a(this.f1260b, 0);
                Object a2 = this.f1262d.mo325a(this.f1260b, 1);
                int hashCode = a == null ? 0 : a.hashCode();
                if (a2 != null) {
                    i = a2.hashCode();
                }
                return i ^ hashCode;
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public /* synthetic */ Object next() {
            return m2028a();
        }

        public void remove() {
            if (this.f1261c) {
                this.f1262d.mo327a(this.f1260b);
                this.f1260b--;
                this.f1259a--;
                this.f1261c = false;
                return;
            }
            throw new IllegalStateException();
        }

        public V setValue(V v) {
            if (this.f1261c) {
                return this.f1262d.mo326a(this.f1260b, (Object) v);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /* renamed from: android.support.v4.f.h$e */
    final class C0476e implements Collection<V> {
        /* renamed from: a */
        final /* synthetic */ C0461h f1263a;

        C0476e(C0461h c0461h) {
            this.f1263a = c0461h;
        }

        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            this.f1263a.mo331c();
        }

        public boolean contains(Object obj) {
            return this.f1263a.mo329b(obj) >= 0;
        }

        public boolean containsAll(Collection<?> collection) {
            for (Object contains : collection) {
                if (!contains(contains)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isEmpty() {
            return this.f1263a.mo323a() == 0;
        }

        public Iterator<V> iterator() {
            return new C0472a(this.f1263a, 1);
        }

        public boolean remove(Object obj) {
            int b = this.f1263a.mo329b(obj);
            if (b < 0) {
                return false;
            }
            this.f1263a.mo327a(b);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            int i = 0;
            int a = this.f1263a.mo323a();
            boolean z = false;
            while (i < a) {
                if (collection.contains(this.f1263a.mo325a(i, 1))) {
                    this.f1263a.mo327a(i);
                    i--;
                    a--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        public boolean retainAll(Collection<?> collection) {
            int i = 0;
            int a = this.f1263a.mo323a();
            boolean z = false;
            while (i < a) {
                if (!collection.contains(this.f1263a.mo325a(i, 1))) {
                    this.f1263a.mo327a(i);
                    i--;
                    a--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        public int size() {
            return this.f1263a.mo323a();
        }

        public Object[] toArray() {
            return this.f1263a.m1963b(1);
        }

        public <T> T[] toArray(T[] tArr) {
            return this.f1263a.m1960a((Object[]) tArr, 1);
        }
    }

    C0461h() {
    }

    /* renamed from: a */
    public static <K, V> boolean m1950a(Map<K, V> map, Collection<?> collection) {
        for (Object containsKey : collection) {
            if (!map.containsKey(containsKey)) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: a */
    public static <T> boolean m1951a(Set<T> set, Object obj) {
        boolean z = true;
        if (set == obj) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set2 = (Set) obj;
        try {
            if (!(set.size() == set2.size() && set.containsAll(set2))) {
                z = false;
            }
            return z;
        } catch (NullPointerException e) {
            return false;
        } catch (ClassCastException e2) {
            return false;
        }
    }

    /* renamed from: b */
    public static <K, V> boolean m1952b(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        for (Object remove : collection) {
            map.remove(remove);
        }
        return size != map.size();
    }

    /* renamed from: c */
    public static <K, V> boolean m1953c(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return size != map.size();
    }

    /* renamed from: a */
    protected abstract int mo323a();

    /* renamed from: a */
    protected abstract int mo324a(Object obj);

    /* renamed from: a */
    protected abstract Object mo325a(int i, int i2);

    /* renamed from: a */
    protected abstract V mo326a(int i, V v);

    /* renamed from: a */
    protected abstract void mo327a(int i);

    /* renamed from: a */
    protected abstract void mo328a(K k, V v);

    /* renamed from: a */
    public <T> T[] m1960a(T[] tArr, int i) {
        int a = mo323a();
        T[] tArr2 = tArr.length < a ? (Object[]) Array.newInstance(tArr.getClass().getComponentType(), a) : tArr;
        for (int i2 = 0; i2 < a; i2++) {
            tArr2[i2] = mo325a(i2, i);
        }
        if (tArr2.length > a) {
            tArr2[a] = null;
        }
        return tArr2;
    }

    /* renamed from: b */
    protected abstract int mo329b(Object obj);

    /* renamed from: b */
    protected abstract Map<K, V> mo330b();

    /* renamed from: b */
    public Object[] m1963b(int i) {
        int a = mo323a();
        Object[] objArr = new Object[a];
        for (int i2 = 0; i2 < a; i2++) {
            objArr[i2] = mo325a(i2, i);
        }
        return objArr;
    }

    /* renamed from: c */
    protected abstract void mo331c();

    /* renamed from: d */
    public Set<Entry<K, V>> m1965d() {
        if (this.f1218b == null) {
            this.f1218b = new C0473b(this);
        }
        return this.f1218b;
    }

    /* renamed from: e */
    public Set<K> m1966e() {
        if (this.f1219c == null) {
            this.f1219c = new C0474c(this);
        }
        return this.f1219c;
    }

    /* renamed from: f */
    public Collection<V> m1967f() {
        if (this.f1220d == null) {
            this.f1220d = new C0476e(this);
        }
        return this.f1220d;
    }
}
