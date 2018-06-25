package com.google.android.gms.internal;

final class zzcid implements Runnable {
    private /* synthetic */ long zzjfi;
    private /* synthetic */ zzcia zzjfj;

    zzcid(zzcia zzcia, long j) {
        this.zzjfj = zzcia;
        this.zzjfi = j;
    }

    public final void run() {
        this.zzjfj.zzak(this.zzjfi);
    }
}
