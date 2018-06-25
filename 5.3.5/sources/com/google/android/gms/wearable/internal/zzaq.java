package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.Channel.GetInputStreamResult;

final /* synthetic */ class zzaq implements zzbo {
    static final zzbo zzgui = new zzaq();

    private zzaq() {
    }

    public final Object zzb(Result result) {
        return ((GetInputStreamResult) result).getInputStream();
    }
}
