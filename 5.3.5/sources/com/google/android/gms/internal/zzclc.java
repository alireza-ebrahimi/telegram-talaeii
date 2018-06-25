package com.google.android.gms.internal;

final class zzclc implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ zzcnl zzjpp;

    zzclc(zzcko zzcko, zzcnl zzcnl, zzcif zzcif) {
        this.zzjpk = zzcko;
        this.zzjpp = zzcnl;
        this.zzjpj = zzcif;
    }

    public final void run() {
        this.zzjpk.zzjev.zzbcc();
        this.zzjpk.zzjev.zzc(this.zzjpp, this.zzjpj);
    }
}
