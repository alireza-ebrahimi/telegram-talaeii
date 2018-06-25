package com.google.android.gms.common.api;

import android.os.Bundle;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.internal.Hide;
import java.util.Iterator;

@Hide
public class zzb<T, R extends AbstractDataBuffer<T> & Result> extends Response<R> implements DataBuffer<T> {
    public void close() {
        ((AbstractDataBuffer) getResult()).close();
    }

    public T get(int i) {
        return ((AbstractDataBuffer) getResult()).get(i);
    }

    public int getCount() {
        return ((AbstractDataBuffer) getResult()).getCount();
    }

    public boolean isClosed() {
        return ((AbstractDataBuffer) getResult()).isClosed();
    }

    public Iterator<T> iterator() {
        return ((AbstractDataBuffer) getResult()).iterator();
    }

    public void release() {
        ((AbstractDataBuffer) getResult()).release();
    }

    public Iterator<T> singleRefIterator() {
        return ((AbstractDataBuffer) getResult()).singleRefIterator();
    }

    public final Bundle zzahs() {
        return ((AbstractDataBuffer) getResult()).zzahs();
    }
}
