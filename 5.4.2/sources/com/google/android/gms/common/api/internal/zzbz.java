package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

final class zzbz implements Runnable {
    private final /* synthetic */ zzby zzlx;

    zzbz(zzby zzby) {
        this.zzlx = zzby;
    }

    public final void run() {
        this.zzlx.zzlw.zzg(new ConnectionResult(4));
    }
}
