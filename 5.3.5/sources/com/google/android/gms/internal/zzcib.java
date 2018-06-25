package com.google.android.gms.internal;

final class zzcib implements Runnable {
    private /* synthetic */ String zzbiq;
    private /* synthetic */ long zzjfi;
    private /* synthetic */ zzcia zzjfj;

    zzcib(zzcia zzcia, String str, long j) {
        this.zzjfj = zzcia;
        this.zzbiq = str;
        this.zzjfi = j;
    }

    public final void run() {
        this.zzjfj.zzd(this.zzbiq, this.zzjfi);
    }
}
