package com.google.android.gms.internal;

final class zzcks implements Runnable {
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ zzcii zzjpl;

    zzcks(zzcko zzcko, zzcii zzcii) {
        this.zzjpk = zzcko;
        this.zzjpl = zzcii;
    }

    public final void run() {
        this.zzjpk.zzjev.zzbcc();
        this.zzjpk.zzjev.zze(this.zzjpl);
    }
}
