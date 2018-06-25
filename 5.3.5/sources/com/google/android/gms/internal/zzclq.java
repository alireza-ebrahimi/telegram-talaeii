package com.google.android.gms.internal;

final class zzclq implements Runnable {
    private /* synthetic */ zzclk zzjpy;
    private /* synthetic */ long zzjqc;

    zzclq(zzclk zzclk, long j) {
        this.zzjpy = zzclk;
        this.zzjqc = j;
    }

    public final void run() {
        this.zzjpy.zzayq().zzjma.set(this.zzjqc);
        this.zzjpy.zzayp().zzbaz().zzj("Minimum session duration set", Long.valueOf(this.zzjqc));
    }
}
