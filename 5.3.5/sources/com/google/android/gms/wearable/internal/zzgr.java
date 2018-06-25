package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.CapabilityApi.GetCapabilityResult;

final class zzgr extends zzgm<GetCapabilityResult> {
    public zzgr(zzn<GetCapabilityResult> zzn) {
        super(zzn);
    }

    public final void zza(zzdk zzdk) {
        zzav(new zzy(zzgd.zzdg(zzdk.statusCode), zzdk.zzlui == null ? null : new zzw(zzdk.zzlui)));
    }
}
