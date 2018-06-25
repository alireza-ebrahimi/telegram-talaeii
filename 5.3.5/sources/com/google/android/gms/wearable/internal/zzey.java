package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;

public final class zzey implements SendMessageResult {
    private final Status mStatus;
    private final int zzgpc;

    public zzey(Status status, int i) {
        this.mStatus = status;
        this.zzgpc = i;
    }

    public final int getRequestId() {
        return this.zzgpc;
    }

    public final Status getStatus() {
        return this.mStatus;
    }
}
