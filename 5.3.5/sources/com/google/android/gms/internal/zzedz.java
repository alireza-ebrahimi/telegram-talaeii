package com.google.android.gms.internal;

import java.util.Comparator;

public interface zzedz<K, V> {
    K getKey();

    V getValue();

    boolean isEmpty();

    int size();

    zzedz<K, V> zza(K k, V v, Integer num, zzedz<K, V> zzedz, zzedz<K, V> zzedz2);

    zzedz<K, V> zza(K k, V v, Comparator<K> comparator);

    zzedz<K, V> zza(K k, Comparator<K> comparator);

    void zza(zzeeb<K, V> zzeeb);

    boolean zzbvw();

    zzedz<K, V> zzbvy();

    zzedz<K, V> zzbvz();

    zzedz<K, V> zzbwa();

    zzedz<K, V> zzbwb();
}
