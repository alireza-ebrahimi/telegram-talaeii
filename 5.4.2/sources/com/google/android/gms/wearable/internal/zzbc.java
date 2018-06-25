package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Channel.GetOutputStreamResult;

final class zzbc extends zzn<GetOutputStreamResult> {
    private final /* synthetic */ zzay zzcm;

    zzbc(zzay zzay, GoogleApiClient googleApiClient) {
        this.zzcm = zzay;
        super(googleApiClient);
    }

    public final /* synthetic */ Result createFailedResult(Status status) {
        return new zzbh(status, null);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        zzhg zzhg = (zzhg) anyClient;
        String zza = this.zzcm.zzce;
        zzei zzbr = new zzbr();
        ((zzep) zzhg.getService()).zzb(new zzgt(this, zzbr), zzbr, zza);
    }
}
