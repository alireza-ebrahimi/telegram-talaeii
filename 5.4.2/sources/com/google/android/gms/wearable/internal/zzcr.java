package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;

final /* synthetic */ class zzcr implements ResultConverter {
    static final ResultConverter zzbx = new zzcr();

    private zzcr() {
    }

    public final Object convert(Result result) {
        return new zzcu((GetFdForAssetResult) result);
    }
}
