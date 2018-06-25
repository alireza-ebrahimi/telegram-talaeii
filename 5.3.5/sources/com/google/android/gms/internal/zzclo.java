package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzclo implements Runnable {
    private /* synthetic */ String zziuw;
    private /* synthetic */ String zzjpm;
    private /* synthetic */ String zzjpn;
    private /* synthetic */ zzclk zzjpy;
    private /* synthetic */ AtomicReference zzjqa;

    zzclo(zzclk zzclk, AtomicReference atomicReference, String str, String str2, String str3) {
        this.zzjpy = zzclk;
        this.zzjqa = atomicReference;
        this.zziuw = str;
        this.zzjpm = str2;
        this.zzjpn = str3;
    }

    public final void run() {
        this.zzjpy.zzjev.zzayg().zza(this.zzjqa, this.zziuw, this.zzjpm, this.zzjpn);
    }
}
