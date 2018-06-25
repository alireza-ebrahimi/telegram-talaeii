package com.p057c.p058a.p063b;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* renamed from: com.c.a.b.e */
public class C1319e<E> extends AbstractList<E> {
    /* renamed from: c */
    private static final C1313f f3982c = C1313f.m6751a(C1319e.class);
    /* renamed from: a */
    List<E> f3983a;
    /* renamed from: b */
    Iterator<E> f3984b;

    /* renamed from: com.c.a.b.e$1 */
    class C13181 implements Iterator<E> {
        /* renamed from: a */
        int f3980a = 0;
        /* renamed from: b */
        final /* synthetic */ C1319e f3981b;

        C13181(C1319e c1319e) {
            this.f3981b = c1319e;
        }

        public boolean hasNext() {
            return this.f3980a < this.f3981b.f3983a.size() || this.f3981b.f3984b.hasNext();
        }

        public E next() {
            if (this.f3980a < this.f3981b.f3983a.size()) {
                List list = this.f3981b.f3983a;
                int i = this.f3980a;
                this.f3980a = i + 1;
                return list.get(i);
            }
            this.f3981b.f3983a.add(this.f3981b.f3984b.next());
            return next();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public C1319e(List<E> list, Iterator<E> it) {
        this.f3983a = list;
        this.f3984b = it;
    }

    /* renamed from: a */
    private void m6761a() {
        f3982c.mo1122a("blowup running");
        while (this.f3984b.hasNext()) {
            this.f3983a.add(this.f3984b.next());
        }
    }

    public E get(int i) {
        if (this.f3983a.size() > i) {
            return this.f3983a.get(i);
        }
        if (this.f3984b.hasNext()) {
            this.f3983a.add(this.f3984b.next());
            return get(i);
        }
        throw new NoSuchElementException();
    }

    public Iterator<E> iterator() {
        return new C13181(this);
    }

    public int size() {
        f3982c.mo1122a("potentially expensive size() call");
        m6761a();
        return this.f3983a.size();
    }
}
