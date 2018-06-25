package com.google.android.gms.internal;

import android.content.ComponentName;

final class zzcmw implements Runnable {
    private /* synthetic */ zzcms zzjrs;

    zzcmw(zzcms zzcms) {
        this.zzjrs = zzcms;
    }

    public final void run() {
        this.zzjrs.zzjri.onServiceDisconnected(new ComponentName(this.zzjrs.zzjri.getContext(), "com.google.android.gms.measurement.AppMeasurementService"));
    }
}
