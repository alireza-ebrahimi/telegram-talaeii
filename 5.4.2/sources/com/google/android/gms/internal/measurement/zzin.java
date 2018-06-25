package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

final class zzin implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzij zzapn;

    zzin(zzij zzij, zzdz zzdz) {
        this.zzapn = zzij;
        this.zzano = zzdz;
    }

    public final void run() {
        zzez zzd = this.zzapn.zzaph;
        if (zzd == null) {
            this.zzapn.zzgf().zzis().log("Discarding data. Failed to send app launch");
            return;
        }
        try {
            zzd.zza(this.zzano);
            this.zzapn.zza(zzd, null, this.zzano);
            this.zzapn.zzcu();
        } catch (RemoteException e) {
            this.zzapn.zzgf().zzis().zzg("Failed to send app launch to the service", e);
        }
    }
}
