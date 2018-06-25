package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.NodeApi.GetConnectedNodesResult;

final /* synthetic */ class zzfn implements zzbo {
    static final zzbo zzgui = new zzfn();

    private zzfn() {
    }

    public final Object zzb(Result result) {
        return ((GetConnectedNodesResult) result).getNodes();
    }
}
