package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.Channel.GetOutputStreamResult;

final /* synthetic */ class zzar implements ResultConverter {
    static final ResultConverter zzbx = new zzar();

    private zzar() {
    }

    public final Object convert(Result result) {
        return ((GetOutputStreamResult) result).getOutputStream();
    }
}
