package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.ChannelApi.OpenChannelResult;

final class zzha extends zzgm<OpenChannelResult> {
    public zzha(zzn<OpenChannelResult> zzn) {
        super(zzn);
    }

    public final void zza(zzfq zzfq) {
        zzav(new zzam(zzgd.zzdg(zzfq.statusCode), zzfq.zzlth));
    }
}
