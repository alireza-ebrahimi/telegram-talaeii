package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzfe;

final class zzm implements Runnable {
    private final /* synthetic */ zzd zzao;
    private final /* synthetic */ zzfe zzap;

    zzm(zzd zzd, zzfe zzfe) {
        this.zzao = zzd;
        this.zzap = zzfe;
    }

    public final void run() {
        this.zzao.zzak.onMessageReceived(this.zzap);
    }
}
