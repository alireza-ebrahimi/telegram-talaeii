package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi.GetCapabilityResult;

final class zzp extends zzn<GetCapabilityResult> {
    private final /* synthetic */ String zzbp;
    private final /* synthetic */ int zzbq;

    zzp(zzo zzo, GoogleApiClient googleApiClient, String str, int i) {
        this.zzbp = str;
        this.zzbq = i;
        super(googleApiClient);
    }

    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new zzy(status, null);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        zzhg zzhg = (zzhg) anyClient;
        ((zzep) zzhg.getService()).zza(new zzgr(this), this.zzbp, this.zzbq);
    }
}
