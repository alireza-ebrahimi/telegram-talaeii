package com.google.android.gms.internal.firebase_auth;

import java.util.ListIterator;

final class zzft implements ListIterator<String> {
    private final /* synthetic */ int val$index;
    private ListIterator<String> zzvg = this.zzvh.zzvf.listIterator(this.val$index);
    private final /* synthetic */ zzfs zzvh;

    zzft(zzfs zzfs, int i) {
        this.zzvh = zzfs;
        this.val$index = i;
    }

    public final /* synthetic */ void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final boolean hasNext() {
        return this.zzvg.hasNext();
    }

    public final boolean hasPrevious() {
        return this.zzvg.hasPrevious();
    }

    public final /* synthetic */ Object next() {
        return (String) this.zzvg.next();
    }

    public final int nextIndex() {
        return this.zzvg.nextIndex();
    }

    public final /* synthetic */ Object previous() {
        return (String) this.zzvg.previous();
    }

    public final int previousIndex() {
        return this.zzvg.previousIndex();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
