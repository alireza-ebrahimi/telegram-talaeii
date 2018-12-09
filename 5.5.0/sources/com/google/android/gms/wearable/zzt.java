package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzaw;

final class zzt implements Runnable {
    private final /* synthetic */ zzd zzao;
    private final /* synthetic */ zzaw zzav;

    zzt(zzd zzd, zzaw zzaw) {
        this.zzao = zzd;
        this.zzav = zzaw;
    }

    public final void run() {
        this.zzav.zza(this.zzao.zzak);
        this.zzav.zza(this.zzao.zzak.zzaj);
    }
}
