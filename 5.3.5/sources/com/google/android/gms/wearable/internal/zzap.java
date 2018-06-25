package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.ChannelApi.OpenChannelResult;

final /* synthetic */ class zzap implements zzbo {
    static final zzbo zzgui = new zzap();

    private zzap() {
    }

    public final Object zzb(Result result) {
        return zzao.zza(((OpenChannelResult) result).getChannel());
    }
}
