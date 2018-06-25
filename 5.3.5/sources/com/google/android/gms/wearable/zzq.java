package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzah;

final class zzq implements Runnable {
    private /* synthetic */ zzd zzlrr;
    private /* synthetic */ zzah zzlrv;

    zzq(zzd zzd, zzah zzah) {
        this.zzlrr = zzd;
        this.zzlrv = zzah;
    }

    public final void run() {
        this.zzlrr.zzlrn.onCapabilityChanged(this.zzlrv);
    }
}
