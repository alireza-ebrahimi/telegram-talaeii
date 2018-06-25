package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzn;

final class zzcfo extends zzcfp {
    private /* synthetic */ PendingIntent zzhmu;

    zzcfo(zzcfk zzcfk, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        this.zzhmu = pendingIntent;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zza(this.zzhmu, (zzn) this);
    }
}
