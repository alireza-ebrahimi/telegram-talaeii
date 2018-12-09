package com.google.p098a;

import com.google.p098a.p100b.C1736g;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: com.google.a.o */
public final class C1776o extends C1771l {
    /* renamed from: a */
    private final C1736g<String, C1771l> f5391a = new C1736g();

    /* renamed from: a */
    public C1771l m8426a(String str) {
        return (C1771l) this.f5391a.get(str);
    }

    /* renamed from: a */
    public void m8427a(String str, C1771l c1771l) {
        Object obj;
        if (c1771l == null) {
            obj = C1775n.f5390a;
        }
        this.f5391a.put(str, obj);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof C1776o) && ((C1776o) obj).f5391a.equals(this.f5391a));
    }

    public int hashCode() {
        return this.f5391a.hashCode();
    }

    /* renamed from: o */
    public Set<Entry<String, C1771l>> m8428o() {
        return this.f5391a.entrySet();
    }
}
