package com.google.android.gms.internal;

final class zzckq implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ zzcii zzjpl;

    zzckq(zzcko zzcko, zzcii zzcii, zzcif zzcif) {
        this.zzjpk = zzcko;
        this.zzjpl = zzcii;
        this.zzjpj = zzcif;
    }

    public final void run() {
        this.zzjpk.zzjev.zzbcc();
        this.zzjpk.zzjev.zzc(this.zzjpl, this.zzjpj);
    }
}
