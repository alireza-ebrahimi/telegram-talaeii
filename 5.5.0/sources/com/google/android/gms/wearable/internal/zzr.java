package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi.AddLocalCapabilityResult;

final class zzr extends zzn<AddLocalCapabilityResult> {
    private final /* synthetic */ String zzbp;

    zzr(zzo zzo, GoogleApiClient googleApiClient, String str) {
        this.zzbp = str;
        super(googleApiClient);
    }

    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new zzu(status);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        zzhg zzhg = (zzhg) anyClient;
        ((zzep) zzhg.getService()).zza(new zzgl(this), this.zzbp);
    }
}
