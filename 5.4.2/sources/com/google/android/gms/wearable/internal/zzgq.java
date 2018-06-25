package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.CapabilityApi.GetAllCapabilitiesResult;

final class zzgq extends zzgm<GetAllCapabilitiesResult> {
    public zzgq(ResultHolder<GetAllCapabilitiesResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzdi zzdi) {
        zza(new zzx(zzgd.zzb(zzdi.statusCode), zzgk.zza(zzdi.zzdp)));
    }
}
