package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.NodeApi.GetConnectedNodesResult;

final /* synthetic */ class zzfn implements ResultConverter {
    static final ResultConverter zzbx = new zzfn();

    private zzfn() {
    }

    public final Object convert(Result result) {
        return ((GetConnectedNodesResult) result).getNodes();
    }
}
