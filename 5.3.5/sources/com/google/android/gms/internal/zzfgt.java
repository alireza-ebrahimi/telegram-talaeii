package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class zzfgt implements Iterator {
    private final int limit = this.zzpny.size();
    private int position = 0;
    private /* synthetic */ zzfgs zzpny;

    zzfgt(zzfgs zzfgs) {
        this.zzpny = zzfgs;
    }

    private final byte nextByte() {
        try {
            zzfgs zzfgs = this.zzpny;
            int i = this.position;
            this.position = i + 1;
            return zzfgs.zzld(i);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public final boolean hasNext() {
        return this.position < this.limit;
    }

    public final /* synthetic */ Object next() {
        return Byte.valueOf(nextByte());
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
