package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;

final class zzcc extends zzn<GetFdForAssetResult> {
    private final /* synthetic */ Asset zzdd;

    zzcc(zzbw zzbw, GoogleApiClient googleApiClient, Asset asset) {
        this.zzdd = asset;
        super(googleApiClient);
    }

    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new zzci(status, null);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        ((zzhg) anyClient).zza((ResultHolder) this, this.zzdd);
    }
}
