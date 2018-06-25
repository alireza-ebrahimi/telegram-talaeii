package com.google.android.gms.internal;

import android.os.RemoteException;

final class zzcmi implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcme zzjri;

    zzcmi(zzcme zzcme, zzcif zzcif) {
        this.zzjri = zzcme;
        this.zzjpj = zzcif;
    }

    public final void run() {
        zzcjb zzd = this.zzjri.zzjrc;
        if (zzd == null) {
            this.zzjri.zzayp().zzbau().log("Discarding data. Failed to send app launch");
            return;
        }
        try {
            zzd.zza(this.zzjpj);
            this.zzjri.zza(zzd, null, this.zzjpj);
            this.zzjri.zzyw();
        } catch (RemoteException e) {
            this.zzjri.zzayp().zzbau().zzj("Failed to send app launch to the service", e);
        }
    }
}
