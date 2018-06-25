package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;

final class zzfu implements Iterator<String> {
    private final /* synthetic */ zzfs zzvh;
    private Iterator<String> zzvi = this.zzvh.zzvf.iterator();

    zzfu(zzfs zzfs) {
        this.zzvh = zzfs;
    }

    public final boolean hasNext() {
        return this.zzvi.hasNext();
    }

    public final /* synthetic */ Object next() {
        return (String) this.zzvi.next();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
