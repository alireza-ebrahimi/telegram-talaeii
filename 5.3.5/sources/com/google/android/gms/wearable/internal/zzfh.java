package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.NodeApi.GetLocalNodeResult;

final class zzfh extends zzn<GetLocalNodeResult> {
    zzfh(zzfg zzfg, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzep) ((zzhg) zzb).zzalw()).zzb(new zzgy(this));
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return new zzfk(status, null);
    }
}
