package com.google.android.gms.internal;

import android.os.RemoteException;

final class zzcmj implements Runnable {
    private /* synthetic */ zzcme zzjri;
    private /* synthetic */ zzclz zzjrk;

    zzcmj(zzcme zzcme, zzclz zzclz) {
        this.zzjri = zzcme;
        this.zzjrk = zzclz;
    }

    public final void run() {
        zzcjb zzd = this.zzjri.zzjrc;
        if (zzd == null) {
            this.zzjri.zzayp().zzbau().log("Failed to send current screen to service");
            return;
        }
        try {
            if (this.zzjrk == null) {
                zzd.zza(0, null, null, this.zzjri.getContext().getPackageName());
            } else {
                zzd.zza(this.zzjrk.zzjql, this.zzjrk.zzjqj, this.zzjrk.zzjqk, this.zzjri.getContext().getPackageName());
            }
            this.zzjri.zzyw();
        } catch (RemoteException e) {
            this.zzjri.zzayp().zzbau().zzj("Failed to send current screen to the service", e);
        }
    }
}
