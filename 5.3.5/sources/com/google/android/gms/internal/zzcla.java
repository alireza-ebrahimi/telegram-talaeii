package com.google.android.gms.internal;

final class zzcla implements Runnable {
    private /* synthetic */ String zziuw;
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ zzcix zzjpo;

    zzcla(zzcko zzcko, zzcix zzcix, String str) {
        this.zzjpk = zzcko;
        this.zzjpo = zzcix;
        this.zziuw = str;
    }

    public final void run() {
        this.zzjpk.zzjev.zzbcc();
        this.zzjpk.zzjev.zzb(this.zzjpo, this.zziuw);
    }
}
