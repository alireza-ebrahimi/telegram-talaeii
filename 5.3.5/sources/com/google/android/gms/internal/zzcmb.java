package com.google.android.gms.internal;

import android.os.Bundle;

final class zzcmb implements Runnable {
    private /* synthetic */ boolean zzjqv;
    private /* synthetic */ zzclz zzjqw;
    private /* synthetic */ zzcmd zzjqx;
    private /* synthetic */ zzcma zzjqy;

    zzcmb(zzcma zzcma, boolean z, zzclz zzclz, zzcmd zzcmd) {
        this.zzjqy = zzcma;
        this.zzjqv = z;
        this.zzjqw = zzclz;
        this.zzjqx = zzcmd;
    }

    public final void run() {
        if (this.zzjqv && this.zzjqy.zzjqm != null) {
            this.zzjqy.zza(this.zzjqy.zzjqm);
        }
        boolean z = (this.zzjqw != null && this.zzjqw.zzjql == this.zzjqx.zzjql && zzcno.zzas(this.zzjqw.zzjqk, this.zzjqx.zzjqk) && zzcno.zzas(this.zzjqw.zzjqj, this.zzjqx.zzjqj)) ? false : true;
        if (z) {
            Bundle bundle = new Bundle();
            zzcma.zza(this.zzjqx, bundle, true);
            if (this.zzjqw != null) {
                if (this.zzjqw.zzjqj != null) {
                    bundle.putString("_pn", this.zzjqw.zzjqj);
                }
                bundle.putString("_pc", this.zzjqw.zzjqk);
                bundle.putLong("_pi", this.zzjqw.zzjql);
            }
            this.zzjqy.zzayd().zzd("auto", "_vs", bundle);
        }
        this.zzjqy.zzjqm = this.zzjqx;
        this.zzjqy.zzayg().zza(this.zzjqx);
    }
}
