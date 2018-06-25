package com.google.android.gms.internal;

import java.util.Map.Entry;

final class zzfii<K> implements Entry<K, Object> {
    private Entry<K, zzfig> zzpqp;

    private zzfii(Entry<K, zzfig> entry) {
        this.zzpqp = entry;
    }

    public final K getKey() {
        return this.zzpqp.getKey();
    }

    public final Object getValue() {
        return ((zzfig) this.zzpqp.getValue()) == null ? null : zzfig.zzdan();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzfjc) {
            return ((zzfig) this.zzpqp.getValue()).zzk((zzfjc) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }

    public final zzfig zzdao() {
        return (zzfig) this.zzpqp.getValue();
    }
}
