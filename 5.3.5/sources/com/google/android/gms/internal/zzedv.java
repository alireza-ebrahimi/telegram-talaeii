package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class zzedv<T> implements Iterable<T> {
    private final zzedq<T, Void> zzmyp;

    private zzedv(zzedq<T, Void> zzedq) {
        this.zzmyp = zzedq;
    }

    public zzedv(List<T> list, Comparator<T> comparator) {
        this.zzmyp = zzedr.zzb(list, Collections.emptyMap(), zzedr.zzbvs(), comparator);
    }

    public final boolean contains(T t) {
        return this.zzmyp.containsKey(t);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzedv)) {
            return false;
        }
        return this.zzmyp.equals(((zzedv) obj).zzmyp);
    }

    public final int hashCode() {
        return this.zzmyp.hashCode();
    }

    public final int indexOf(T t) {
        return this.zzmyp.indexOf(t);
    }

    public final boolean isEmpty() {
        return this.zzmyp.isEmpty();
    }

    public final Iterator<T> iterator() {
        return new zzedw(this.zzmyp.iterator());
    }

    public final int size() {
        return this.zzmyp.size();
    }

    public final Iterator<T> zzbk(T t) {
        return new zzedw(this.zzmyp.zzbk(t));
    }

    public final zzedv<T> zzbp(T t) {
        zzedq zzbj = this.zzmyp.zzbj(t);
        return zzbj == this.zzmyp ? this : new zzedv(zzbj);
    }

    public final zzedv<T> zzbq(T t) {
        return new zzedv(this.zzmyp.zzg(t, null));
    }

    public final T zzbr(T t) {
        return this.zzmyp.zzbl(t);
    }

    public final Iterator<T> zzbvr() {
        return new zzedw(this.zzmyp.zzbvr());
    }

    public final T zzbvt() {
        return this.zzmyp.zzbvp();
    }

    public final T zzbvu() {
        return this.zzmyp.zzbvq();
    }
}
