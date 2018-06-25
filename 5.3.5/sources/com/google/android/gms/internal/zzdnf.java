package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.CreateWalletObjectsRequest;
import com.google.android.gms.wallet.Wallet.zzb;

final class zzdnf extends zzb {
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ CreateWalletObjectsRequest zzlog;

    zzdnf(zzdne zzdne, GoogleApiClient googleApiClient, CreateWalletObjectsRequest createWalletObjectsRequest, int i) {
        this.zzlog = createWalletObjectsRequest;
        this.val$requestCode = i;
        super(googleApiClient);
    }

    protected final void zza(zzdmv zzdmv) {
        zzdmv.zza(this.zzlog, this.val$requestCode);
        setResult(Status.zzftq);
    }
}
