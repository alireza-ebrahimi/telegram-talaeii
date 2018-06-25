package com.google.android.gms.internal;

final class zzcnh implements Runnable {
    private /* synthetic */ long zzjfi;
    private /* synthetic */ zzcnd zzjse;

    zzcnh(zzcnd zzcnd, long j) {
        this.zzjse = zzcnd;
        this.zzjfi = j;
    }

    public final void run() {
        this.zzjse.zzbf(this.zzjfi);
    }
}
