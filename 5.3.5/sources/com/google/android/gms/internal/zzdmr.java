package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.Wallet.zzb;

final class zzdmr extends zzb {
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ FullWalletRequest zzlpp;

    zzdmr(zzdmo zzdmo, GoogleApiClient googleApiClient, FullWalletRequest fullWalletRequest, int i) {
        this.zzlpp = fullWalletRequest;
        this.val$requestCode = i;
        super(googleApiClient);
    }

    protected final void zza(zzdmv zzdmv) {
        zzdmv.zza(this.zzlpp, this.val$requestCode);
        setResult(Status.zzftq);
    }
}
