package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

final class zzcfz extends zzcgj {
    private /* synthetic */ LocationRequest zzitt;
    private /* synthetic */ LocationListener zzitu;

    zzcfz(zzcfy zzcfy, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener) {
        this.zzitt = locationRequest;
        this.zzitu = locationListener;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zza(this.zzitt, zzcm.zzb(this.zzitu, zzchz.zzaxp(), LocationListener.class.getSimpleName()), new zzcgk(this));
    }
}
