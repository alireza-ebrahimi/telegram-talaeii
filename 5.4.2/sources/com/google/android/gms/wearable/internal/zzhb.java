package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import java.util.List;
import java.util.concurrent.FutureTask;

final class zzhb extends zzgm<DataItemResult> {
    private final List<FutureTask<Boolean>> zzev;

    zzhb(ResultHolder<DataItemResult> resultHolder, List<FutureTask<Boolean>> list) {
        super(resultHolder);
        this.zzev = list;
    }

    public final void zza(zzfu zzfu) {
        zza(new zzcg(zzgd.zzb(zzfu.statusCode), zzfu.zzdy));
        if (zzfu.statusCode != 0) {
            for (FutureTask cancel : this.zzev) {
                cancel.cancel(true);
            }
        }
    }
}
