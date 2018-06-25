package com.google.android.gms.internal.measurement;

final class zziv implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzjz zzanv;
    private final /* synthetic */ zzij zzapn;
    private final /* synthetic */ boolean zzapq;

    zziv(zzij zzij, boolean z, zzjz zzjz, zzdz zzdz) {
        this.zzapn = zzij;
        this.zzapq = z;
        this.zzanv = zzjz;
        this.zzano = zzdz;
    }

    public final void run() {
        zzez zzd = this.zzapn.zzaph;
        if (zzd == null) {
            this.zzapn.zzgf().zzis().log("Discarding data. Failed to set user attribute");
            return;
        }
        this.zzapn.zza(zzd, this.zzapq ? null : this.zzanv, this.zzano);
        this.zzapn.zzcu();
    }
}
