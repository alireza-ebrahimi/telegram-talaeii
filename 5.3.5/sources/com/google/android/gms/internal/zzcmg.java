package com.google.android.gms.internal;

import android.os.RemoteException;

final class zzcmg implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcme zzjri;

    zzcmg(zzcme zzcme, zzcif zzcif) {
        this.zzjri = zzcme;
        this.zzjpj = zzcif;
    }

    public final void run() {
        zzcjb zzd = this.zzjri.zzjrc;
        if (zzd == null) {
            this.zzjri.zzayp().zzbau().log("Failed to reset data on the service; null service");
            return;
        }
        try {
            zzd.zzd(this.zzjpj);
        } catch (RemoteException e) {
            this.zzjri.zzayp().zzbau().zzj("Failed to reset data on the service", e);
        }
        this.zzjri.zzyw();
    }
}
