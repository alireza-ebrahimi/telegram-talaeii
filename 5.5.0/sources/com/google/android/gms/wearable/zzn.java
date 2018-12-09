package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzfo;

final class zzn implements Runnable {
    private final /* synthetic */ zzd zzao;
    private final /* synthetic */ zzfo zzaq;

    zzn(zzd zzd, zzfo zzfo) {
        this.zzao = zzd;
        this.zzaq = zzfo;
    }

    public final void run() {
        this.zzao.zzak.onPeerConnected(this.zzaq);
    }
}
