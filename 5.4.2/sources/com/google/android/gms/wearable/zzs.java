package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzi;

final class zzs implements Runnable {
    private final /* synthetic */ zzd zzao;
    private final /* synthetic */ zzi zzau;

    zzs(zzd zzd, zzi zzi) {
        this.zzao = zzd;
        this.zzau = zzi;
    }

    public final void run() {
        this.zzao.zzak.onEntityUpdate(this.zzau);
    }
}
