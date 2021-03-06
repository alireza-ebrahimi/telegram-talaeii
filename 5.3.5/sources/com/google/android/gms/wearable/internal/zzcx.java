package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;

@Hide
public final class zzcx implements DataEvent {
    private int zzenu;
    private DataItem zzltz;

    public zzcx(DataEvent dataEvent) {
        this.zzenu = dataEvent.getType();
        this.zzltz = (DataItem) dataEvent.getDataItem().freeze();
    }

    public final /* bridge */ /* synthetic */ Object freeze() {
        if (this != null) {
            return this;
        }
        throw null;
    }

    public final DataItem getDataItem() {
        return this.zzltz;
    }

    public final int getType() {
        return this.zzenu;
    }

    public final boolean isDataValid() {
        return true;
    }

    public final String toString() {
        String str = getType() == 1 ? "changed" : getType() == 2 ? "deleted" : "unknown";
        String valueOf = String.valueOf(getDataItem());
        return new StringBuilder((String.valueOf(str).length() + 35) + String.valueOf(valueOf).length()).append("DataEventEntity{ type=").append(str).append(", dataitem=").append(valueOf).append(" }").toString();
    }
}
