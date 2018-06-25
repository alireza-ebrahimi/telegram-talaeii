package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzzz<K> implements Iterator<Entry<K, Object>> {
    private Iterator<Entry<K, Object>> zzbtf;

    public zzzz(Iterator<Entry<K, Object>> it) {
        this.zzbtf = it;
    }

    public final boolean hasNext() {
        return this.zzbtf.hasNext();
    }

    public final /* synthetic */ Object next() {
        Entry entry = (Entry) this.zzbtf.next();
        return entry.getValue() instanceof zzzw ? new zzzy(entry) : entry;
    }

    public final void remove() {
        this.zzbtf.remove();
    }
}
