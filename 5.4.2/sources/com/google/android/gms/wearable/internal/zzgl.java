package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.CapabilityApi.AddLocalCapabilityResult;

final class zzgl extends zzgm<AddLocalCapabilityResult> {
    public zzgl(ResultHolder<AddLocalCapabilityResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzf zzf) {
        zza(new zzu(zzgd.zzb(zzf.statusCode)));
    }
}
