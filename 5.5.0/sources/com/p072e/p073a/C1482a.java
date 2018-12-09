package com.p072e.p073a;

import java.util.ArrayList;

/* renamed from: com.e.a.a */
public abstract class C1482a implements Cloneable {
    /* renamed from: a */
    ArrayList<C1481a> f4497a = null;

    /* renamed from: com.e.a.a$a */
    public interface C1481a {
        /* renamed from: a */
        void m7333a(C1482a c1482a);

        /* renamed from: b */
        void m7334b(C1482a c1482a);

        /* renamed from: c */
        void m7335c(C1482a c1482a);
    }

    /* renamed from: a */
    public void mo1188a() {
    }

    /* renamed from: b */
    public void mo1189b() {
    }

    /* renamed from: c */
    public C1482a mo1190c() {
        try {
            C1482a c1482a = (C1482a) super.clone();
            if (this.f4497a != null) {
                ArrayList arrayList = this.f4497a;
                c1482a.f4497a = new ArrayList();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    c1482a.f4497a.add(arrayList.get(i));
                }
            }
            return c1482a;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public /* synthetic */ Object clone() {
        return mo1190c();
    }
}
