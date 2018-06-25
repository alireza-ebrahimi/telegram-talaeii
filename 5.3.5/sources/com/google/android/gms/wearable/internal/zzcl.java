package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.DataApi.DataItemResult;

final /* synthetic */ class zzcl implements zzbo {
    static final zzbo zzgui = new zzcl();

    private zzcl() {
    }

    public final Object zzb(Result result) {
        return ((DataItemResult) result).getDataItem();
    }
}
