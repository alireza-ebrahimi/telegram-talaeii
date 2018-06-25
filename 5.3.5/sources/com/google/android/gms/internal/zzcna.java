package com.google.android.gms.internal;

import android.app.job.JobParameters;

final /* synthetic */ class zzcna implements Runnable {
    private final zzcmy zzjrv;
    private final zzcjj zzjry;
    private final JobParameters zzjrz;

    zzcna(zzcmy zzcmy, zzcjj zzcjj, JobParameters jobParameters) {
        this.zzjrv = zzcmy;
        this.zzjry = zzcjj;
        this.zzjrz = jobParameters;
    }

    public final void run() {
        this.zzjrv.zza(this.zzjry, this.zzjrz);
    }
}
