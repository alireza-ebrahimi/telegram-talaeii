package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.location.LocationCallback;

final class zzcga extends zzcgj {
    private /* synthetic */ LocationCallback zzitv;

    zzcga(zzcfy zzcfy, GoogleApiClient googleApiClient, LocationCallback locationCallback) {
        this.zzitv = locationCallback;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zzb(zzcm.zzb(this.zzitv, LocationCallback.class.getSimpleName()), new zzcgk(this));
    }
}
