package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class zzbv implements Iterator {
    private final int limit = this.zzml.size();
    private int position = 0;
    private final /* synthetic */ zzbu zzml;

    zzbv(zzbu zzbu) {
        this.zzml = zzbu;
    }

    private final byte nextByte() {
        try {
            zzbu zzbu = this.zzml;
            int i = this.position;
            this.position = i + 1;
            return zzbu.zzk(i);
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
