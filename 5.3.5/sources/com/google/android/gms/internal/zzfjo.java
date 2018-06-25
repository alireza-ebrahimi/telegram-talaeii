package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;

final class zzfjo<E> extends zzfgn<E> {
    private static final zzfjo<Object> zzprp;
    private final List<E> zzprq;

    static {
        zzfgn zzfjo = new zzfjo();
        zzprp = zzfjo;
        zzfjo.zzbkr();
    }

    zzfjo() {
        this(new ArrayList(10));
    }

    private zzfjo(List<E> list) {
        this.zzprq = list;
    }

    public static <E> zzfjo<E> zzdbg() {
        return zzprp;
    }

    public final void add(int i, E e) {
        zzcxl();
        this.zzprq.add(i, e);
        this.modCount++;
    }

    public final E get(int i) {
        return this.zzprq.get(i);
    }

    public final E remove(int i) {
        zzcxl();
        E remove = this.zzprq.remove(i);
        this.modCount++;
        return remove;
    }

    public final E set(int i, E e) {
        zzcxl();
        E e2 = this.zzprq.set(i, e);
        this.modCount++;
        return e2;
    }

    public final int size() {
        return this.zzprq.size();
    }

    public final /* synthetic */ zzfid zzmo(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        List arrayList = new ArrayList(i);
        arrayList.addAll(this.zzprq);
        return new zzfjo(arrayList);
    }
}
