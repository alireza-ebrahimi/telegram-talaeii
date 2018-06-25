package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

final class zzcgg extends zzcgj {
    private /* synthetic */ PendingIntent zzith;
    private /* synthetic */ LocationRequest zzitt;

    zzcgg(zzcfy zzcfy, GoogleApiClient googleApiClient, LocationRequest locationRequest, PendingIntent pendingIntent) {
        this.zzitt = locationRequest;
        this.zzith = pendingIntent;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zza(this.zzitt, this.zzith, new zzcgk(this));
    }
}
