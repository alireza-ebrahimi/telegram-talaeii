package com.google.android.gms.internal.firebase_auth;

import java.util.Map.Entry;

final class zzdm<K> implements Entry<K, Object> {
    private Entry<K, zzdk> zzsl;

    private zzdm(Entry<K, zzdk> entry) {
        this.zzsl = entry;
    }

    public final K getKey() {
        return this.zzsl.getKey();
    }

    public final Object getValue() {
        return ((zzdk) this.zzsl.getValue()) == null ? null : zzdk.zzem();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzeh) {
            return ((zzdk) this.zzsl.getValue()).zzi((zzeh) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }

    public final zzdk zzen() {
        return (zzdk) this.zzsl.getValue();
    }
}
