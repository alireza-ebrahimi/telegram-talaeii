package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.DataApi.DataItemResult;

final /* synthetic */ class zzcl implements ResultConverter {
    static final ResultConverter zzbx = new zzcl();

    private zzcl() {
    }

    public final Object convert(Result result) {
        return ((DataItemResult) result).getDataItem();
    }
}
