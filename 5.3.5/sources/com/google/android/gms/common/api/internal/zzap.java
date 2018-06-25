package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.zzf;

final class zzap implements Runnable {
    private /* synthetic */ zzao zzfxt;

    zzap(zzao zzao) {
        this.zzfxt = zzao;
    }

    public final void run() {
        zzf.zzcf(this.zzfxt.mContext);
    }
}
