package com.google.android.gms.internal;

final class zzcjt implements Runnable {
    private /* synthetic */ boolean zzjli;
    private /* synthetic */ zzcjs zzjlj;

    zzcjt(zzcjs zzcjs, boolean z) {
        this.zzjlj = zzcjs;
        this.zzjli = z;
    }

    public final void run() {
        this.zzjlj.zzjev.zzbt(this.zzjli);
    }
}
