package com.google.android.gms.internal;

final class zzcng implements Runnable {
    private /* synthetic */ long zzjfi;
    private /* synthetic */ zzcnd zzjse;

    zzcng(zzcnd zzcnd, long j) {
        this.zzjse = zzcnd;
        this.zzjfi = j;
    }

    public final void run() {
        this.zzjse.zzbe(this.zzjfi);
    }
}
