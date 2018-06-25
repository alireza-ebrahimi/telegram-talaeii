package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcfm extends zzcfp {
    private /* synthetic */ PendingIntent zzith;

    zzcfm(zzcfk zzcfk, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        this.zzith = pendingIntent;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zzc(this.zzith);
        setResult(Status.zzftq);
    }
}
