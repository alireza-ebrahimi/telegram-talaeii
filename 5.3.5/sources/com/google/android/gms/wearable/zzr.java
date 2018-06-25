package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzl;

final class zzr implements Runnable {
    private /* synthetic */ zzd zzlrr;
    private /* synthetic */ zzl zzlrw;

    zzr(zzd zzd, zzl zzl) {
        this.zzlrr = zzd;
        this.zzlrw = zzl;
    }

    public final void run() {
        this.zzlrr.zzlrn.onNotificationReceived(this.zzlrw);
    }
}
