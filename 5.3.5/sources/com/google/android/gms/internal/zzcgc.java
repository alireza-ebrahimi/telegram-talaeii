package com.google.android.gms.internal;

import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcgc extends zzcgj {
    private /* synthetic */ Location zzitx;

    zzcgc(zzcfy zzcfy, GoogleApiClient googleApiClient, Location location) {
        this.zzitx = location;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zzc(this.zzitx);
        setResult(Status.zzftq);
    }
}
