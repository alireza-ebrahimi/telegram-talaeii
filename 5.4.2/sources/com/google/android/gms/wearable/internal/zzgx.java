package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;

final class zzgx extends zzgm<GetFdForAssetResult> {
    public zzgx(ResultHolder<GetFdForAssetResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzee zzee) {
        zza(new zzci(zzgd.zzb(zzee.statusCode), zzee.zzdz));
    }
}
