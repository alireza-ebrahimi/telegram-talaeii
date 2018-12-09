package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.CapabilityApi.GetCapabilityResult;

final /* synthetic */ class zzab implements ResultConverter {
    static final ResultConverter zzbx = new zzab();

    private zzab() {
    }

    public final Object convert(Result result) {
        return ((GetCapabilityResult) result).getCapability();
    }
}
