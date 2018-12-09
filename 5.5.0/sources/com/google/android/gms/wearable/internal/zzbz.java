package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataItemBuffer;

final class zzbz extends zzn<DataItemBuffer> {
    zzbz(zzbw zzbw, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new DataItemBuffer(DataHolder.empty(status.getStatusCode()));
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        ((zzep) ((zzhg) anyClient).getService()).zza(new zzgw(this));
    }
}
