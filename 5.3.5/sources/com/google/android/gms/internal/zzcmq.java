package com.google.android.gms.internal;

final class zzcmq implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcnl zzjpp;
    private /* synthetic */ zzcme zzjri;
    private /* synthetic */ boolean zzjrm;

    zzcmq(zzcme zzcme, boolean z, zzcnl zzcnl, zzcif zzcif) {
        this.zzjri = zzcme;
        this.zzjrm = z;
        this.zzjpp = zzcnl;
        this.zzjpj = zzcif;
    }

    public final void run() {
        zzcjb zzd = this.zzjri.zzjrc;
        if (zzd == null) {
            this.zzjri.zzayp().zzbau().log("Discarding data. Failed to set user attribute");
            return;
        }
        this.zzjri.zza(zzd, this.zzjrm ? null : this.zzjpp, this.zzjpj);
        this.zzjri.zzyw();
    }
}
