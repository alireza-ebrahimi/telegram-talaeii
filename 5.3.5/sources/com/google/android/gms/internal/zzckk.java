package com.google.android.gms.internal;

final class zzckk implements Runnable {
    private /* synthetic */ zzclj zzjpc;
    private /* synthetic */ zzckj zzjpd;

    zzckk(zzckj zzckj, zzclj zzclj) {
        this.zzjpd = zzckj;
        this.zzjpc = zzclj;
    }

    public final void run() {
        this.zzjpd.zza(this.zzjpc);
        this.zzjpd.start();
    }
}
