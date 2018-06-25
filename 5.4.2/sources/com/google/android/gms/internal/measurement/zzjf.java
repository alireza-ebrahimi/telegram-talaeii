package com.google.android.gms.internal.measurement;

import android.app.job.JobParameters;

final /* synthetic */ class zzjf implements Runnable {
    private final JobParameters zzabs;
    private final zzjd zzapy;
    private final zzfh zzaqb;

    zzjf(zzjd zzjd, zzfh zzfh, JobParameters jobParameters) {
        this.zzapy = zzjd;
        this.zzaqb = zzfh;
        this.zzabs = jobParameters;
    }

    public final void run() {
        this.zzapy.zza(this.zzaqb, this.zzabs);
    }
}
