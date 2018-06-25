package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.Wallet.zzb;

final class zzdmp extends zzb {
    private /* synthetic */ int val$requestCode;

    zzdmp(zzdmo zzdmo, GoogleApiClient googleApiClient, int i) {
        this.val$requestCode = i;
        super(googleApiClient);
    }

    protected final void zza(zzdmv zzdmv) {
        zzdmv.zzfq(this.val$requestCode);
        setResult(Status.zzftq);
    }
}
