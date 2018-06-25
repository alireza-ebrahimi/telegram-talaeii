package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;
import com.google.android.gms.wearable.DataItemAsset;

final class zzcd extends zzn<GetFdForAssetResult> {
    private /* synthetic */ DataItemAsset zzltx;

    zzcd(zzbw zzbw, GoogleApiClient googleApiClient, DataItemAsset dataItemAsset) {
        this.zzltx = dataItemAsset;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzhg) zzb).zza((zzn) this, Asset.createFromRef(this.zzltx.getId()));
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return new zzci(status, null);
    }
}
