package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzedw<T> implements Iterator<T> {
    private Iterator<Entry<T, Void>> zzmyq;

    public zzedw(Iterator<Entry<T, Void>> it) {
        this.zzmyq = it;
    }

    public final boolean hasNext() {
        return this.zzmyq.hasNext();
    }

    public final T next() {
        return ((Entry) this.zzmyq.next()).getKey();
    }

    public final void remove() {
        this.zzmyq.remove();
    }
}
