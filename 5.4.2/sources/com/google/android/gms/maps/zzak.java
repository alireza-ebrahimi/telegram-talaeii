package com.google.android.gms.maps;

import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.zzaq;

final class zzak extends zzaq {
    private final /* synthetic */ OnMapReadyCallback zzbb;

    zzak(zza zza, OnMapReadyCallback onMapReadyCallback) {
        this.zzbb = onMapReadyCallback;
    }

    public final void zza(IGoogleMapDelegate iGoogleMapDelegate) {
        this.zzbb.onMapReady(new GoogleMap(iGoogleMapDelegate));
    }
}
