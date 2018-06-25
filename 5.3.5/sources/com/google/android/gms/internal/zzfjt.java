package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

final class zzfjt implements Iterator<zzfgy> {
    private final Stack<zzfjq> zzpry;
    private zzfgy zzprz;

    private zzfjt(zzfgs zzfgs) {
        this.zzpry = new Stack();
        this.zzprz = zzbc(zzfgs);
    }

    private final zzfgy zzbc(zzfgs zzfgs) {
        zzfgs zzfgs2 = zzfgs;
        while (zzfgs2 instanceof zzfjq) {
            zzfjq zzfjq = (zzfjq) zzfgs2;
            this.zzpry.push(zzfjq);
            zzfgs2 = zzfjq.zzprt;
        }
        return (zzfgy) zzfgs2;
    }

    private final zzfgy zzdbi() {
        while (!this.zzpry.isEmpty()) {
            zzfgs zzbc = zzbc(((zzfjq) this.zzpry.pop()).zzpru);
            if (!zzbc.isEmpty()) {
                return zzbc;
            }
        }
        return null;
    }

    public final boolean hasNext() {
        return this.zzprz != null;
    }

    public final /* synthetic */ Object next() {
        if (this.zzprz == null) {
            throw new NoSuchElementException();
        }
        zzfgy zzfgy = this.zzprz;
        this.zzprz = zzdbi();
        return zzfgy;
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
