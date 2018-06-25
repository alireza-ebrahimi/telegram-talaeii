package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzi;

final class zzs implements Runnable {
    private /* synthetic */ zzd zzlrr;
    private /* synthetic */ zzi zzlrx;

    zzs(zzd zzd, zzi zzi) {
        this.zzlrr = zzd;
        this.zzlrx = zzi;
    }

    public final void run() {
        this.zzlrr.zzlrn.onEntityUpdate(this.zzlrx);
    }
}
