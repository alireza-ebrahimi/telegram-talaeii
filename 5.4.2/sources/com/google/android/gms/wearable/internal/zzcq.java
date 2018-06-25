package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;

final /* synthetic */ class zzcq implements ResultConverter {
    static final ResultConverter zzbx = new zzcq();

    private zzcq() {
    }

    public final Object convert(Result result) {
        return Integer.valueOf(((DeleteDataItemsResult) result).getNumDeleted());
    }
}
