package com.google.p098a;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.google.a.i */
public final class C1772i extends C1771l implements Iterable<C1771l> {
    /* renamed from: a */
    private final List<C1771l> f5389a = new ArrayList();

    /* renamed from: a */
    public Number mo1292a() {
        if (this.f5389a.size() == 1) {
            return ((C1771l) this.f5389a.get(0)).mo1292a();
        }
        throw new IllegalStateException();
    }

    /* renamed from: a */
    public void m8420a(C1771l c1771l) {
        Object obj;
        if (c1771l == null) {
            obj = C1775n.f5390a;
        }
        this.f5389a.add(obj);
    }

    /* renamed from: b */
    public String mo1293b() {
        if (this.f5389a.size() == 1) {
            return ((C1771l) this.f5389a.get(0)).mo1293b();
        }
        throw new IllegalStateException();
    }

    /* renamed from: c */
    public double mo1294c() {
        if (this.f5389a.size() == 1) {
            return ((C1771l) this.f5389a.get(0)).mo1294c();
        }
        throw new IllegalStateException();
    }

    /* renamed from: d */
    public long mo1295d() {
        if (this.f5389a.size() == 1) {
            return ((C1771l) this.f5389a.get(0)).mo1295d();
        }
        throw new IllegalStateException();
    }

    /* renamed from: e */
    public int mo1296e() {
        if (this.f5389a.size() == 1) {
            return ((C1771l) this.f5389a.get(0)).mo1296e();
        }
        throw new IllegalStateException();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof C1772i) && ((C1772i) obj).f5389a.equals(this.f5389a));
    }

    /* renamed from: f */
    public boolean mo1297f() {
        if (this.f5389a.size() == 1) {
            return ((C1771l) this.f5389a.get(0)).mo1297f();
        }
        throw new IllegalStateException();
    }

    public int hashCode() {
        return this.f5389a.hashCode();
    }

    public Iterator<C1771l> iterator() {
        return this.f5389a.iterator();
    }
}
