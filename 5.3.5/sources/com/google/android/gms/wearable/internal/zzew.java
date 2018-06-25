package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.MessageApi.MessageListener;

final class zzew extends zzn<Status> {
    private /* synthetic */ MessageListener zzluv;

    zzew(zzeu zzeu, GoogleApiClient googleApiClient, MessageListener messageListener) {
        this.zzluv = messageListener;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzhg) zzb).zza((zzn) this, this.zzluv);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
