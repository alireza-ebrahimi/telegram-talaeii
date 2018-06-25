package com.google.android.gms.internal;

final class zzckz implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ zzcix zzjpo;

    zzckz(zzcko zzcko, zzcix zzcix, zzcif zzcif) {
        this.zzjpk = zzcko;
        this.zzjpo = zzcix;
        this.zzjpj = zzcif;
    }

    public final void run() {
        this.zzjpk.zzjev.zzbcc();
        this.zzjpk.zzjev.zzb(this.zzjpo, this.zzjpj);
    }
}
