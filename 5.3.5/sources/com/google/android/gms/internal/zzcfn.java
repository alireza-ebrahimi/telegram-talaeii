package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.location.ActivityTransitionRequest;

final class zzcfn extends zzcfp {
    private /* synthetic */ PendingIntent zzhmu;
    private /* synthetic */ ActivityTransitionRequest zziti;

    zzcfn(zzcfk zzcfk, GoogleApiClient googleApiClient, ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent) {
        this.zziti = activityTransitionRequest;
        this.zzhmu = pendingIntent;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zza(this.zziti, this.zzhmu, (zzn) this);
    }
}
