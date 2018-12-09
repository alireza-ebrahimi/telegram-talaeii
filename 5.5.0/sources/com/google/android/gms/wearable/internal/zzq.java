package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi.GetAllCapabilitiesResult;

final class zzq extends zzn<GetAllCapabilitiesResult> {
    private final /* synthetic */ int zzbq;

    zzq(zzo zzo, GoogleApiClient googleApiClient, int i) {
        this.zzbq = i;
        super(googleApiClient);
    }

    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new zzx(status, null);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        zzhg zzhg = (zzhg) anyClient;
        ((zzep) zzhg.getService()).zza(new zzgq(this), this.zzbq);
    }
}
