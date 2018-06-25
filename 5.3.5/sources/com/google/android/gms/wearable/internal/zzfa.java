package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;

final /* synthetic */ class zzfa implements zzbo {
    static final zzbo zzgui = new zzfa();

    private zzfa() {
    }

    public final Object zzb(Result result) {
        return Integer.valueOf(((SendMessageResult) result).getRequestId());
    }
}
