package com.google.android.gms.internal;

import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

final class zzcgf extends zzcgj {
    private /* synthetic */ LocationRequest zzitt;
    private /* synthetic */ LocationCallback zzitv;
    private /* synthetic */ Looper zzity;

    zzcgf(zzcfy zzcfy, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationCallback locationCallback, Looper looper) {
        this.zzitt = locationRequest;
        this.zzitv = locationCallback;
        this.zzity = looper;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zza(zzchl.zza(this.zzitt), zzcm.zzb(this.zzitv, zzchz.zzb(this.zzity), LocationCallback.class.getSimpleName()), new zzcgk(this));
    }
}
