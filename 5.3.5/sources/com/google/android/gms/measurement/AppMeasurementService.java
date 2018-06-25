package com.google.android.gms.measurement;

import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.MainThread;
import android.support.v4.content.WakefulBroadcastReceiver;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzcmy;
import com.google.android.gms.internal.zzcnc;

public final class AppMeasurementService extends Service implements zzcnc {
    private zzcmy<AppMeasurementService> zzjfe;

    private final zzcmy<AppMeasurementService> zzaxy() {
        if (this.zzjfe == null) {
            this.zzjfe = new zzcmy(this);
        }
        return this.zzjfe;
    }

    @Hide
    public final boolean callServiceStopSelfResult(int i) {
        return stopSelfResult(i);
    }

    @MainThread
    public final IBinder onBind(Intent intent) {
        return zzaxy().onBind(intent);
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

    @MainThread
    public final int onStartCommand(Intent intent, int i, int i2) {
        return zzaxy().onStartCommand(intent, i, i2);
    }

    @MainThread
    public final boolean onUnbind(Intent intent) {
        return zzaxy().onUnbind(intent);
    }

    @Hide
    public final void zza(JobParameters jobParameters, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public final void zzl(Intent intent) {
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }
}
