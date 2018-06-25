package com.google.android.gms.internal.measurement;

final class zzja implements Runnable {
    private final /* synthetic */ zzix zzapw;
    private final /* synthetic */ zzez zzapx;

    zzja(zzix zzix, zzez zzez) {
        this.zzapw = zzix;
        this.zzapx = zzez;
    }

    public final void run() {
        synchronized (this.zzapw) {
            this.zzapw.zzapt = false;
            if (!this.zzapw.zzapn.isConnected()) {
                this.zzapw.zzapn.zzgf().zziy().log("Connected to remote service");
                this.zzapw.zzapn.zza(this.zzapx);
            }
        }
    }
}
