package com.google.android.gms.internal.firebase_auth;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public final class zzfs extends AbstractList<String> implements zzdq, RandomAccess {
    private final zzdq zzvf;

    public zzfs(zzdq zzdq) {
        this.zzvf = zzdq;
    }

    public final /* synthetic */ Object get(int i) {
        return (String) this.zzvf.get(i);
    }

    public final Object getRaw(int i) {
        return this.zzvf.getRaw(i);
    }

    public final Iterator<String> iterator() {
        return new zzfu(this);
    }

    public final ListIterator<String> listIterator(int i) {
        return new zzft(this, i);
    }

    public final int size() {
        return this.zzvf.size();
    }

    public final void zzc(zzbu zzbu) {
        throw new UnsupportedOperationException();
    }

    public final List<?> zzeo() {
        return this.zzvf.zzeo();
    }

    public final zzdq zzep() {
        return this;
    }
}
