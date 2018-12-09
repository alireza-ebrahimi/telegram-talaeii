package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.Channel.GetInputStreamResult;

final /* synthetic */ class zzaq implements ResultConverter {
    static final ResultConverter zzbx = new zzaq();

    private zzaq() {
    }

    public final Object convert(Result result) {
        return ((GetInputStreamResult) result).getInputStream();
    }
}
