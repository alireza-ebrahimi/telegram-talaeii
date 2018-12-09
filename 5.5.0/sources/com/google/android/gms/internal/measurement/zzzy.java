package com.google.android.gms.internal.measurement;

import java.util.Map.Entry;

final class zzzy<K> implements Entry<K, Object> {
    private Entry<K, zzzw> zzbte;

    private zzzy(Entry<K, zzzw> entry) {
        this.zzbte = entry;
    }

    public final K getKey() {
        return this.zzbte.getKey();
    }

    public final Object getValue() {
        return ((zzzw) this.zzbte.getValue()) == null ? null : zzzw.zztx();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzaan) {
            return ((zzzw) this.zzbte.getValue()).zzc((zzaan) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }
}
