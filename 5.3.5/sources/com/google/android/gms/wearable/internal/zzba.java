package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzba extends zzn<Status> {
    private /* synthetic */ int zzcdn;
    private /* synthetic */ zzay zzlti;

    zzba(zzay zzay, GoogleApiClient googleApiClient, int i) {
        this.zzlti = zzay;
        this.zzcdn = i;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzhg zzhg = (zzhg) zzb;
        ((zzep) zzhg.zzalw()).zzb(new zzgo(this), this.zzlti.zzeia, this.zzcdn);
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
