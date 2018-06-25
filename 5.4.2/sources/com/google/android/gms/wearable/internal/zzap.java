package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.ChannelApi.OpenChannelResult;

final /* synthetic */ class zzap implements ResultConverter {
    static final ResultConverter zzbx = new zzap();

    private zzap() {
    }

    public final Object convert(Result result) {
        return zzao.zza(((OpenChannelResult) result).getChannel());
    }
}
