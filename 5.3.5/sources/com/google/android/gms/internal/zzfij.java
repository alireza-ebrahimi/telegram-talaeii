package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzfij<K> implements Iterator<Entry<K, Object>> {
    private Iterator<Entry<K, Object>> zzmyq;

    public zzfij(Iterator<Entry<K, Object>> it) {
        this.zzmyq = it;
    }

    public final boolean hasNext() {
        return this.zzmyq.hasNext();
    }

    public final /* synthetic */ Object next() {
        Entry entry = (Entry) this.zzmyq.next();
        return entry.getValue() instanceof zzfig ? new zzfii(entry) : entry;
    }

    public final void remove() {
        this.zzmyq.remove();
    }
}
