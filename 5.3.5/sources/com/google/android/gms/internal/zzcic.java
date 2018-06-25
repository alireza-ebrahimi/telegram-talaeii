package com.google.android.gms.internal;

final class zzcic implements Runnable {
    private /* synthetic */ String zzbiq;
    private /* synthetic */ long zzjfi;
    private /* synthetic */ zzcia zzjfj;

    zzcic(zzcia zzcia, String str, long j) {
        this.zzjfj = zzcia;
        this.zzbiq = str;
        this.zzjfi = j;
    }

    public final void run() {
        this.zzjfj.zze(this.zzbiq, this.zzjfi);
    }
}
