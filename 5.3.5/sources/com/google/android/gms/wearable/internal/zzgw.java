package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataItemBuffer;

final class zzgw extends zzgm<DataItemBuffer> {
    public zzgw(zzn<DataItemBuffer> zzn) {
        super(zzn);
    }

    public final void zzbp(DataHolder dataHolder) {
        zzav(new DataItemBuffer(dataHolder));
    }
}
