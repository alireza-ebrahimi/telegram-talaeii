package com.google.android.gms.measurement;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.annotation.MainThread;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzcmy;
import com.google.android.gms.internal.zzcnc;

@TargetApi(24)
public final class AppMeasurementJobService extends JobService implements zzcnc {
    private zzcmy<AppMeasurementJobService> zzjfd;

    private final zzcmy<AppMeasurementJobService> zzaxy() {
        if (this.zzjfd == null) {
            this.zzjfd = new zzcmy(this);
        }
        return this.zzjfd;
    }

    @Hide
    public final boolean callServiceStopSelfResult(int i) {
        throw new UnsupportedOperationException();
    }

    @MainThread
    public final void onCreate() {
        super.onCreate();
        zzaxy().onCreate();
    }

    @MainThread
    public final void onDestroy() {
        zzaxy().onDestroy();
        super.onDestroy();
    }

    @MainThread
    public final void onRebind(Intent intent) {
        zzaxy().onRebind(intent);
    }

    public final boolean onStartJob(JobParameters jobParameters) {
        return zzaxy().onStartJob(jobParameters);
    }

    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @MainThread
    public final boolean onUnbind(Intent intent) {
        return zzaxy().onUnbind(intent);
    }

    @Hide
    @TargetApi(24)
    public final void zza(JobParameters jobParameters, boolean z) {
        jobFinished(jobParameters, false);
    }

    @Hide
    public final void zzl(Intent intent) {
    }
}
