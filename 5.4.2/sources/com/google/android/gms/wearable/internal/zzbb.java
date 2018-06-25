package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Channel.GetInputStreamResult;

final class zzbb extends zzn<GetInputStreamResult> {
    private final /* synthetic */ zzay zzcm;

    zzbb(zzay zzay, GoogleApiClient googleApiClient) {
        this.zzcm = zzay;
        super(googleApiClient);
    }

    public final /* synthetic */ Result createFailedResult(Status status) {
        return new zzbg(status, null);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        zzhg zzhg = (zzhg) anyClient;
        String zza = this.zzcm.zzce;
        zzei zzbr = new zzbr();
        ((zzep) zzhg.getService()).zza(new zzgs(this, zzbr), zzbr, zza);
    }
}
