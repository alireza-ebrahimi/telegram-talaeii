package com.google.android.gms.wearable;

import com.google.android.gms.wearable.internal.zzaw;

final class zzt implements Runnable {
    private /* synthetic */ zzd zzlrr;
    private /* synthetic */ zzaw zzlry;

    zzt(zzd zzd, zzaw zzaw) {
        this.zzlrr = zzd;
        this.zzlry = zzaw;
    }

    public final void run() {
        this.zzlry.zza(this.zzlrr.zzlrn);
        this.zzlry.zza(this.zzlrr.zzlrn.zzlrm);
    }
}
