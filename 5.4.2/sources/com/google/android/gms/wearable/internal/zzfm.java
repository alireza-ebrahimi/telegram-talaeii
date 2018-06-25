package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.NodeApi.GetLocalNodeResult;

final /* synthetic */ class zzfm implements ResultConverter {
    static final ResultConverter zzbx = new zzfm();

    private zzfm() {
    }

    public final Object convert(Result result) {
        return ((GetLocalNodeResult) result).getNode();
    }
}
