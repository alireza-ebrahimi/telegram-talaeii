package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.location.LocationListener;

final class zzcgh extends zzcgj {
    private /* synthetic */ LocationListener zzitu;

    zzcgh(zzcfy zzcfy, GoogleApiClient googleApiClient, LocationListener locationListener) {
        this.zzitu = locationListener;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zza(zzcm.zzb(this.zzitu, LocationListener.class.getSimpleName()), new zzcgk(this));
    }
}
