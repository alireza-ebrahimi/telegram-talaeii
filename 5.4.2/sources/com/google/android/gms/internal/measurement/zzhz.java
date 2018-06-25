package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

final class zzhz implements Runnable {
    private final /* synthetic */ AtomicReference zzaof;
    private final /* synthetic */ zzhl zzaog;

    zzhz(zzhl zzhl, AtomicReference atomicReference) {
        this.zzaog = zzhl;
        this.zzaof = atomicReference;
    }

    public final void run() {
        synchronized (this.zzaof) {
            try {
                AtomicReference atomicReference = this.zzaof;
                zzhh zzgh = this.zzaog.zzgh();
                atomicReference.set(Double.valueOf(zzgh.zzc(zzgh.zzfw().zzah(), zzey.zzaia)));
                this.zzaof.notify();
            } catch (Throwable th) {
                this.zzaof.notify();
            }
        }
    }
}
