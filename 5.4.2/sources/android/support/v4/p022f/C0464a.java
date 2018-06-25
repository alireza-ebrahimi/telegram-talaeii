package android.support.v4.p022f;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: android.support.v4.f.a */
public class C0464a<K, V> extends C0463k<K, V> implements Map<K, V> {
    /* renamed from: a */
    C0461h<K, V> f1229a;

    /* renamed from: android.support.v4.f.a$1 */
    class C04621 extends C0461h<K, V> {
        /* renamed from: a */
        final /* synthetic */ C0464a f1221a;

        C04621(C0464a c0464a) {
            this.f1221a = c0464a;
        }

        /* renamed from: a */
        protected int mo323a() {
            return this.f1221a.h;
        }

        /* renamed from: a */
        protected int mo324a(Object obj) {
            return this.f1221a.m1980a(obj);
        }

        /* renamed from: a */
        protected Object mo325a(int i, int i2) {
            return this.f1221a.g[(i << 1) + i2];
        }

        /* renamed from: a */
        protected V mo326a(int i, V v) {
            return this.f1221a.m1982a(i, (Object) v);
        }

        /* renamed from: a */
        protected void mo327a(int i) {
            this.f1221a.m1987d(i);
        }

        /* renamed from: a */
        protected void mo328a(K k, V v) {
            this.f1221a.put(k, v);
        }

        /* renamed from: b */
        protected int mo329b(Object obj) {
            return this.f1221a.m1984b(obj);
        }

        /* renamed from: b */
        protected Map<K, V> mo330b() {
            return this.f1221a;
        }

        /* renamed from: c */
        protected void mo331c() {
            this.f1221a.clear();
        }
    }

    public C0464a(int i) {
        super(i);
    }

    /* renamed from: b */
    private C0461h<K, V> m1988b() {
        if (this.f1229a == null) {
            this.f1229a = new C04621(this);
        }
        return this.f1229a;
    }

    /* renamed from: a */
    public boolean m1989a(Collection<?> collection) {
        return C0461h.m1953c(this, collection);
    }

    public Set<Entry<K, V>> entrySet() {
        return m1988b().m1965d();
    }

    public Set<K> keySet() {
        return m1988b().m1966e();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        m1983a(this.h + map.size());
        for (Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public Collection<V> values() {
        return m1988b().m1967f();
    }
}
