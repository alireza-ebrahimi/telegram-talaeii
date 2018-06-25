package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.NodeApi.GetLocalNodeResult;

final class zzfh extends zzn<GetLocalNodeResult> {
    zzfh(zzfg zzfg, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new zzfk(status, null);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        ((zzep) ((zzhg) anyClient).getService()).zzb(new zzgy(this));
    }
}
