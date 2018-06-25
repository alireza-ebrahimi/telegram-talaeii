package com.google.android.gms.internal;

final class zzcmc implements Runnable {
    private /* synthetic */ zzcma zzjqy;
    private /* synthetic */ zzcmd zzjqz;

    zzcmc(zzcma zzcma, zzcmd zzcmd) {
        this.zzjqy = zzcma;
        this.zzjqz = zzcmd;
    }

    public final void run() {
        this.zzjqy.zza(this.zzjqz);
        this.zzjqy.zzjqm = null;
        this.zzjqy.zzayg().zza(null);
    }
}
