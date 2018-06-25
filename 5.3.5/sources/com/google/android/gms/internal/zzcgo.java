package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.location.GeofencingRequest;

final class zzcgo extends zzcgq {
    private /* synthetic */ PendingIntent zzhmu;
    private /* synthetic */ GeofencingRequest zziua;

    zzcgo(zzcgn zzcgn, GoogleApiClient googleApiClient, GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        this.zziua = geofencingRequest;
        this.zzhmu = pendingIntent;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zza(this.zziua, this.zzhmu, (zzn) this);
    }
}
