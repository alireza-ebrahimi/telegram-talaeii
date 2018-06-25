package com.google.android.gms.internal;

import java.util.Comparator;

public final class zzedy<K, V> implements zzedz<K, V> {
    private static final zzedy zzmyr = new zzedy();

    private zzedy() {
    }

    public static <K, V> zzedy<K, V> zzbvx() {
        return zzmyr;
    }

    public final K getKey() {
        return null;
    }

    public final V getValue() {
        return null;
    }

    public final boolean isEmpty() {
        return true;
    }

    public final int size() {
        return 0;
    }

    public final zzedz<K, V> zza(K k, V v, Integer num, zzedz<K, V> zzedz, zzedz<K, V> zzedz2) {
        return this;
    }

    public final zzedz<K, V> zza(K k, V v, Comparator<K> comparator) {
        return new zzeec(k, v);
    }

    public final zzedz<K, V> zza(K k, Comparator<K> comparator) {
        return this;
    }

    public final void zza(zzeeb<K, V> zzeeb) {
    }

    public final boolean zzbvw() {
        return false;
    }

    public final zzedz<K, V> zzbvy() {
        return this;
    }

    public final zzedz<K, V> zzbvz() {
        return this;
    }

    public final zzedz<K, V> zzbwa() {
        return this;
    }

    public final zzedz<K, V> zzbwb() {
        return this;
    }
}
