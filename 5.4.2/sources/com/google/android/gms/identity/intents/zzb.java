package com.google.android.gms.identity.intents;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.identity.zze;

final class zzb extends zza {
    private final /* synthetic */ int val$requestCode;
    private final /* synthetic */ UserAddressRequest zze;

    zzb(GoogleApiClient googleApiClient, UserAddressRequest userAddressRequest, int i) {
        this.zze = userAddressRequest;
        this.val$requestCode = i;
        super(googleApiClient);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        ((zze) anyClient).zza(this.zze, this.val$requestCode);
        setResult(Status.RESULT_SUCCESS);
    }
}
