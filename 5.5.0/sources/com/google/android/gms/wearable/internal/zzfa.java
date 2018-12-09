package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;

final /* synthetic */ class zzfa implements ResultConverter {
    static final ResultConverter zzbx = new zzfa();

    private zzfa() {
    }

    public final Object convert(Result result) {
        return Integer.valueOf(((SendMessageResult) result).getRequestId());
    }
}
