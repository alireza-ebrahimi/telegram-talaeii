package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.location.zzal;

final class zzcgp extends zzcgq {
    private /* synthetic */ zzal zziub;

    zzcgp(zzcgn zzcgn, GoogleApiClient googleApiClient, zzal zzal) {
        this.zziub = zzal;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzchh) zzb).zza(this.zziub, (zzn) this);
    }
}
