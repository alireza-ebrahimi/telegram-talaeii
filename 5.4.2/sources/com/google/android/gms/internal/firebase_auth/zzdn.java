package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzdn<K> implements Iterator<Entry<K, Object>> {
    private Iterator<Entry<K, Object>> zzsm;

    public zzdn(Iterator<Entry<K, Object>> it) {
        this.zzsm = it;
    }

    public final boolean hasNext() {
        return this.zzsm.hasNext();
    }

    public final /* synthetic */ Object next() {
        Entry entry = (Entry) this.zzsm.next();
        return entry.getValue() instanceof zzdk ? new zzdm(entry) : entry;
    }

    public final void remove() {
        this.zzsm.remove();
    }
}
