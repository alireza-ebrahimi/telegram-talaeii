package com.google.android.gms.internal;

import android.os.RemoteException;
import android.text.TextUtils;

final class zzcmn implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcme zzjri;
    private /* synthetic */ boolean zzjrl = true;
    private /* synthetic */ boolean zzjrm;
    private /* synthetic */ zzcii zzjrn;
    private /* synthetic */ zzcii zzjro;

    zzcmn(zzcme zzcme, boolean z, boolean z2, zzcii zzcii, zzcif zzcif, zzcii zzcii2) {
        this.zzjri = zzcme;
        this.zzjrm = z2;
        this.zzjrn = zzcii;
        this.zzjpj = zzcif;
        this.zzjro = zzcii2;
    }

    public final void run() {
        zzcjb zzd = this.zzjri.zzjrc;
        if (zzd == null) {
            this.zzjri.zzayp().zzbau().log("Discarding data. Failed to send conditional user property to service");
            return;
        }
        if (this.zzjrl) {
            this.zzjri.zza(zzd, this.zzjrm ? null : this.zzjrn, this.zzjpj);
        } else {
            try {
                if (TextUtils.isEmpty(this.zzjro.packageName)) {
                    zzd.zza(this.zzjrn, this.zzjpj);
                } else {
                    zzd.zzb(this.zzjrn);
                }
            } catch (RemoteException e) {
                this.zzjri.zzayp().zzbau().zzj("Failed to send conditional user property to the service", e);
            }
        }
        this.zzjri.zzyw();
    }
}
