package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;

final class zzjb implements Runnable {
    private final /* synthetic */ zzix zzapw;

    zzjb(zzix zzix) {
        this.zzapw = zzix;
    }

    public final void run() {
        zzij zzij = this.zzapw.zzapn;
        Context context = this.zzapw.zzapn.getContext();
        this.zzapw.zzapn.zzgi();
        zzij.onServiceDisconnected(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementService"));
    }
}
