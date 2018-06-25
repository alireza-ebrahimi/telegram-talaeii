package com.google.android.gms.internal;

final class zzcll implements Runnable {
    private /* synthetic */ boolean zzeih;
    private /* synthetic */ zzclk zzjpy;

    zzcll(zzclk zzclk, boolean z) {
        this.zzjpy = zzclk;
        this.zzeih = z;
    }

    public final void run() {
        this.zzjpy.zzbu(this.zzeih);
    }
}
