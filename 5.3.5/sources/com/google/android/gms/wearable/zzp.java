package com.google.android.gms.wearable;

import java.util.List;

final class zzp implements Runnable {
    private /* synthetic */ zzd zzlrr;
    private /* synthetic */ List zzlru;

    zzp(zzd zzd, List list) {
        this.zzlrr = zzd;
        this.zzlru = list;
    }

    public final void run() {
        this.zzlrr.zzlrn.onConnectedNodes(this.zzlru);
    }
}
