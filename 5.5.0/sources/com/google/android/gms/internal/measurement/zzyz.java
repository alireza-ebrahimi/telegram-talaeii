package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class zzyz implements Iterator {
    private final int limit = this.zzbrj.size();
    private int position = 0;
    private final /* synthetic */ zzyy zzbrj;

    zzyz(zzyy zzyy) {
        this.zzbrj = zzyy;
    }

    private final byte nextByte() {
        try {
            zzyy zzyy = this.zzbrj;
            int i = this.position;
            this.position = i + 1;
            return zzyy.zzae(i);
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
