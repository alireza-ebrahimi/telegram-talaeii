package com.google.android.gms.internal;

final class zzcmv implements Runnable {
    private /* synthetic */ zzcms zzjrs;
    private /* synthetic */ zzcjb zzjrt;

    zzcmv(zzcms zzcms, zzcjb zzcjb) {
        this.zzjrs = zzcms;
        this.zzjrt = zzcjb;
    }

    public final void run() {
        synchronized (this.zzjrs) {
            this.zzjrs.zzjrp = false;
            if (!this.zzjrs.zzjri.isConnected()) {
                this.zzjrs.zzjri.zzayp().zzbaz().log("Connected to remote service");
                this.zzjrs.zzjri.zza(this.zzjrt);
            }
        }
    }
}
