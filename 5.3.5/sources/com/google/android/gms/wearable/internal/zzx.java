package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi.GetAllCapabilitiesResult;
import com.google.android.gms.wearable.CapabilityInfo;
import java.util.Map;

public final class zzx implements GetAllCapabilitiesResult {
    private final Status mStatus;
    private final Map<String, CapabilityInfo> zzlss;

    public zzx(Status status, Map<String, CapabilityInfo> map) {
        this.mStatus = status;
        this.zzlss = map;
    }

    public final Map<String, CapabilityInfo> getAllCapabilities() {
        return this.zzlss;
    }

    public final Status getStatus() {
        return this.mStatus;
    }
}
