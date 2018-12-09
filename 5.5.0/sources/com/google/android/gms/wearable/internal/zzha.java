package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.ChannelApi.OpenChannelResult;

final class zzha extends zzgm<OpenChannelResult> {
    public zzha(ResultHolder<OpenChannelResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzfq zzfq) {
        zza(new zzam(zzgd.zzb(zzfq.statusCode), zzfq.zzck));
    }
}
