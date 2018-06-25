package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.DataItem;

public final class zzcg implements DataItemResult {
    private final Status mStatus;
    private final DataItem zzltz;

    public zzcg(Status status, DataItem dataItem) {
        this.mStatus = status;
        this.zzltz = dataItem;
    }

    public final DataItem getDataItem() {
        return this.zzltz;
    }

    public final Status getStatus() {
        return this.mStatus;
    }
}
