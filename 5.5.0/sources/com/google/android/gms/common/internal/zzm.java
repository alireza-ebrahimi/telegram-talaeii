package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil.ResultConverter;

final class zzm implements ResultConverter<R, T> {
    private final /* synthetic */ Response zzus;

    zzm(Response response) {
        this.zzus = response;
    }

    public final /* synthetic */ Object convert(Result result) {
        this.zzus.setResult(result);
        return this.zzus;
    }
}
