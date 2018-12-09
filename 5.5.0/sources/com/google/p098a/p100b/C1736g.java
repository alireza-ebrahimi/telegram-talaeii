package com.google.p098a.p100b;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

/* renamed from: com.google.a.b.g */
public final class C1736g<K, V> extends AbstractMap<K, V> implements Serializable {
    /* renamed from: f */
    static final /* synthetic */ boolean f5318f = (!C1736g.class.desiredAssertionStatus());
    /* renamed from: g */
    private static final Comparator<Comparable> f5319g = new C17291();
    /* renamed from: a */
    Comparator<? super K> f5320a;
    /* renamed from: b */
    C1735d<K, V> f5321b;
    /* renamed from: c */
    int f5322c;
    /* renamed from: d */
    int f5323d;
    /* renamed from: e */
    final C1735d<K, V> f5324e;
    /* renamed from: h */
    private C1732a f5325h;
    /* renamed from: i */
    private C1734b f5326i;

    /* renamed from: com.google.a.b.g$1 */
    static class C17291 implements Comparator<Comparable> {
        C17291() {
        }

        /* renamed from: a */
        public int m8327a(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m8327a((Comparable) obj, (Comparable) obj2);
        }
    }

    /* renamed from: com.google.a.b.g$c */
    private abstract class C1730c<T> implements Iterator<T> {
        /* renamed from: b */
        C1735d<K, V> f5302b;
        /* renamed from: c */
        C1735d<K, V> f5303c;
        /* renamed from: d */
        int f5304d;
        /* renamed from: e */
        final /* synthetic */ C1736g f5305e;

        private C1730c(C1736g c1736g) {
            this.f5305e = c1736g;
            this.f5302b = this.f5305e.f5324e.f5313d;
            this.f5303c = null;
            this.f5304d = this.f5305e.f5323d;
        }

        /* renamed from: b */
        final C1735d<K, V> m8328b() {
            C1735d<K, V> c1735d = this.f5302b;
            if (c1735d == this.f5305e.f5324e) {
                throw new NoSuchElementException();
            } else if (this.f5305e.f5323d != this.f5304d) {
                throw new ConcurrentModificationException();
            } else {
                this.f5302b = c1735d.f5313d;
                this.f5303c = c1735d;
                return c1735d;
            }
        }

        public final boolean hasNext() {
            return this.f5302b != this.f5305e.f5324e;
        }

        public final void remove() {
            if (this.f5303c == null) {
                throw new IllegalStateException();
            }
            this.f5305e.m8340a(this.f5303c, true);
            this.f5303c = null;
            this.f5304d = this.f5305e.f5323d;
        }
    }

    /* renamed from: com.google.a.b.g$a */
    class C1732a extends AbstractSet<Entry<K, V>> {
        /* renamed from: a */
        final /* synthetic */ C1736g f5307a;

        /* renamed from: com.google.a.b.g$a$1 */
        class C17311 extends C1730c<Entry<K, V>> {
            /* renamed from: a */
            final /* synthetic */ C1732a f5306a;

            C17311(C1732a c1732a) {
                this.f5306a = c1732a;
                super();
            }

            /* renamed from: a */
            public Entry<K, V> m8329a() {
                return m8328b();
            }

            public /* synthetic */ Object next() {
                return m8329a();
            }
        }

        C1732a(C1736g c1736g) {
            this.f5307a = c1736g;
        }

        public void clear() {
            this.f5307a.clear();
        }

        public boolean contains(Object obj) {
            return (obj instanceof Entry) && this.f5307a.m8339a((Entry) obj) != null;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new C17311(this);
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            C1735d a = this.f5307a.m8339a((Entry) obj);
            if (a == null) {
                return false;
            }
            this.f5307a.m8340a(a, true);
            return true;
        }

        public int size() {
            return this.f5307a.f5322c;
        }
    }

    /* renamed from: com.google.a.b.g$b */
    final class C1734b extends AbstractSet<K> {
        /* renamed from: a */
        final /* synthetic */ C1736g f5309a;

        /* renamed from: com.google.a.b.g$b$1 */
        class C17331 extends C1730c<K> {
            /* renamed from: a */
            final /* synthetic */ C1734b f5308a;

            C17331(C1734b c1734b) {
                this.f5308a = c1734b;
                super();
            }

            public K next() {
                return m8328b().f5315f;
            }
        }

        C1734b(C1736g c1736g) {
            this.f5309a = c1736g;
        }

        public void clear() {
            this.f5309a.clear();
        }

        public boolean contains(Object obj) {
            return this.f5309a.containsKey(obj);
        }

        public Iterator<K> iterator() {
            return new C17331(this);
        }

        public boolean remove(Object obj) {
            return this.f5309a.m8341b(obj) != null;
        }

        public int size() {
            return this.f5309a.f5322c;
        }
    }

    /* renamed from: com.google.a.b.g$d */
    static final class C1735d<K, V> implements Entry<K, V> {
        /* renamed from: a */
        C1735d<K, V> f5310a;
        /* renamed from: b */
        C1735d<K, V> f5311b;
        /* renamed from: c */
        C1735d<K, V> f5312c;
        /* renamed from: d */
        C1735d<K, V> f5313d;
        /* renamed from: e */
        C1735d<K, V> f5314e;
        /* renamed from: f */
        final K f5315f;
        /* renamed from: g */
        V f5316g;
        /* renamed from: h */
        int f5317h;

        C1735d() {
            this.f5315f = null;
            this.f5314e = this;
            this.f5313d = this;
        }

        C1735d(C1735d<K, V> c1735d, K k, C1735d<K, V> c1735d2, C1735d<K, V> c1735d3) {
            this.f5310a = c1735d;
            this.f5315f = k;
            this.f5317h = 1;
            this.f5313d = c1735d2;
            this.f5314e = c1735d3;
            c1735d3.f5313d = this;
            c1735d2.f5314e = this;
        }

        /* renamed from: a */
        public C1735d<K, V> m8330a() {
            C1735d<K, V> c1735d;
            for (C1735d<K, V> c1735d2 = this.f5311b; c1735d2 != null; c1735d2 = c1735d2.f5311b) {
                c1735d = c1735d2;
            }
            return c1735d;
        }

        /* renamed from: b */
        public C1735d<K, V> m8331b() {
            C1735d<K, V> c1735d;
            for (C1735d<K, V> c1735d2 = this.f5312c; c1735d2 != null; c1735d2 = c1735d2.f5312c) {
                c1735d = c1735d2;
            }
            return c1735d;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (this.f5315f == null) {
                if (entry.getKey() != null) {
                    return false;
                }
            } else if (!this.f5315f.equals(entry.getKey())) {
                return false;
            }
            if (this.f5316g == null) {
                if (entry.getValue() != null) {
                    return false;
                }
            } else if (!this.f5316g.equals(entry.getValue())) {
                return false;
            }
            return true;
        }

        public K getKey() {
            return this.f5315f;
        }

        public V getValue() {
            return this.f5316g;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = this.f5315f == null ? 0 : this.f5315f.hashCode();
            if (this.f5316g != null) {
                i = this.f5316g.hashCode();
            }
            return hashCode ^ i;
        }

        public V setValue(V v) {
            V v2 = this.f5316g;
            this.f5316g = v;
            return v2;
        }

        public String toString() {
            return this.f5315f + "=" + this.f5316g;
        }
    }

    public C1736g() {
        this(f5319g);
    }

    public C1736g(Comparator<? super K> comparator) {
        Comparator comparator2;
        this.f5322c = 0;
        this.f5323d = 0;
        this.f5324e = new C1735d();
        if (comparator == null) {
            comparator2 = f5319g;
        }
        this.f5320a = comparator2;
    }

    /* renamed from: a */
    private void m8332a(C1735d<K, V> c1735d) {
        int i = 0;
        C1735d c1735d2 = c1735d.f5311b;
        C1735d c1735d3 = c1735d.f5312c;
        C1735d c1735d4 = c1735d3.f5311b;
        C1735d c1735d5 = c1735d3.f5312c;
        c1735d.f5312c = c1735d4;
        if (c1735d4 != null) {
            c1735d4.f5310a = c1735d;
        }
        m8333a((C1735d) c1735d, c1735d3);
        c1735d3.f5311b = c1735d;
        c1735d.f5310a = c1735d3;
        c1735d.f5317h = Math.max(c1735d2 != null ? c1735d2.f5317h : 0, c1735d4 != null ? c1735d4.f5317h : 0) + 1;
        int i2 = c1735d.f5317h;
        if (c1735d5 != null) {
            i = c1735d5.f5317h;
        }
        c1735d3.f5317h = Math.max(i2, i) + 1;
    }

    /* renamed from: a */
    private void m8333a(C1735d<K, V> c1735d, C1735d<K, V> c1735d2) {
        C1735d c1735d3 = c1735d.f5310a;
        c1735d.f5310a = null;
        if (c1735d2 != null) {
            c1735d2.f5310a = c1735d3;
        }
        if (c1735d3 == null) {
            this.f5321b = c1735d2;
        } else if (c1735d3.f5311b == c1735d) {
            c1735d3.f5311b = c1735d2;
        } else if (f5318f || c1735d3.f5312c == c1735d) {
            c1735d3.f5312c = c1735d2;
        } else {
            throw new AssertionError();
        }
    }

    /* renamed from: a */
    private boolean m8334a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    /* renamed from: b */
    private void m8335b(C1735d<K, V> c1735d) {
        int i = 0;
        C1735d c1735d2 = c1735d.f5311b;
        C1735d c1735d3 = c1735d.f5312c;
        C1735d c1735d4 = c1735d2.f5311b;
        C1735d c1735d5 = c1735d2.f5312c;
        c1735d.f5311b = c1735d5;
        if (c1735d5 != null) {
            c1735d5.f5310a = c1735d;
        }
        m8333a((C1735d) c1735d, c1735d2);
        c1735d2.f5312c = c1735d;
        c1735d.f5310a = c1735d2;
        c1735d.f5317h = Math.max(c1735d3 != null ? c1735d3.f5317h : 0, c1735d5 != null ? c1735d5.f5317h : 0) + 1;
        int i2 = c1735d.f5317h;
        if (c1735d4 != null) {
            i = c1735d4.f5317h;
        }
        c1735d2.f5317h = Math.max(i2, i) + 1;
    }

    /* renamed from: b */
    private void m8336b(C1735d<K, V> c1735d, boolean z) {
        C1735d c1735d2;
        while (c1735d2 != null) {
            C1735d c1735d3 = c1735d2.f5311b;
            C1735d c1735d4 = c1735d2.f5312c;
            int i = c1735d3 != null ? c1735d3.f5317h : 0;
            int i2 = c1735d4 != null ? c1735d4.f5317h : 0;
            int i3 = i - i2;
            C1735d c1735d5;
            if (i3 == -2) {
                c1735d3 = c1735d4.f5311b;
                c1735d5 = c1735d4.f5312c;
                i2 = (c1735d3 != null ? c1735d3.f5317h : 0) - (c1735d5 != null ? c1735d5.f5317h : 0);
                if (i2 == -1 || (i2 == 0 && !z)) {
                    m8332a(c1735d2);
                } else if (f5318f || i2 == 1) {
                    m8335b(c1735d4);
                    m8332a(c1735d2);
                } else {
                    throw new AssertionError();
                }
                if (z) {
                    return;
                }
            } else if (i3 == 2) {
                c1735d4 = c1735d3.f5311b;
                c1735d5 = c1735d3.f5312c;
                i2 = (c1735d4 != null ? c1735d4.f5317h : 0) - (c1735d5 != null ? c1735d5.f5317h : 0);
                if (i2 == 1 || (i2 == 0 && !z)) {
                    m8335b(c1735d2);
                } else if (f5318f || i2 == -1) {
                    m8332a(c1735d3);
                    m8335b(c1735d2);
                } else {
                    throw new AssertionError();
                }
                if (z) {
                    return;
                }
            } else if (i3 == 0) {
                c1735d2.f5317h = i + 1;
                if (z) {
                    return;
                }
            } else if (f5318f || i3 == -1 || i3 == 1) {
                c1735d2.f5317h = Math.max(i, i2) + 1;
                if (!z) {
                    return;
                }
            } else {
                throw new AssertionError();
            }
            c1735d2 = c1735d2.f5310a;
        }
    }

    /* renamed from: a */
    C1735d<K, V> m8337a(Object obj) {
        C1735d<K, V> c1735d = null;
        if (obj != null) {
            try {
                c1735d = m8338a(obj, false);
            } catch (ClassCastException e) {
            }
        }
        return c1735d;
    }

    /* renamed from: a */
    C1735d<K, V> m8338a(K k, boolean z) {
        int i;
        Comparator comparator = this.f5320a;
        C1735d<K, V> c1735d = this.f5321b;
        if (c1735d != null) {
            int compareTo;
            Comparable comparable = comparator == f5319g ? (Comparable) k : null;
            while (true) {
                compareTo = comparable != null ? comparable.compareTo(c1735d.f5315f) : comparator.compare(k, c1735d.f5315f);
                if (compareTo == 0) {
                    return c1735d;
                }
                C1735d<K, V> c1735d2 = compareTo < 0 ? c1735d.f5311b : c1735d.f5312c;
                if (c1735d2 == null) {
                    break;
                }
                c1735d = c1735d2;
            }
            int i2 = compareTo;
            C1735d c1735d3 = c1735d;
            i = i2;
        } else {
            C1735d<K, V> c1735d4 = c1735d;
            i = 0;
        }
        if (!z) {
            return null;
        }
        C1735d<K, V> c1735d5;
        C1735d c1735d6 = this.f5324e;
        if (c1735d3 != null) {
            c1735d5 = new C1735d(c1735d3, k, c1735d6, c1735d6.f5314e);
            if (i < 0) {
                c1735d3.f5311b = c1735d5;
            } else {
                c1735d3.f5312c = c1735d5;
            }
            m8336b(c1735d3, true);
        } else if (comparator != f5319g || (k instanceof Comparable)) {
            c1735d5 = new C1735d(c1735d3, k, c1735d6, c1735d6.f5314e);
            this.f5321b = c1735d5;
        } else {
            throw new ClassCastException(k.getClass().getName() + " is not Comparable");
        }
        this.f5322c++;
        this.f5323d++;
        return c1735d5;
    }

    /* renamed from: a */
    C1735d<K, V> m8339a(Entry<?, ?> entry) {
        C1735d<K, V> a = m8337a(entry.getKey());
        Object obj = (a == null || !m8334a(a.f5316g, entry.getValue())) ? null : 1;
        return obj != null ? a : null;
    }

    /* renamed from: a */
    void m8340a(C1735d<K, V> c1735d, boolean z) {
        int i = 0;
        if (z) {
            c1735d.f5314e.f5313d = c1735d.f5313d;
            c1735d.f5313d.f5314e = c1735d.f5314e;
        }
        C1735d c1735d2 = c1735d.f5311b;
        C1735d c1735d3 = c1735d.f5312c;
        C1735d c1735d4 = c1735d.f5310a;
        if (c1735d2 == null || c1735d3 == null) {
            if (c1735d2 != null) {
                m8333a((C1735d) c1735d, c1735d2);
                c1735d.f5311b = null;
            } else if (c1735d3 != null) {
                m8333a((C1735d) c1735d, c1735d3);
                c1735d.f5312c = null;
            } else {
                m8333a((C1735d) c1735d, null);
            }
            m8336b(c1735d4, false);
            this.f5322c--;
            this.f5323d++;
            return;
        }
        int i2;
        c1735d2 = c1735d2.f5317h > c1735d3.f5317h ? c1735d2.m8331b() : c1735d3.m8330a();
        m8340a(c1735d2, false);
        c1735d4 = c1735d.f5311b;
        if (c1735d4 != null) {
            i2 = c1735d4.f5317h;
            c1735d2.f5311b = c1735d4;
            c1735d4.f5310a = c1735d2;
            c1735d.f5311b = null;
        } else {
            i2 = 0;
        }
        c1735d4 = c1735d.f5312c;
        if (c1735d4 != null) {
            i = c1735d4.f5317h;
            c1735d2.f5312c = c1735d4;
            c1735d4.f5310a = c1735d2;
            c1735d.f5312c = null;
        }
        c1735d2.f5317h = Math.max(i2, i) + 1;
        m8333a((C1735d) c1735d, c1735d2);
    }

    /* renamed from: b */
    C1735d<K, V> m8341b(Object obj) {
        C1735d a = m8337a(obj);
        if (a != null) {
            m8340a(a, true);
        }
        return a;
    }

    public void clear() {
        this.f5321b = null;
        this.f5322c = 0;
        this.f5323d++;
        C1735d c1735d = this.f5324e;
        c1735d.f5314e = c1735d;
        c1735d.f5313d = c1735d;
    }

    public boolean containsKey(Object obj) {
        return m8337a(obj) != null;
    }

    public Set<Entry<K, V>> entrySet() {
        Set set = this.f5325h;
        if (set != null) {
            return set;
        }
        set = new C1732a(this);
        this.f5325h = set;
        return set;
    }

    public V get(Object obj) {
        C1735d a = m8337a(obj);
        return a != null ? a.f5316g : null;
    }

    public Set<K> keySet() {
        Set set = this.f5326i;
        if (set != null) {
            return set;
        }
        set = new C1734b(this);
        this.f5326i = set;
        return set;
    }

    public V put(K k, V v) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        C1735d a = m8338a((Object) k, true);
        V v2 = a.f5316g;
        a.f5316g = v;
        return v2;
    }

    public V remove(Object obj) {
        C1735d b = m8341b(obj);
        return b != null ? b.f5316g : null;
    }

    public int size() {
        return this.f5322c;
    }
}
