package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzl;

final class zzr implements Runnable {
    private final /* synthetic */ zzd zzao;
    private final /* synthetic */ zzl zzat;

    zzr(zzd zzd, zzl zzl) {
        this.zzao = zzd;
        this.zzat = zzl;
    }

    public final void run() {
        this.zzao.zzak.onNotificationReceived(this.zzat);
    }
}
