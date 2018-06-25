package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;

final class zzcnf extends zzcip {
    private /* synthetic */ zzcnd zzjse;

    zzcnf(zzcnd zzcnd, zzckj zzckj) {
        this.zzjse = zzcnd;
        super(zzckj);
    }

    @WorkerThread
    public final void run() {
        this.zzjse.zzbco();
    }
}
