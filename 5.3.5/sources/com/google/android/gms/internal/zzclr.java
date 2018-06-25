package com.google.android.gms.internal;

final class zzclr implements Runnable {
    private /* synthetic */ zzclk zzjpy;
    private /* synthetic */ long zzjqc;

    zzclr(zzclk zzclk, long j) {
        this.zzjpy = zzclk;
        this.zzjqc = j;
    }

    public final void run() {
        this.zzjpy.zzayq().zzjmb.set(this.zzjqc);
        this.zzjpy.zzayp().zzbaz().zzj("Session timeout duration set", Long.valueOf(this.zzjqc));
    }
}
