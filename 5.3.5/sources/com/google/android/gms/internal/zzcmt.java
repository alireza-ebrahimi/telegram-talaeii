package com.google.android.gms.internal;

final class zzcmt implements Runnable {
    private /* synthetic */ zzcjb zzjrr;
    private /* synthetic */ zzcms zzjrs;

    zzcmt(zzcms zzcms, zzcjb zzcjb) {
        this.zzjrs = zzcms;
        this.zzjrr = zzcjb;
    }

    public final void run() {
        synchronized (this.zzjrs) {
            this.zzjrs.zzjrp = false;
            if (!this.zzjrs.zzjri.isConnected()) {
                this.zzjrs.zzjri.zzayp().zzbba().log("Connected to service");
                this.zzjrs.zzjri.zza(this.zzjrr);
            }
        }
    }
}
