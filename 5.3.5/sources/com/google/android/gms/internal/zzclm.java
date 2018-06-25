package com.google.android.gms.internal;

import com.google.android.gms.measurement.AppMeasurement.ConditionalUserProperty;

final class zzclm implements Runnable {
    private /* synthetic */ zzclk zzjpy;
    private /* synthetic */ ConditionalUserProperty zzjpz;

    zzclm(zzclk zzclk, ConditionalUserProperty conditionalUserProperty) {
        this.zzjpy = zzclk;
        this.zzjpz = conditionalUserProperty;
    }

    public final void run() {
        this.zzjpy.zzb(this.zzjpz);
    }
}
