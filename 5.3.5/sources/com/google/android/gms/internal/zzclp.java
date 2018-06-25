package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzclp implements Runnable {
    private /* synthetic */ String zziuw;
    private /* synthetic */ String zzjpm;
    private /* synthetic */ String zzjpn;
    private /* synthetic */ zzclk zzjpy;
    private /* synthetic */ AtomicReference zzjqa;
    private /* synthetic */ boolean zzjqb;

    zzclp(zzclk zzclk, AtomicReference atomicReference, String str, String str2, String str3, boolean z) {
        this.zzjpy = zzclk;
        this.zzjqa = atomicReference;
        this.zziuw = str;
        this.zzjpm = str2;
        this.zzjpn = str3;
        this.zzjqb = z;
    }

    public final void run() {
        this.zzjpy.zzjev.zzayg().zza(this.zzjqa, this.zziuw, this.zzjpm, this.zzjpn, this.zzjqb);
    }
}
