package com.google.android.gms.internal.firebase_auth;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

abstract class zzbq<E> extends AbstractList<E> implements zzdg<E> {
    private boolean zzmd = true;

    zzbq() {
    }

    public void add(int i, E e) {
        zzbt();
        super.add(i, e);
    }

    public boolean add(E e) {
        zzbt();
        return super.add(e);
    }

    public boolean addAll(int i, Collection<? extends E> collection) {
        zzbt();
        return super.addAll(i, collection);
    }

    public boolean addAll(Collection<? extends E> collection) {
        zzbt();
        return super.addAll(collection);
    }

    public void clear() {
        zzbt();
        super.clear();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        if (!(obj instanceof RandomAccess)) {
            return super.equals(obj);
        }
        List list = (List) obj;
        int size = size();
        if (size != list.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!get(i).equals(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < size(); i2++) {
            i = (i * 31) + get(i2).hashCode();
        }
        return i;
    }

    public E remove(int i) {
        zzbt();
        return super.remove(i);
    }

    public boolean remove(Object obj) {
        zzbt();
        return super.remove(obj);
    }

    public boolean removeAll(Collection<?> collection) {
        zzbt();
        return super.removeAll(collection);
    }

    public boolean retainAll(Collection<?> collection) {
        zzbt();
        return super.retainAll(collection);
    }

    public E set(int i, E e) {
        zzbt();
        return super.set(i, e);
    }

    public boolean zzbr() {
        return this.zzmd;
    }

    public final void zzbs() {
        this.zzmd = false;
    }

    protected final void zzbt() {
        if (!this.zzmd) {
            throw new UnsupportedOperationException();
        }
    }
}
