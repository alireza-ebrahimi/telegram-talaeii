package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

final class zzcw implements Runnable {
    private /* synthetic */ zzcv zzgbe;

    zzcw(zzcv zzcv) {
        this.zzgbe = zzcv;
    }

    public final void run() {
        this.zzgbe.zzgbd.zzh(new ConnectionResult(4));
    }
}
