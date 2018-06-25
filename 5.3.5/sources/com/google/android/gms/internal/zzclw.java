package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzclw implements Runnable {
    private /* synthetic */ zzclk zzjpy;
    private /* synthetic */ AtomicReference zzjqa;

    zzclw(zzclk zzclk, AtomicReference atomicReference) {
        this.zzjpy = zzclk;
        this.zzjqa = atomicReference;
    }

    public final void run() {
        this.zzjpy.zzayg().zza(this.zzjqa);
    }
}
