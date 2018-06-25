package android.support.design.widget;

import android.support.v4.p022f.C0463k;
import android.support.v4.p022f.C0481j.C0478a;
import android.support.v4.p022f.C0481j.C0479b;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* renamed from: android.support.design.widget.f */
final class C0150f<T> {
    /* renamed from: a */
    private final C0478a<ArrayList<T>> f518a = new C0479b(10);
    /* renamed from: b */
    private final C0463k<T, ArrayList<T>> f519b = new C0463k();
    /* renamed from: c */
    private final ArrayList<T> f520c = new ArrayList();
    /* renamed from: d */
    private final HashSet<T> f521d = new HashSet();

    C0150f() {
    }

    /* renamed from: a */
    private void m733a(T t, ArrayList<T> arrayList, HashSet<T> hashSet) {
        if (!arrayList.contains(t)) {
            if (hashSet.contains(t)) {
                throw new RuntimeException("This graph contains cyclic dependencies");
            }
            hashSet.add(t);
            ArrayList arrayList2 = (ArrayList) this.f519b.get(t);
            if (arrayList2 != null) {
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    m733a(arrayList2.get(i), arrayList, hashSet);
                }
            }
            hashSet.remove(t);
            arrayList.add(t);
        }
    }

    /* renamed from: a */
    private void m734a(ArrayList<T> arrayList) {
        arrayList.clear();
        this.f518a.mo333a(arrayList);
    }

    /* renamed from: c */
    private ArrayList<T> m735c() {
        ArrayList<T> arrayList = (ArrayList) this.f518a.mo332a();
        return arrayList == null ? new ArrayList() : arrayList;
    }

    /* renamed from: a */
    void m736a() {
        int size = this.f519b.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) this.f519b.m1986c(i);
            if (arrayList != null) {
                m734a(arrayList);
            }
        }
        this.f519b.clear();
    }

    /* renamed from: a */
    void m737a(T t) {
        if (!this.f519b.containsKey(t)) {
            this.f519b.put(t, null);
        }
    }

    /* renamed from: a */
    void m738a(T t, T t2) {
        if (this.f519b.containsKey(t) && this.f519b.containsKey(t2)) {
            ArrayList arrayList = (ArrayList) this.f519b.get(t);
            if (arrayList == null) {
                arrayList = m735c();
                this.f519b.put(t, arrayList);
            }
            arrayList.add(t2);
            return;
        }
        throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
    }

    /* renamed from: b */
    ArrayList<T> m739b() {
        this.f520c.clear();
        this.f521d.clear();
        int size = this.f519b.size();
        for (int i = 0; i < size; i++) {
            m733a(this.f519b.m1985b(i), this.f520c, this.f521d);
        }
        return this.f520c;
    }

    /* renamed from: b */
    boolean m740b(T t) {
        return this.f519b.containsKey(t);
    }

    /* renamed from: c */
    List m741c(T t) {
        return (List) this.f519b.get(t);
    }

    /* renamed from: d */
    List m742d(T t) {
        List list = null;
        int size = this.f519b.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) this.f519b.m1986c(i);
            if (arrayList != null && arrayList.contains(t)) {
                if (list == null) {
                    arrayList = new ArrayList();
                } else {
                    List list2 = list;
                }
                arrayList.add(this.f519b.m1985b(i));
                list = arrayList;
            }
        }
        return list;
    }

    /* renamed from: e */
    boolean m743e(T t) {
        int size = this.f519b.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) this.f519b.m1986c(i);
            if (arrayList != null && arrayList.contains(t)) {
                return true;
            }
        }
        return false;
    }
}
