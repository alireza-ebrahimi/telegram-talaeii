package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcfl extends zzcfp {
    private /* synthetic */ long zzitg;
    private /* synthetic */ PendingIntent zzith;

    zzcfl(zzcfk zzcfk, GoogleApiClient googleApiClient, long j, PendingIntent pendingIntent) {
        this.zzitg = j;
        this.zzith = pendingIntent;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zza(this.zzitg, this.zzith);
        setResult(Status.zzftq);
    }
}
