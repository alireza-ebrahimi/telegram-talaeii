package com.google.android.gms.internal.measurement;

final class zzic implements Runnable {
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ long zzaon;

    zzic(zzhl zzhl, long j) {
        this.zzaog = zzhl;
        this.zzaon = j;
    }

    public final void run() {
        this.zzaog.zzgg().zzaks.set(this.zzaon);
        this.zzaog.zzgf().zziy().zzg("Session timeout duration set", Long.valueOf(this.zzaon));
    }
}
