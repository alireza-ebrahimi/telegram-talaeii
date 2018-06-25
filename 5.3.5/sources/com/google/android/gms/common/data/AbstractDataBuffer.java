package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.internal.Hide;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T> implements DataBuffer<T> {
    @Hide
    protected final DataHolder zzfxb;

    @Hide
    protected AbstractDataBuffer(DataHolder dataHolder) {
        this.zzfxb = dataHolder;
    }

    @Deprecated
    public final void close() {
        release();
    }

    public abstract T get(int i);

    public int getCount() {
        return this.zzfxb == null ? 0 : this.zzfxb.zzgcq;
    }

    @Deprecated
    public boolean isClosed() {
        return this.zzfxb == null || this.zzfxb.isClosed();
    }

    public Iterator<T> iterator() {
        return new zzb(this);
    }

    public void release() {
        if (this.zzfxb != null) {
            this.zzfxb.close();
        }
    }

    public Iterator<T> singleRefIterator() {
        return new zzh(this);
    }

    @Hide
    public final Bundle zzahs() {
        return this.zzfxb.zzahs();
    }
}
