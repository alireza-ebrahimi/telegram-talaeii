package com.google.android.gms.internal.measurement;

final class zzgd implements Runnable {
    private final /* synthetic */ zzgm zzalk;
    private final /* synthetic */ zzfh zzall;

    zzgd(zzgc zzgc, zzgm zzgm, zzfh zzfh) {
        this.zzalk = zzgm;
        this.zzall = zzfh;
    }

    public final void run() {
        if (this.zzalk.zzjw() == null) {
            this.zzall.zzis().log("Install Referrer Reporter is null");
        } else {
            this.zzalk.zzjw().zzjo();
        }
    }
}
