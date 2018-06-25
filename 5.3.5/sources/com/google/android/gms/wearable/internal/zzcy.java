package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;

@Hide
public final class zzcy extends zzc implements DataEvent {
    private final int zzidy;

    public zzcy(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.zzidy = i2;
    }

    public final /* synthetic */ Object freeze() {
        return new zzcx(this);
    }

    public final DataItem getDataItem() {
        return new zzdf(this.zzfxb, this.zzgch, this.zzidy);
    }

    public final int getType() {
        return getInteger("event_type");
    }

    public final String toString() {
        String str = getType() == 1 ? "changed" : getType() == 2 ? "deleted" : "unknown";
        String valueOf = String.valueOf(getDataItem());
        return new StringBuilder((String.valueOf(str).length() + 32) + String.valueOf(valueOf).length()).append("DataEventRef{ type=").append(str).append(", dataitem=").append(valueOf).append(" }").toString();
    }
}
