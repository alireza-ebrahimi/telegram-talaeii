package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.DataItemBuffer;

final /* synthetic */ class zzcm implements ResultConverter {
    static final ResultConverter zzbx = new zzcm();

    private zzcm() {
    }

    public final Object convert(Result result) {
        return (DataItemBuffer) result;
    }
}
