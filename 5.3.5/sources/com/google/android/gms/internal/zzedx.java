package com.google.android.gms.internal;

public final class zzedx<K, V> extends zzeed<K, V> {
    private int size = -1;

    zzedx(K k, V v, zzedz<K, V> zzedz, zzedz<K, V> zzedz2) {
        super(k, v, zzedz, zzedz2);
    }

    public final int size() {
        if (this.size == -1) {
            this.size = (zzbvy().size() + 1) + zzbvz().size();
        }
        return this.size;
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
        return new zzedx(key, value, zzbvy, zzbvz);
    }

    final void zza(zzedz<K, V> zzedz) {
        if (this.size != -1) {
            throw new IllegalStateException("Can't set left after using size");
        }
        super.zza((zzedz) zzedz);
    }

    protected final int zzbvv() {
        return zzeea.zzmyt;
    }

    public final boolean zzbvw() {
        return false;
    }
}
