package com.google.android.gms.maps;

import com.google.android.gms.maps.internal.ILocationSourceDelegate.zza;
import com.google.android.gms.maps.internal.zzah;

final class zzl extends zza {
    private /* synthetic */ LocationSource zzjae;

    zzl(GoogleMap googleMap, LocationSource locationSource) {
        this.zzjae = locationSource;
    }

    public final void activate(zzah zzah) {
        this.zzjae.activate(new zzm(this, zzah));
    }

    public final void deactivate() {
        this.zzjae.deactivate();
    }
}
