package com.google.android.gms.internal.measurement;

final class zzia implements Runnable {
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ boolean zzaom;

    zzia(zzhl zzhl, boolean z) {
        this.zzaog = zzhl;
        this.zzaom = z;
    }

    public final void run() {
        this.zzaog.zzi(this.zzaom);
    }
}
