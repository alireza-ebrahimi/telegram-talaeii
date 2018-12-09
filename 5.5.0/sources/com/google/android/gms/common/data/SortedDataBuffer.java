package com.google.android.gms.common.data;

import android.os.Bundle;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public final class SortedDataBuffer<T> implements DataBuffer<T> {
    private final DataBuffer<T> zzok;
    private final Integer[] zzol;

    public SortedDataBuffer(DataBuffer<T> dataBuffer, Comparator<T> comparator) {
        this.zzok = dataBuffer;
        this.zzol = new Integer[dataBuffer.getCount()];
        for (int i = 0; i < this.zzol.length; i++) {
            this.zzol[i] = Integer.valueOf(i);
        }
        Arrays.sort(this.zzol, new zzb(this, comparator));
    }

    public final void close() {
        this.zzok.release();
    }

    public final T get(int i) {
        return this.zzok.get(this.zzol[i].intValue());
    }

    public final int getCount() {
        return this.zzol.length;
    }

    public final Bundle getMetadata() {
        return this.zzok.getMetadata();
    }

    public final boolean isClosed() {
        return this.zzok.isClosed();
    }

    public final Iterator<T> iterator() {
        return new DataBufferIterator(this);
    }

    public final void release() {
        this.zzok.release();
    }

    public final Iterator<T> singleRefIterator() {
        return iterator();
    }
}
