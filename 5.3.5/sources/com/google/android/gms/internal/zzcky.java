package com.google.android.gms.internal;

final class zzcky implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcko zzjpk;

    zzcky(zzcko zzcko, zzcif zzcif) {
        this.zzjpk = zzcko;
        this.zzjpj = zzcif;
    }

    public final void run() {
        this.zzjpk.zzjev.zzbcc();
        this.zzjpk.zzjev.zzd(this.zzjpj);
    }
}
