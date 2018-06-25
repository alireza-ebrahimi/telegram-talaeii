package com.google.android.gms.internal;

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Stack;

public final class zzedu<K, V> implements Iterator<Entry<K, V>> {
    private final Stack<zzeed<K, V>> zzmyn = new Stack();
    private final boolean zzmyo;

    zzedu(zzedz<K, V> zzedz, K k, Comparator<K> comparator, boolean z) {
        this.zzmyo = z;
        zzedz zzedz2 = zzedz;
        while (!zzedz2.isEmpty()) {
            int compare = k != null ? z ? comparator.compare(k, zzedz2.getKey()) : comparator.compare(zzedz2.getKey(), k) : 1;
            if (compare < 0) {
                if (!z) {
                    zzedz2 = zzedz2.zzbvz();
                }
            } else if (compare == 0) {
                this.zzmyn.push((zzeed) zzedz2);
                return;
            } else {
                this.zzmyn.push((zzeed) zzedz2);
                if (z) {
                    zzedz2 = zzedz2.zzbvz();
                }
            }
            zzedz2 = zzedz2.zzbvy();
        }
    }

    private final Entry<K, V> next() {
        try {
            zzeed zzeed = (zzeed) this.zzmyn.pop();
            Entry simpleEntry = new SimpleEntry(zzeed.getKey(), zzeed.getValue());
            zzedz zzbvy;
            if (this.zzmyo) {
                for (zzbvy = zzeed.zzbvy(); !zzbvy.isEmpty(); zzbvy = zzbvy.zzbvz()) {
                    this.zzmyn.push((zzeed) zzbvy);
                }
            } else {
                for (zzbvy = zzeed.zzbvz(); !zzbvy.isEmpty(); zzbvy = zzbvy.zzbvy()) {
                    this.zzmyn.push((zzeed) zzbvy);
                }
            }
            return simpleEntry;
        } catch (EmptyStackException e) {
            throw new NoSuchElementException();
        }
    }

    public final boolean hasNext() {
        return this.zzmyn.size() > 0;
    }

    public final void remove() {
        throw new UnsupportedOperationException("remove called on immutable collection");
    }
}
