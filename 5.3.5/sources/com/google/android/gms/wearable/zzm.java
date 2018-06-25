package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzfe;

final class zzm implements Runnable {
    private /* synthetic */ zzd zzlrr;
    private /* synthetic */ zzfe zzlrs;

    zzm(zzd zzd, zzfe zzfe) {
        this.zzlrr = zzd;
        this.zzlrs = zzfe;
    }

    public final void run() {
        this.zzlrr.zzlrn.onMessageReceived(this.zzlrs);
    }
}
