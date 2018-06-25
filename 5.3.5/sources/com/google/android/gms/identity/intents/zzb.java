package com.google.android.gms.identity.intents;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzcdo;

final class zzb extends zza {
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ UserAddressRequest zzilm;

    zzb(GoogleApiClient googleApiClient, UserAddressRequest userAddressRequest, int i) {
        this.zzilm = userAddressRequest;
        this.val$requestCode = i;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(com.google.android.gms.common.api.Api.zzb zzb) throws RemoteException {
        ((zzcdo) zzb).zza(this.zzilm, this.val$requestCode);
        setResult(Status.zzftq);
    }
}
