package com.google.android.gms.internal.clearcut;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzcu<K> implements Iterator<Entry<K, Object>> {
    private Iterator<Entry<K, Object>> zzlm;

    public zzcu(Iterator<Entry<K, Object>> it) {
        this.zzlm = it;
    }

    public final boolean hasNext() {
        return this.zzlm.hasNext();
    }

    public final /* synthetic */ Object next() {
        Entry entry = (Entry) this.zzlm.next();
        return entry.getValue() instanceof zzcr ? new zzct(entry) : entry;
    }

    public final void remove() {
        this.zzlm.remove();
    }
}
