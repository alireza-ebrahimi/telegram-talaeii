package com.google.android.gms.internal;

import android.content.Intent;

final /* synthetic */ class zzcmz implements Runnable {
    private final int zzdrw;
    private final zzcmy zzjrv;
    private final zzcjj zzjrw;
    private final Intent zzjrx;

    zzcmz(zzcmy zzcmy, int i, zzcjj zzcjj, Intent intent) {
        this.zzjrv = zzcmy;
        this.zzdrw = i;
        this.zzjrw = zzcjj;
        this.zzjrx = intent;
    }

    public final void run() {
        this.zzjrv.zza(this.zzdrw, this.zzjrw, this.zzjrx);
    }
}
