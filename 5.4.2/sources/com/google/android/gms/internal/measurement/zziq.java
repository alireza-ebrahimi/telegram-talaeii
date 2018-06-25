package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

final class zziq implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzij zzapn;

    zziq(zzij zzij, zzdz zzdz) {
        this.zzapn = zzij;
        this.zzano = zzdz;
    }

    public final void run() {
        zzez zzd = this.zzapn.zzaph;
        if (zzd == null) {
            this.zzapn.zzgf().zzis().log("Failed to send measurementEnabled to service");
            return;
        }
        try {
            zzd.zzb(this.zzano);
            this.zzapn.zzcu();
        } catch (RemoteException e) {
            this.zzapn.zzgf().zzis().zzg("Failed to send measurementEnabled to the service", e);
        }
    }
}
