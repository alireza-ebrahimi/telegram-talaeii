package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.CapabilityApi.GetCapabilityResult;

final class zzgr extends zzgm<GetCapabilityResult> {
    public zzgr(ResultHolder<GetCapabilityResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzdk zzdk) {
        zza(new zzy(zzgd.zzb(zzdk.statusCode), zzdk.zzdq == null ? null : new zzw(zzdk.zzdq)));
    }
}
