package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.NodeApi.GetConnectedNodesResult;
import java.util.ArrayList;

final class zzfi extends zzn<GetConnectedNodesResult> {
    zzfi(zzfg zzfg, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new zzfj(status, new ArrayList());
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        ((zzep) ((zzhg) anyClient).getService()).zzc(new zzgu(this));
    }
}
