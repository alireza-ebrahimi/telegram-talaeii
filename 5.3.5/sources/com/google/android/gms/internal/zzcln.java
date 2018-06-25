package com.google.android.gms.internal;

import com.google.android.gms.measurement.AppMeasurement.ConditionalUserProperty;

final class zzcln implements Runnable {
    private /* synthetic */ zzclk zzjpy;
    private /* synthetic */ ConditionalUserProperty zzjpz;

    zzcln(zzclk zzclk, ConditionalUserProperty conditionalUserProperty) {
        this.zzjpy = zzclk;
        this.zzjpz = conditionalUserProperty;
    }

    public final void run() {
        this.zzjpy.zzc(this.zzjpz);
    }
}
