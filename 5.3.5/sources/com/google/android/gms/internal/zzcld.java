package com.google.android.gms.internal;

final class zzcld implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ zzcnl zzjpp;

    zzcld(zzcko zzcko, zzcnl zzcnl, zzcif zzcif) {
        this.zzjpk = zzcko;
        this.zzjpp = zzcnl;
        this.zzjpj = zzcif;
    }

    public final void run() {
        this.zzjpk.zzjev.zzbcc();
        this.zzjpk.zzjev.zzb(this.zzjpp, this.zzjpj);
    }
}
