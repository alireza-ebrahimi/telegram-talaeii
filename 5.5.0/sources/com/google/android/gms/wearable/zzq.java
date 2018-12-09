package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzah;

final class zzq implements Runnable {
    private final /* synthetic */ zzd zzao;
    private final /* synthetic */ zzah zzas;

    zzq(zzd zzd, zzah zzah) {
        this.zzao = zzd;
        this.zzas = zzah;
    }

    public final void run() {
        this.zzao.zzak.onCapabilityChanged(this.zzas);
    }
}
