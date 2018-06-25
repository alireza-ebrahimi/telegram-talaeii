package com.google.firebase.iid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Hide
public final class zzk {
    private static zzk zzokw;
    private final Context zzaiq;
    private final ScheduledExecutorService zzind;
    private int zzinf = 1;
    private zzm zzokx = new zzm();

    @VisibleForTesting
    private zzk(Context context, ScheduledExecutorService scheduledExecutorService) {
        this.zzind = scheduledExecutorService;
        this.zzaiq = context.getApplicationContext();
    }

    private final synchronized <T> Task<T> zza(zzt<T> zzt) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(zzt);
            Log.d("MessengerIpcClient", new StringBuilder(String.valueOf(valueOf).length() + 9).append("Queueing ").append(valueOf).toString());
        }
        if (!this.zzokx.zzb(zzt)) {
            this.zzokx = new zzm();
            this.zzokx.zzb(zzt);
        }
        return zzt.zzgyc.getTask();
    }

    private final synchronized int zzaws() {
        int i;
        i = this.zzinf;
        this.zzinf = i + 1;
        return i;
    }

    public static synchronized zzk zzfa(Context context) {
        zzk zzk;
        synchronized (zzk.class) {
            if (zzokw == null) {
                zzokw = new zzk(context, Executors.newSingleThreadScheduledExecutor());
            }
            zzk = zzokw;
        }
        return zzk;
    }

    public final Task<Bundle> zzj(int i, Bundle bundle) {
        return zza(new zzv(zzaws(), 1, bundle));
    }

    public final Task<Void> zzm(int i, Bundle bundle) {
        return zza(new zzs(zzaws(), 2, bundle));
    }
}
