package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.annotation.MainThread;
import com.google.android.gms.common.internal.zzbq;

public final class zzcmy<T extends Context & zzcnc> {
    private final T zzjru;

    public zzcmy(T t) {
        zzbq.checkNotNull(t);
        this.zzjru = t;
    }

    private final zzcjj zzayp() {
        return zzckj.zzed(this.zzjru).zzayp();
    }

    public static boolean zzg(Context context, boolean z) {
        zzbq.checkNotNull(context);
        return VERSION.SDK_INT >= 24 ? zzcno.zzp(context, "com.google.android.gms.measurement.AppMeasurementJobService") : zzcno.zzp(context, "com.google.android.gms.measurement.AppMeasurementService");
    }

    private final void zzl(Runnable runnable) {
        zzckj zzed = zzckj.zzed(this.zzjru);
        zzed.zzayp();
        zzed.zzayo().zzh(new zzcnb(this, zzed, runnable));
    }

    @MainThread
    public final IBinder onBind(Intent intent) {
        if (intent == null) {
            zzayp().zzbau().log("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzcko(zzckj.zzed(this.zzjru));
        }
        zzayp().zzbaw().zzj("onBind received unknown action", action);
        return null;
    }

    @MainThread
    public final void onCreate() {
        zzckj.zzed(this.zzjru).zzayp().zzbba().log("Local AppMeasurementService is starting up");
    }

    @MainThread
    public final void onDestroy() {
        zzckj.zzed(this.zzjru).zzayp().zzbba().log("Local AppMeasurementService is shutting down");
    }

    @MainThread
    public final void onRebind(Intent intent) {
        if (intent == null) {
            zzayp().zzbau().log("onRebind called with null intent");
            return;
        }
        zzayp().zzbba().zzj("onRebind called. action", intent.getAction());
    }

    @MainThread
    public final int onStartCommand(Intent intent, int i, int i2) {
        zzcjj zzayp = zzckj.zzed(this.zzjru).zzayp();
        if (intent == null) {
            zzayp.zzbaw().log("AppMeasurementService started with null intent");
        } else {
            String action = intent.getAction();
            zzayp.zzbba().zze("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
            if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
                zzl(new zzcmz(this, i2, zzayp, intent));
            }
        }
        return 2;
    }

    @TargetApi(24)
    @MainThread
    public final boolean onStartJob(JobParameters jobParameters) {
        zzcjj zzayp = zzckj.zzed(this.zzjru).zzayp();
        String string = jobParameters.getExtras().getString("action");
        zzayp.zzbba().zzj("Local AppMeasurementJobService called. action", string);
        if ("com.google.android.gms.measurement.UPLOAD".equals(string)) {
            zzl(new zzcna(this, zzayp, jobParameters));
        }
        return true;
    }

    @MainThread
    public final boolean onUnbind(Intent intent) {
        if (intent == null) {
            zzayp().zzbau().log("onUnbind called with null intent");
        } else {
            zzayp().zzbba().zzj("onUnbind called for intent. action", intent.getAction());
        }
        return true;
    }

    final /* synthetic */ void zza(int i, zzcjj zzcjj, Intent intent) {
        if (((zzcnc) this.zzjru).callServiceStopSelfResult(i)) {
            zzcjj.zzbba().zzj("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzayp().zzbba().log("Completed wakeful intent.");
            ((zzcnc) this.zzjru).zzl(intent);
        }
    }

    final /* synthetic */ void zza(zzcjj zzcjj, JobParameters jobParameters) {
        zzcjj.zzbba().log("AppMeasurementJobService processed last upload request.");
        ((zzcnc) this.zzjru).zza(jobParameters, false);
    }
}
