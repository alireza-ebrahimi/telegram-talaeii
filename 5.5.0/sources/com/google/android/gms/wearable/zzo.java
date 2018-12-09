package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzfo;

final class zzo implements Runnable {
    private final /* synthetic */ zzd zzao;
    private final /* synthetic */ zzfo zzaq;

    zzo(zzd zzd, zzfo zzfo) {
        this.zzao = zzd;
        this.zzaq = zzfo;
    }

    public final void run() {
        this.zzao.zzak.onPeerDisconnected(this.zzaq);
    }
}
