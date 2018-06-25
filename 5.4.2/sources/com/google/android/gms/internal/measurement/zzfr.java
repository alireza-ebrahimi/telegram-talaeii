package com.google.android.gms.internal.measurement;

final class zzfr implements Runnable {
    private final /* synthetic */ boolean zzajz;
    private final /* synthetic */ zzfq zzaka;

    zzfr(zzfq zzfq, boolean z) {
        this.zzaka = zzfq;
        this.zzajz = z;
    }

    public final void run() {
        this.zzaka.zzajy.zzm(this.zzajz);
    }
}
