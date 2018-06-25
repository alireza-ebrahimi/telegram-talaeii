package com.google.p098a.p100b.p101a;

import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.p100b.C1713b;
import com.google.p098a.p100b.C1714h;
import com.google.p098a.p100b.C1724c;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p102d.C1758b;
import com.google.p098a.p103c.C1748a;
import java.lang.reflect.Type;
import java.util.Collection;

/* renamed from: com.google.a.b.a.b */
public final class C1673b implements C1668x {
    /* renamed from: a */
    private final C1724c f5128a;

    /* renamed from: com.google.a.b.a.b$a */
    private static final class C1672a<E> extends C1670w<Collection<E>> {
        /* renamed from: a */
        private final C1670w<E> f5126a;
        /* renamed from: b */
        private final C1714h<? extends Collection<E>> f5127b;

        public C1672a(C1768f c1768f, Type type, C1670w<E> c1670w, C1714h<? extends Collection<E>> c1714h) {
            this.f5126a = new C1696l(c1768f, c1670w, type);
            this.f5127b = c1714h;
        }

        /* renamed from: a */
        public Collection<E> m8092a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            Collection<E> collection = (Collection) this.f5127b.mo1288a();
            c1678a.mo1256a();
            while (c1678a.mo1261e()) {
                collection.add(this.f5126a.read(c1678a));
            }
            c1678a.mo1257b();
            return collection;
        }

        /* renamed from: a */
        public void m8093a(C1681c c1681c, Collection<E> collection) {
            if (collection == null) {
                c1681c.mo1284f();
                return;
            }
            c1681c.mo1278b();
            for (E write : collection) {
                this.f5126a.write(c1681c, write);
            }
            c1681c.mo1280c();
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8092a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8093a(c1681c, (Collection) obj);
        }
    }

    public C1673b(C1724c c1724c) {
        this.f5128a = c1724c;
    }

    public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
        Type b = c1748a.m8360b();
        Class a = c1748a.m8359a();
        if (!Collection.class.isAssignableFrom(a)) {
            return null;
        }
        Type a2 = C1713b.m8282a(b, a);
        return new C1672a(c1768f, a2, c1768f.m8387a(C1748a.m8356a(a2)), this.f5128a.m8315a((C1748a) c1748a));
    }
}
