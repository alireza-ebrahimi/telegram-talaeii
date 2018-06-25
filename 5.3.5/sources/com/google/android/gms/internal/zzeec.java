package com.google.android.gms.internal;

public final class zzeec<K, V> extends zzeed<K, V> {
    zzeec(K k, V v) {
        super(k, v, zzedy.zzbvx(), zzedy.zzbvx());
    }

    zzeec(K k, V v, zzedz<K, V> zzedz, zzedz<K, V> zzedz2) {
        super(k, v, zzedz, zzedz2);
    }

    public final int size() {
        return (zzbvy().size() + 1) + zzbvz().size();
    }

    protected final zzeed<K, V> zza(K k, V v, zzedz<K, V> zzedz, zzedz<K, V> zzedz2) {
        Object key;
        Object value;
        zzedz zzbvy;
        zzedz zzbvz;
        if (k == null) {
            key = getKey();
        }
        if (v == null) {
            value = getValue();
        }
        if (zzedz == null) {
            zzbvy = zzbvy();
        }
        if (zzedz2 == null) {
            zzbvz = zzbvz();
        }
        return new zzeec(key, value, zzbvy, zzbvz);
    }

    protected final int zzbvv() {
        return zzeea.zzmys;
    }

    public final boolean zzbvw() {
        return true;
    }
}
