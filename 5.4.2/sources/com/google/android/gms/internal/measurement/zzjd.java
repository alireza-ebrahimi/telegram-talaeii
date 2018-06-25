package com.google.android.gms.internal.measurement;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.common.internal.Preconditions;

public final class zzjd<T extends Context & zzjh> {
    private final T zzabm;

    public zzjd(T t) {
        Preconditions.checkNotNull(t);
        this.zzabm = t;
    }

    private final void zzb(Runnable runnable) {
        zzjs zzg = zzjs.zzg(this.zzabm);
        zzg.zzge().zzc(new zzjg(this, zzg, runnable));
    }

    private final zzfh zzgf() {
        return zzgm.zza(this.zzabm, null, null).zzgf();
    }

    public final IBinder onBind(Intent intent) {
        if (intent == null) {
            zzgf().zzis().log("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzgo(zzjs.zzg(this.zzabm));
        }
        zzgf().zziv().zzg("onBind received unknown action", action);
        return null;
    }

    public final void onCreate() {
        zzgm zza = zzgm.zza(this.zzabm, null, null);
        zzfh zzgf = zza.zzgf();
        zza.zzgi();
        zzgf.zziz().log("Local AppMeasurementService is starting up");
    }

    public final void onDestroy() {
        zzgm zza = zzgm.zza(this.zzabm, null, null);
        zzfh zzgf = zza.zzgf();
        zza.zzgi();
        zzgf.zziz().log("Local AppMeasurementService is shutting down");
    }

    public final void onRebind(Intent intent) {
        if (intent == null) {
            zzgf().zzis().log("onRebind called with null intent");
            return;
        }
        zzgf().zziz().zzg("onRebind called. action", intent.getAction());
    }

    public final int onStartCommand(Intent intent, int i, int i2) {
        zzgm zza = zzgm.zza(this.zzabm, null, null);
        zzfh zzgf = zza.zzgf();
        if (intent == null) {
            zzgf.zziv().log("AppMeasurementService started with null intent");
        } else {
            String action = intent.getAction();
            zza.zzgi();
            zzgf.zziz().zze("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
            if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
                zzb(new zzje(this, i2, zzgf, intent));
            }
        }
        return 2;
    }

    @TargetApi(24)
    public final boolean onStartJob(JobParameters jobParameters) {
        zzgm zza = zzgm.zza(this.zzabm, null, null);
        zzfh zzgf = zza.zzgf();
        String string = jobParameters.getExtras().getString("action");
        zza.zzgi();
        zzgf.zziz().zzg("Local AppMeasurementJobService called. action", string);
        if ("com.google.android.gms.measurement.UPLOAD".equals(string)) {
            zzb(new zzjf(this, zzgf, jobParameters));
        }
        return true;
    }

    public final boolean onUnbind(Intent intent) {
        if (intent == null) {
            zzgf().zzis().log("onUnbind called with null intent");
        } else {
            zzgf().zziz().zzg("onUnbind called for intent. action", intent.getAction());
        }
        return true;
    }

    final /* synthetic */ void zza(int i, zzfh zzfh, Intent intent) {
        if (((zzjh) this.zzabm).callServiceStopSelfResult(i)) {
            zzfh.zziz().zzg("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzgf().zziz().log("Completed wakeful intent.");
            ((zzjh) this.zzabm).zzb(intent);
        }
    }

    final /* synthetic */ void zza(zzfh zzfh, JobParameters jobParameters) {
        zzfh.zziz().log("AppMeasurementJobService processed last upload request.");
        ((zzjh) this.zzabm).zza(jobParameters, false);
    }
}
