package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.DataApi.DataListener;

final class zzcf extends zzn<Status> {
    private /* synthetic */ DataListener zzlty;

    zzcf(zzbw zzbw, GoogleApiClient googleApiClient, DataListener dataListener) {
        this.zzlty = dataListener;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzhg) zzb).zza((zzn) this, this.zzlty);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
