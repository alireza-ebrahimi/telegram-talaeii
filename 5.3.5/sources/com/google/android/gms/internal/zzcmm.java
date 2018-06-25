package com.google.android.gms.internal;

import android.os.RemoteException;
import android.text.TextUtils;

final class zzcmm implements Runnable {
    private /* synthetic */ String zziuw;
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcix zzjpo;
    private /* synthetic */ zzcme zzjri;
    private /* synthetic */ boolean zzjrl = true;
    private /* synthetic */ boolean zzjrm;

    zzcmm(zzcme zzcme, boolean z, boolean z2, zzcix zzcix, zzcif zzcif, String str) {
        this.zzjri = zzcme;
        this.zzjrm = z2;
        this.zzjpo = zzcix;
        this.zzjpj = zzcif;
        this.zziuw = str;
    }

    public final void run() {
        zzcjb zzd = this.zzjri.zzjrc;
        if (zzd == null) {
            this.zzjri.zzayp().zzbau().log("Discarding data. Failed to send event to service");
            return;
        }
        if (this.zzjrl) {
            this.zzjri.zza(zzd, this.zzjrm ? null : this.zzjpo, this.zzjpj);
        } else {
            try {
                if (TextUtils.isEmpty(this.zziuw)) {
                    zzd.zza(this.zzjpo, this.zzjpj);
                } else {
                    zzd.zza(this.zzjpo, this.zziuw, this.zzjri.zzayp().zzbbc());
                }
            } catch (RemoteException e) {
                this.zzjri.zzayp().zzbau().zzj("Failed to send event to the service", e);
            }
        }
        this.zzjri.zzyw();
    }
}
