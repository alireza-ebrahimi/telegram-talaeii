package com.google.android.gms.internal.measurement;

import com.google.android.gms.measurement.AppMeasurement.ConditionalUserProperty;

final class zzhs implements Runnable {
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ ConditionalUserProperty zzaol;

    zzhs(zzhl zzhl, ConditionalUserProperty conditionalUserProperty) {
        this.zzaog = zzhl;
        this.zzaol = conditionalUserProperty;
    }

    public final void run() {
        this.zzaog.zzb(this.zzaol);
    }
}
