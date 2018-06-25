package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;

final class zzgp extends zzgm<DeleteDataItemsResult> {
    public zzgp(ResultHolder<DeleteDataItemsResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzdg zzdg) {
        zza(new zzch(zzgd.zzb(zzdg.statusCode), zzdg.zzdh));
    }
}
