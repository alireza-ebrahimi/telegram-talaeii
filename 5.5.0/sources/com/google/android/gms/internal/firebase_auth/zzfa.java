package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzfa implements Iterator<Entry<K, V>> {
    private int pos;
    private Iterator<Entry<K, V>> zzuq;
    private final /* synthetic */ zzey zzur;

    private zzfa(zzey zzey) {
        this.zzur = zzey;
        this.pos = this.zzur.zzul.size();
    }

    private final Iterator<Entry<K, V>> zzft() {
        if (this.zzuq == null) {
            this.zzuq = this.zzur.zzuo.entrySet().iterator();
        }
        return this.zzuq;
    }

    public final boolean hasNext() {
        return (this.pos > 0 && this.pos <= this.zzur.zzul.size()) || zzft().hasNext();
    }

    public final /* synthetic */ Object next() {
        if (zzft().hasNext()) {
            return (Entry) zzft().next();
        }
        List zzb = this.zzur.zzul;
        int i = this.pos - 1;
        this.pos = i;
        return (Entry) zzb.get(i);
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
