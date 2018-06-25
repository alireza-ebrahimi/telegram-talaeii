package com.google.android.gms.internal;

final class zzckt implements Runnable {
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ zzcii zzjpl;

    zzckt(zzcko zzcko, zzcii zzcii) {
        this.zzjpk = zzcko;
        this.zzjpl = zzcii;
    }

    public final void run() {
        this.zzjpk.zzjev.zzbcc();
        this.zzjpk.zzjev.zzd(this.zzjpl);
    }
}
