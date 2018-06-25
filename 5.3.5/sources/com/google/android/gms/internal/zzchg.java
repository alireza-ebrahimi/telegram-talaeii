package com.google.android.gms.internal;

import android.location.Location;
import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.location.LocationListener;

final class zzchg implements zzcl<LocationListener> {
    private /* synthetic */ Location zziuj;

    zzchg(zzchf zzchf, Location location) {
        this.zziuj = location;
    }

    public final void zzajh() {
    }

    public final /* synthetic */ void zzu(Object obj) {
        ((LocationListener) obj).onLocationChanged(this.zziuj);
    }
}
