package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzcgi extends zzcgj {
    private /* synthetic */ PendingIntent zzith;

    zzcgi(zzcfy zzcfy, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        this.zzith = pendingIntent;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zza(this.zzith, new zzcgk(this));
    }
}
