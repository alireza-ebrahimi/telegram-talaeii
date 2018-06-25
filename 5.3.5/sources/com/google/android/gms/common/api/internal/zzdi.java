package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

final class zzdi implements Runnable {
    private /* synthetic */ Result zzgbo;
    private /* synthetic */ zzdh zzgbp;

    zzdi(zzdh zzdh, Result result) {
        this.zzgbp = zzdh;
        this.zzgbo = result;
    }

    @WorkerThread
    public final void run() {
        GoogleApiClient googleApiClient;
        try {
            BasePendingResult.zzfvb.set(Boolean.valueOf(true));
            this.zzgbp.zzgbm.sendMessage(this.zzgbp.zzgbm.obtainMessage(0, this.zzgbp.zzgbh.onSuccess(this.zzgbo)));
            BasePendingResult.zzfvb.set(Boolean.valueOf(false));
            zzdh.zzd(this.zzgbo);
            googleApiClient = (GoogleApiClient) this.zzgbp.zzfve.get();
            if (googleApiClient != null) {
                googleApiClient.zzb(this.zzgbp);
            }
        } catch (RuntimeException e) {
            this.zzgbp.zzgbm.sendMessage(this.zzgbp.zzgbm.obtainMessage(1, e));
            BasePendingResult.zzfvb.set(Boolean.valueOf(false));
            zzdh.zzd(this.zzgbo);
            googleApiClient = (GoogleApiClient) this.zzgbp.zzfve.get();
            if (googleApiClient != null) {
                googleApiClient.zzb(this.zzgbp);
            }
        } catch (Throwable th) {
            Throwable th2 = th;
            BasePendingResult.zzfvb.set(Boolean.valueOf(false));
            zzdh.zzd(this.zzgbo);
            googleApiClient = (GoogleApiClient) this.zzgbp.zzfve.get();
            if (googleApiClient != null) {
                googleApiClient.zzb(this.zzgbp);
            }
        }
    }
}
