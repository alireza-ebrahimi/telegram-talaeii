package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcgb extends zzcgj {
    private /* synthetic */ boolean zzitw;

    zzcgb(zzcfy zzcfy, GoogleApiClient googleApiClient, boolean z) {
        this.zzitw = z;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zzbo(this.zzitw);
        setResult(Status.zzftq);
    }
}
