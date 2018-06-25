package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzaz extends zzn<Status> {
    private /* synthetic */ zzay zzlti;

    zzaz(zzay zzay, GoogleApiClient googleApiClient) {
        this.zzlti = zzay;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzhg zzhg = (zzhg) zzb;
        ((zzep) zzhg.zzalw()).zzc(new zzgn(this), this.zzlti.zzeia);
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
