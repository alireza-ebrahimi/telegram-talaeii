package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.CapabilityApi.GetAllCapabilitiesResult;

final /* synthetic */ class zzac implements ResultConverter {
    static final ResultConverter zzbx = new zzac();

    private zzac() {
    }

    public final Object convert(Result result) {
        return ((GetAllCapabilitiesResult) result).getAllCapabilities();
    }
}
