package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzfo;

final class zzn implements Runnable {
    private /* synthetic */ zzd zzlrr;
    private /* synthetic */ zzfo zzlrt;

    zzn(zzd zzd, zzfo zzfo) {
        this.zzlrr = zzd;
        this.zzlrt = zzfo;
    }

    public final void run() {
        this.zzlrr.zzlrn.onPeerConnected(this.zzlrt);
    }
}
