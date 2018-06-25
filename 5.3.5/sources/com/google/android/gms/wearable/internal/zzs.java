package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi.RemoveLocalCapabilityResult;

final class zzs extends zzn<RemoveLocalCapabilityResult> {
    private /* synthetic */ String zzlsm;

    zzs(zzo zzo, GoogleApiClient googleApiClient, String str) {
        this.zzlsm = str;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzhg zzhg = (zzhg) zzb;
        ((zzep) zzhg.zzalw()).zzb(new zzhd(this), this.zzlsm);
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return new zzu(status);
    }
}
