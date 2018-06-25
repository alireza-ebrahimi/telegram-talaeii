package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzclu implements Runnable {
    private /* synthetic */ zzclk zzjpy;
    private /* synthetic */ AtomicReference zzjqa;
    private /* synthetic */ boolean zzjqb;

    zzclu(zzclk zzclk, AtomicReference atomicReference, boolean z) {
        this.zzjpy = zzclk;
        this.zzjqa = atomicReference;
        this.zzjqb = z;
    }

    public final void run() {
        this.zzjpy.zzayg().zza(this.zzjqa, this.zzjqb);
    }
}
