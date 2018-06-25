package com.google.android.gms.internal.measurement;

final class zzjp extends zzeo {
    private final /* synthetic */ zzjs zzaqc;
    private final /* synthetic */ zzjo zzaqi;

    zzjp(zzjo zzjo, zzhj zzhj, zzjs zzjs) {
        this.zzaqi = zzjo;
        this.zzaqc = zzjs;
        super(zzhj);
    }

    public final void run() {
        this.zzaqi.cancel();
        this.zzaqi.zzgf().zziz().log("Starting upload from DelayedRunnable");
        this.zzaqc.zzlb();
    }
}
