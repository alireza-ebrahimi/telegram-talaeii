package com.google.android.gms.internal.wallet;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.Wallet.zzb;

final class zzx extends zzb {
    private final /* synthetic */ int val$requestCode;

    zzx(zzw zzw, GoogleApiClient googleApiClient, int i) {
        this.val$requestCode = i;
        super(googleApiClient);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        zza((zzad) anyClient);
    }

    protected final void zza(zzad zzad) {
        zzad.zzb(this.val$requestCode);
        setResult(Status.RESULT_SUCCESS);
    }
}
