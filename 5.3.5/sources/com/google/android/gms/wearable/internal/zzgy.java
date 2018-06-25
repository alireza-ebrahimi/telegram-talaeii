package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.NodeApi.GetLocalNodeResult;

final class zzgy extends zzgm<GetLocalNodeResult> {
    public zzgy(zzn<GetLocalNodeResult> zzn) {
        super(zzn);
    }

    public final void zza(zzeg zzeg) {
        zzav(new zzfk(zzgd.zzdg(zzeg.statusCode), zzeg.zzlur));
    }
}
