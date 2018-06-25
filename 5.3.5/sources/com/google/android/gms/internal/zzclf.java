package com.google.android.gms.internal;

final class zzclf implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcko zzjpk;

    zzclf(zzcko zzcko, zzcif zzcif) {
        this.zzjpk = zzcko;
        this.zzjpj = zzcif;
    }

    public final void run() {
        this.zzjpk.zzjev.zzbcc();
        this.zzjpk.zzjev.zzf(this.zzjpj);
    }
}
