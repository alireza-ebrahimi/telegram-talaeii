package com.google.android.gms.measurement;

import android.content.BroadcastReceiver;
import android.content.BroadcastReceiver.PendingResult;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import com.google.android.gms.internal.zzcka;
import com.google.android.gms.internal.zzckc;

public final class AppMeasurementInstallReferrerReceiver extends BroadcastReceiver implements zzckc {
    private zzcka zzjfc;

    public final PendingResult doGoAsync() {
        return goAsync();
    }

    public final void doStartService(Context context, Intent intent) {
    }

    @MainThread
    public final void onReceive(Context context, Intent intent) {
        if (this.zzjfc == null) {
            this.zzjfc = new zzcka(this);
        }
        this.zzjfc.onReceive(context, intent);
    }
}
