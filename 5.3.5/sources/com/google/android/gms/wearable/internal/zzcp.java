package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;

final /* synthetic */ class zzcp implements zzbo {
    static final zzbo zzgui = new zzcp();

    private zzcp() {
    }

    public final Object zzb(Result result) {
        return Integer.valueOf(((DeleteDataItemsResult) result).getNumDeleted());
    }
}
