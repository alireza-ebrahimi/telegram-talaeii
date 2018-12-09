package com.google.android.gms.measurement;

import android.content.BroadcastReceiver.PendingResult;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.C0429p;
import com.google.android.gms.internal.measurement.zzgc;
import com.google.android.gms.internal.measurement.zzgf;

public final class AppMeasurementReceiver extends C0429p implements zzgf {
    private zzgc zzadd;

    public final PendingResult doGoAsync() {
        return goAsync();
    }

    public final void doStartService(Context context, Intent intent) {
        C0429p.startWakefulService(context, intent);
    }

    public final void onReceive(Context context, Intent intent) {
        if (this.zzadd == null) {
            this.zzadd = new zzgc(this);
        }
        this.zzadd.onReceive(context, intent);
    }
}
