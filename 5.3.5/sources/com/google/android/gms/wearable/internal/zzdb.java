package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.wearable.DataItemAsset;

@Hide
public final class zzdb extends zzc implements DataItemAsset {
    public zzdb(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    public final /* synthetic */ Object freeze() {
        return new zzcz(this);
    }

    public final String getDataItemKey() {
        return getString("asset_key");
    }

    @Hide
    public final String getId() {
        return getString("asset_id");
    }
}
