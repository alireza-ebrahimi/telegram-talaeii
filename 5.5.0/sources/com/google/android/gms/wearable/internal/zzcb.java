package com.google.android.gms.wearable.internal;

import android.net.Uri;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;

final class zzcb extends zzn<DeleteDataItemsResult> {
    private final /* synthetic */ Uri zzco;
    private final /* synthetic */ int zzdc;

    zzcb(zzbw zzbw, GoogleApiClient googleApiClient, Uri uri, int i) {
        this.zzco = uri;
        this.zzdc = i;
        super(googleApiClient);
    }

    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new zzch(status, 0);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        zzhg zzhg = (zzhg) anyClient;
        ((zzep) zzhg.getService()).zzb(new zzgp(this), this.zzco, this.zzdc);
    }
}
