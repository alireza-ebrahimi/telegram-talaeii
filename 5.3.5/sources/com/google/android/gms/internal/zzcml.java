package com.google.android.gms.internal;

import android.os.RemoteException;

final class zzcml implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcme zzjri;

    zzcml(zzcme zzcme, zzcif zzcif) {
        this.zzjri = zzcme;
        this.zzjpj = zzcif;
    }

    public final void run() {
        zzcjb zzd = this.zzjri.zzjrc;
        if (zzd == null) {
            this.zzjri.zzayp().zzbau().log("Failed to send measurementEnabled to service");
            return;
        }
        try {
            zzd.zzb(this.zzjpj);
            this.zzjri.zzyw();
        } catch (RemoteException e) {
            this.zzjri.zzayp().zzbau().zzj("Failed to send measurementEnabled to the service", e);
        }
    }
}
