package com.google.firebase.components;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.p106a.C1810c;
import com.google.firebase.p106a.C1811d;
import com.google.firebase.p108b.C1896a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/* renamed from: com.google.firebase.components.l */
public final class C1913l implements C1903b {
    /* renamed from: a */
    private final List<C1902a<?>> f5618a;
    /* renamed from: b */
    private final Map<Class<?>, C1918p<?>> f5619b = new HashMap();
    /* renamed from: c */
    private final C1916n f5620c;

    public C1913l(Executor executor, Iterable<C1816e> iterable, C1902a<?>... c1902aArr) {
        this.f5620c = new C1916n(executor);
        List arrayList = new ArrayList();
        arrayList.add(C1902a.m8707a(this.f5620c, C1916n.class, C1811d.class, C1810c.class));
        for (C1816e components : iterable) {
            arrayList.addAll(components.getComponents());
        }
        Collections.addAll(arrayList, c1902aArr);
        this.f5618a = Collections.unmodifiableList(C1915m.m8740a(arrayList));
        for (C1902a a : this.f5618a) {
            m8729a(a);
        }
        m8728a();
    }

    /* renamed from: a */
    private void m8728a() {
        for (C1902a b : this.f5618a) {
            for (C1905f c1905f : b.m8710b()) {
                if (c1905f.m8720b() && !this.f5619b.containsKey(c1905f.m8719a())) {
                    throw new C1908i(String.format("Unsatisfied dependency for component %s: %s", new Object[]{b, c1905f.m8719a()}));
                }
            }
        }
    }

    /* renamed from: a */
    private <T> void m8729a(C1902a<T> c1902a) {
        C1918p c1918p = new C1918p(c1902a.m8711c(), new C1921r(c1902a, this));
        for (Class put : c1902a.m8709a()) {
            this.f5619b.put(put, c1918p);
        }
    }

    /* renamed from: a */
    public final Object mo3042a(Class cls) {
        return C1904c.m8717a(this, cls);
    }

    /* renamed from: a */
    public final void m8731a(boolean z) {
        for (C1902a c1902a : this.f5618a) {
            if (c1902a.m8713e() || (c1902a.m8714f() && z)) {
                mo3042a((Class) c1902a.m8709a().iterator().next());
            }
        }
        this.f5620c.m8744a();
    }

    /* renamed from: b */
    public final <T> C1896a<T> mo3043b(Class<T> cls) {
        Preconditions.checkNotNull(cls, "Null interface requested.");
        return (C1896a) this.f5619b.get(cls);
    }
}
