package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi.GetLocalNodeResult;

public final class zzfk implements GetLocalNodeResult {
    private final Status mStatus;
    private final Node zzlvb;

    public zzfk(Status status, Node node) {
        this.mStatus = status;
        this.zzlvb = node;
    }

    public final Node getNode() {
        return this.zzlvb;
    }

    public final Status getStatus() {
        return this.mStatus;
    }
}
