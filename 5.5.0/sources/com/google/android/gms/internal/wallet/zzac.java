package com.google.android.gms.internal.wallet;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.Wallet.zza;

final class zzac extends zza<BooleanResult> {
    private final /* synthetic */ IsReadyToPayRequest zzef;

    zzac(zzw zzw, GoogleApiClient googleApiClient, IsReadyToPayRequest isReadyToPayRequest) {
        this.zzef = isReadyToPayRequest;
        super(googleApiClient);
    }

    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new BooleanResult(status, false);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        zza((zzad) anyClient);
    }

    protected final void zza(zzad zzad) {
        zzad.zza(this.zzef, (ResultHolder) this);
    }
}
