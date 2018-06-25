package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.internal.zzp;

final class zzbs implements zzp {
    final /* synthetic */ zzbo zzgaa;

    zzbs(zzbo zzbo) {
        this.zzgaa = zzbo;
    }

    public final void zzako() {
        this.zzgaa.zzfzq.mHandler.post(new zzbt(this));
    }
}
