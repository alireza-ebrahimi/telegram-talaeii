package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;

public final class zzch implements DeleteDataItemsResult {
    private final Status mStatus;
    private final int zzlua;

    public zzch(Status status, int i) {
        this.mStatus = status;
        this.zzlua = i;
    }

    public final int getNumDeleted() {
        return this.zzlua;
    }

    public final Status getStatus() {
        return this.mStatus;
    }
}
