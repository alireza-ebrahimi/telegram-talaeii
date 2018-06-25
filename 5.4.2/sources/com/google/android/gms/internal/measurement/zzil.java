package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

final class zzil implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzij zzapn;

    zzil(zzij zzij, zzdz zzdz) {
        this.zzapn = zzij;
        this.zzano = zzdz;
    }

    public final void run() {
        zzez zzd = this.zzapn.zzaph;
        if (zzd == null) {
            this.zzapn.zzgf().zzis().log("Failed to reset data on the service; null service");
            return;
        }
        try {
            zzd.zzd(this.zzano);
        } catch (RemoteException e) {
            this.zzapn.zzgf().zzis().zzg("Failed to reset data on the service", e);
        }
        this.zzapn.zzcu();
    }
}
