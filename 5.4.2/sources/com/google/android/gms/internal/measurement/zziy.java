package com.google.android.gms.internal.measurement;

final class zziy implements Runnable {
    private final /* synthetic */ zzez zzapv;
    private final /* synthetic */ zzix zzapw;

    zziy(zzix zzix, zzez zzez) {
        this.zzapw = zzix;
        this.zzapv = zzez;
    }

    public final void run() {
        synchronized (this.zzapw) {
            this.zzapw.zzapt = false;
            if (!this.zzapw.zzapn.isConnected()) {
                this.zzapw.zzapn.zzgf().zziz().log("Connected to service");
                this.zzapw.zzapn.zza(this.zzapv);
            }
        }
    }
}
