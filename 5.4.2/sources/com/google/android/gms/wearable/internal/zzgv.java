package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.DataApi.DataItemResult;

final class zzgv extends zzgm<DataItemResult> {
    public zzgv(ResultHolder<DataItemResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzec zzec) {
        zza(new zzcg(zzgd.zzb(zzec.statusCode), zzec.zzdy));
    }
}
