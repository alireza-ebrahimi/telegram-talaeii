package com.google.android.gms.common.internal;

import android.support.v4.p022f.C0471g;

final class zze extends C0471g<K, V> {
    private final /* synthetic */ ExpirableLruCache zzss;

    zze(ExpirableLruCache expirableLruCache, int i) {
        this.zzss = expirableLruCache;
        super(i);
    }

    protected final V create(K k) {
        return this.zzss.create(k);
    }

    protected final void entryRemoved(boolean z, K k, V v, V v2) {
        this.zzss.entryRemoved(z, k, v, v2);
        synchronized (this.zzss.mLock) {
            if (v2 == null) {
                if (this.zzss.zzct()) {
                    this.zzss.zzsq.remove(k);
                }
            }
            if (v2 == null && this.zzss.zzcu()) {
                this.zzss.zzsr.remove(k);
            }
        }
    }

    protected final int sizeOf(K k, V v) {
        return this.zzss.sizeOf(k, v);
    }
}
