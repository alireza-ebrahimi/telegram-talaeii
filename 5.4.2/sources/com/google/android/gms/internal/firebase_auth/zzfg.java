package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzfg implements Iterator<Entry<K, V>> {
    private int pos;
    private Iterator<Entry<K, V>> zzuq;
    private final /* synthetic */ zzey zzur;
    private boolean zzuv;

    private zzfg(zzey zzey) {
        this.zzur = zzey;
        this.pos = -1;
    }

    private final Iterator<Entry<K, V>> zzft() {
        if (this.zzuq == null) {
            this.zzuq = this.zzur.zzum.entrySet().iterator();
        }
        return this.zzuq;
    }

    public final boolean hasNext() {
        return this.pos + 1 < this.zzur.zzul.size() || (!this.zzur.zzum.isEmpty() && zzft().hasNext());
    }

    public final /* synthetic */ Object next() {
        this.zzuv = true;
        int i = this.pos + 1;
        this.pos = i;
        return i < this.zzur.zzul.size() ? (Entry) this.zzur.zzul.get(this.pos) : (Entry) zzft().next();
    }

    public final void remove() {
        if (this.zzuv) {
            this.zzuv = false;
            this.zzur.zzfr();
            if (this.pos < this.zzur.zzul.size()) {
                zzey zzey = this.zzur;
                int i = this.pos;
                this.pos = i - 1;
                zzey.zzav(i);
                return;
            }
            zzft().remove();
            return;
        }
        throw new IllegalStateException("remove() was called before next()");
    }
}
