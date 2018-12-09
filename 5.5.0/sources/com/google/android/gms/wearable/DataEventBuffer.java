package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.EntityBuffer;
import com.google.android.gms.wearable.internal.zzcy;

public class DataEventBuffer extends EntityBuffer<DataEvent> implements Result {
    private final Status zzp;

    public DataEventBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.zzp = new Status(dataHolder.getStatusCode());
    }

    protected /* synthetic */ Object getEntry(int i, int i2) {
        return new zzcy(this.mDataHolder, i, i2);
    }

    protected String getPrimaryDataMarkerColumn() {
        return "path";
    }

    public Status getStatus() {
        return this.zzp;
    }
}
