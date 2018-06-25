package com.google.android.gms.internal.clearcut;

import java.util.Map.Entry;

final class zzct<K> implements Entry<K, Object> {
    private Entry<K, zzcr> zzll;

    private zzct(Entry<K, zzcr> entry) {
        this.zzll = entry;
    }

    public final K getKey() {
        return this.zzll.getKey();
    }

    public final Object getValue() {
        return ((zzcr) this.zzll.getValue()) == null ? null : zzcr.zzbr();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzdo) {
            return ((zzcr) this.zzll.getValue()).zzi((zzdo) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }

    public final zzcr zzbs() {
        return (zzcr) this.zzll.getValue();
    }
}
