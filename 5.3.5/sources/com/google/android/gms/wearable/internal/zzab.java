package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.CapabilityApi.GetCapabilityResult;

final /* synthetic */ class zzab implements zzbo {
    static final zzbo zzgui = new zzab();

    private zzab() {
    }

    public final Object zzb(Result result) {
        return ((GetCapabilityResult) result).getCapability();
    }
}
