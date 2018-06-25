package com.google.android.gms.internal;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Iterator;
import java.util.Map.Entry;

final class zzedp implements Iterator<Entry<K, V>> {
    private int zzmyi = this.zzmyj;
    private /* synthetic */ int zzmyj;
    private /* synthetic */ boolean zzmyk;
    private /* synthetic */ zzedo zzmyl;

    zzedp(zzedo zzedo, int i, boolean z) {
        this.zzmyl = zzedo;
        this.zzmyj = i;
        this.zzmyk = z;
    }

    public final boolean hasNext() {
        return this.zzmyk ? this.zzmyi >= 0 : this.zzmyi < this.zzmyl.zzmav.length;
    }

    public final /* synthetic */ Object next() {
        Object obj = this.zzmyl.zzmav[this.zzmyi];
        Object obj2 = this.zzmyl.zzmyg[this.zzmyi];
        this.zzmyi = this.zzmyk ? this.zzmyi - 1 : this.zzmyi + 1;
        return new SimpleImmutableEntry(obj, obj2);
    }

    public final void remove() {
        throw new UnsupportedOperationException("Can't remove elements from ImmutableSortedMap");
    }
}
