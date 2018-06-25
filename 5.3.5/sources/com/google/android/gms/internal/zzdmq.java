package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.Wallet.zzb;

final class zzdmq extends zzb {
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ MaskedWalletRequest zzlpo;

    zzdmq(zzdmo zzdmo, GoogleApiClient googleApiClient, MaskedWalletRequest maskedWalletRequest, int i) {
        this.zzlpo = maskedWalletRequest;
        this.val$requestCode = i;
        super(googleApiClient);
    }

    protected final void zza(zzdmv zzdmv) {
        zzdmv.zza(this.zzlpo, this.val$requestCode);
        setResult(Status.zzftq);
    }
}
