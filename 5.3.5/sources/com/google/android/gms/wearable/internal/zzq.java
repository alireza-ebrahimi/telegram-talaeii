package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi.GetAllCapabilitiesResult;

final class zzq extends zzn<GetAllCapabilitiesResult> {
    private /* synthetic */ int zzlsn;

    zzq(zzo zzo, GoogleApiClient googleApiClient, int i) {
        this.zzlsn = i;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzhg zzhg = (zzhg) zzb;
        ((zzep) zzhg.zzalw()).zza(new zzgq(this), this.zzlsn);
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return new zzx(status, null);
    }
}
