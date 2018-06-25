package com.google.android.gms.internal;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.Wallet.zza;

final class zzdmt extends zza<BooleanResult> {
    zzdmt(zzdmo zzdmo, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected final void zza(zzdmv zzdmv) {
        zzdmv.zza(IsReadyToPayRequest.newBuilder().build(), (zzn) this);
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return new BooleanResult(status, false);
    }
}
