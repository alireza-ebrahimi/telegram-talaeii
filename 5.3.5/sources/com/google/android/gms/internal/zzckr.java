package com.google.android.gms.internal;

final class zzckr implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ zzcii zzjpl;

    zzckr(zzcko zzcko, zzcii zzcii, zzcif zzcif) {
        this.zzjpk = zzcko;
        this.zzjpl = zzcii;
        this.zzjpj = zzcif;
    }

    public final void run() {
        this.zzjpk.zzjev.zzbcc();
        this.zzjpk.zzjev.zzb(this.zzjpl, this.zzjpj);
    }
}
