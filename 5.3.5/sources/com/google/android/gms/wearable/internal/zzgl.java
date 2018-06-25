package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.CapabilityApi.AddLocalCapabilityResult;

final class zzgl extends zzgm<AddLocalCapabilityResult> {
    public zzgl(zzn<AddLocalCapabilityResult> zzn) {
        super(zzn);
    }

    public final void zza(zzf zzf) {
        zzav(new zzu(zzgd.zzdg(zzf.statusCode)));
    }
}
