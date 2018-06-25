package com.google.android.gms.internal;

import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzv;

final class zzchb extends zzv {
    private final zzci<LocationCallback> zzgbb;

    zzchb(zzci<LocationCallback> zzci) {
        this.zzgbb = zzci;
    }

    public final void onLocationAvailability(LocationAvailability locationAvailability) {
        this.zzgbb.zza(new zzchd(this, locationAvailability));
    }

    public final void onLocationResult(LocationResult locationResult) {
        this.zzgbb.zza(new zzchc(this, locationResult));
    }

    public final synchronized void release() {
        this.zzgbb.clear();
    }
}
