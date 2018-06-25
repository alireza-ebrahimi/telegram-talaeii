package com.google.p098a.p100b.p101a;

import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.C1771l;
import com.google.p098a.C1777q;
import com.google.p098a.C1778t;
import com.google.p098a.p100b.C1713b;
import com.google.p098a.p100b.C1714h;
import com.google.p098a.p100b.C1724c;
import com.google.p098a.p100b.C1727e;
import com.google.p098a.p100b.C1741j;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p102d.C1758b;
import com.google.p098a.p103c.C1748a;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: com.google.a.b.a.g */
public final class C1684g implements C1668x {
    /* renamed from: a */
    private final C1724c f5173a;
    /* renamed from: b */
    private final boolean f5174b;

    /* renamed from: com.google.a.b.a.g$a */
    private final class C1683a<K, V> extends C1670w<Map<K, V>> {
        /* renamed from: a */
        final /* synthetic */ C1684g f5169a;
        /* renamed from: b */
        private final C1670w<K> f5170b;
        /* renamed from: c */
        private final C1670w<V> f5171c;
        /* renamed from: d */
        private final C1714h<? extends Map<K, V>> f5172d;

        public C1683a(C1684g c1684g, C1768f c1768f, Type type, C1670w<K> c1670w, Type type2, C1670w<V> c1670w2, C1714h<? extends Map<K, V>> c1714h) {
            this.f5169a = c1684g;
            this.f5170b = new C1696l(c1768f, c1670w, type);
            this.f5171c = new C1696l(c1768f, c1670w2, type2);
            this.f5172d = c1714h;
        }

        /* renamed from: a */
        private String m8198a(C1771l c1771l) {
            if (c1771l.m8413i()) {
                C1777q m = c1771l.m8417m();
                if (m.m8440p()) {
                    return String.valueOf(m.mo1292a());
                }
                if (m.m8439o()) {
                    return Boolean.toString(m.mo1297f());
                }
                if (m.m8441q()) {
                    return m.mo1293b();
                }
                throw new AssertionError();
            } else if (c1771l.m8414j()) {
                return "null";
            } else {
                throw new AssertionError();
            }
        }

        /* renamed from: a */
        public Map<K, V> m8199a(C1678a c1678a) {
            C1758b f = c1678a.mo1262f();
            if (f == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            Map<K, V> map = (Map) this.f5172d.mo1288a();
            Object read;
            if (f == C1758b.BEGIN_ARRAY) {
                c1678a.mo1256a();
                while (c1678a.mo1261e()) {
                    c1678a.mo1256a();
                    read = this.f5170b.read(c1678a);
                    if (map.put(read, this.f5171c.read(c1678a)) != null) {
                        throw new C1778t("duplicate key: " + read);
                    }
                    c1678a.mo1257b();
                }
                c1678a.mo1257b();
                return map;
            }
            c1678a.mo1258c();
            while (c1678a.mo1261e()) {
                C1727e.f5300a.mo1291a(c1678a);
                read = this.f5170b.read(c1678a);
                if (map.put(read, this.f5171c.read(c1678a)) != null) {
                    throw new C1778t("duplicate key: " + read);
                }
            }
            c1678a.mo1260d();
            return map;
        }

        /* renamed from: a */
        public void m8200a(C1681c c1681c, Map<K, V> map) {
            int i = 0;
            if (map == null) {
                c1681c.mo1284f();
            } else if (this.f5169a.f5174b) {
                List arrayList = new ArrayList(map.size());
                List arrayList2 = new ArrayList(map.size());
                int i2 = 0;
                for (Entry entry : map.entrySet()) {
                    C1771l toJsonTree = this.f5170b.toJsonTree(entry.getKey());
                    arrayList.add(toJsonTree);
                    arrayList2.add(entry.getValue());
                    int i3 = (toJsonTree.m8411g() || toJsonTree.m8412h()) ? 1 : 0;
                    i2 = i3 | i2;
                }
                if (i2 != 0) {
                    c1681c.mo1278b();
                    while (i < arrayList.size()) {
                        c1681c.mo1278b();
                        C1741j.m8347a((C1771l) arrayList.get(i), c1681c);
                        this.f5171c.write(c1681c, arrayList2.get(i));
                        c1681c.mo1280c();
                        i++;
                    }
                    c1681c.mo1280c();
                    return;
                }
                c1681c.mo1282d();
                while (i < arrayList.size()) {
                    c1681c.mo1275a(m8198a((C1771l) arrayList.get(i)));
                    this.f5171c.write(c1681c, arrayList2.get(i));
                    i++;
                }
                c1681c.mo1283e();
            } else {
                c1681c.mo1282d();
                for (Entry entry2 : map.entrySet()) {
                    c1681c.mo1275a(String.valueOf(entry2.getKey()));
                    this.f5171c.write(c1681c, entry2.getValue());
                }
                c1681c.mo1283e();
            }
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8199a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8200a(c1681c, (Map) obj);
        }
    }

    public C1684g(C1724c c1724c, boolean z) {
        this.f5173a = c1724c;
        this.f5174b = z;
    }

    /* renamed from: a */
    private C1670w<?> m8201a(C1768f c1768f, Type type) {
        return (type == Boolean.TYPE || type == Boolean.class) ? C1708m.f5237f : c1768f.m8387a(C1748a.m8356a(type));
    }

    public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
        Type b = c1748a.m8360b();
        if (!Map.class.isAssignableFrom(c1748a.m8359a())) {
            return null;
        }
        Type[] b2 = C1713b.m8291b(b, C1713b.m8294e(b));
        C1670w a = m8201a(c1768f, b2[0]);
        C1670w a2 = c1768f.m8387a(C1748a.m8356a(b2[1]));
        C1714h a3 = this.f5173a.m8315a((C1748a) c1748a);
        return new C1683a(this, c1768f, b2[0], a, b2[1], a2, a3);
    }
}
