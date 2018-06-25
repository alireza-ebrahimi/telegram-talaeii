package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;

final class zzbm implements zzbo<R, T> {
    private /* synthetic */ Response zzghu;

    zzbm(Response response) {
        this.zzghu = response;
    }

    public final /* synthetic */ Object zzb(Result result) {
        this.zzghu.setResult(result);
        return this.zzghu;
    }
}
