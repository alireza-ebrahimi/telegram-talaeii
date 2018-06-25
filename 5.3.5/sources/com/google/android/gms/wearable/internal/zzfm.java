package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.NodeApi.GetLocalNodeResult;

final /* synthetic */ class zzfm implements zzbo {
    static final zzbo zzgui = new zzfm();

    private zzfm() {
    }

    public final Object zzb(Result result) {
        return ((GetLocalNodeResult) result).getNode();
    }
}
