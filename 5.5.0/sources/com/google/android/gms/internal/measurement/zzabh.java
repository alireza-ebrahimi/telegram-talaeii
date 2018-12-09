package com.google.android.gms.internal.measurement;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;

final class zzabh extends AbstractSet<Entry<K, V>> {
    private final /* synthetic */ zzaba zzbup;

    private zzabh(zzaba zzaba) {
        this.zzbup = zzaba;
    }

    public final /* synthetic */ boolean add(Object obj) {
        Entry entry = (Entry) obj;
        if (contains(entry)) {
            return false;
        }
        this.zzbup.zza((Comparable) entry.getKey(), entry.getValue());
        return true;
    }

    public final void clear() {
        this.zzbup.clear();
    }

    public final boolean contains(Object obj) {
        Entry entry = (Entry) obj;
        Object obj2 = this.zzbup.get(entry.getKey());
        Object value = entry.getValue();
        return obj2 == value || (obj2 != null && obj2.equals(value));
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new zzabg(this.zzbup);
    }

    public final boolean remove(Object obj) {
        Entry entry = (Entry) obj;
        if (!contains(entry)) {
            return false;
        }
        this.zzbup.remove(entry.getKey());
        return true;
    }

    public final int size() {
        return this.zzbup.size();
    }
}
