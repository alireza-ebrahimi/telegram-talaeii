package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.CapabilityApi.GetAllCapabilitiesResult;

final /* synthetic */ class zzac implements zzbo {
    static final zzbo zzgui = new zzac();

    private zzac() {
    }

    public final Object zzb(Result result) {
        return ((GetAllCapabilitiesResult) result).getAllCapabilities();
    }
}
