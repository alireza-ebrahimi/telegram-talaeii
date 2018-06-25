package com.google.android.gms.internal;

import android.location.Location;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.zzy;

final class zzchf extends zzy {
    private final zzci<LocationListener> zzgbb;

    zzchf(zzci<LocationListener> zzci) {
        this.zzgbb = zzci;
    }

    public final synchronized void onLocationChanged(Location location) {
        this.zzgbb.zza(new zzchg(this, location));
    }

    public final synchronized void release() {
        this.zzgbb.clear();
    }
}
