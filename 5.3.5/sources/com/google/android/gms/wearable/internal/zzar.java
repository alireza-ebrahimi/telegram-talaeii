package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.Channel.GetOutputStreamResult;

final /* synthetic */ class zzar implements zzbo {
    static final zzbo zzgui = new zzar();

    private zzar() {
    }

    public final Object zzb(Result result) {
        return ((GetOutputStreamResult) result).getOutputStream();
    }
}
