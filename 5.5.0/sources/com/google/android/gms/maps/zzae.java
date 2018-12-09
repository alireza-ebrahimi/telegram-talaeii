package com.google.android.gms.maps;

import com.google.android.gms.maps.StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener;
import com.google.android.gms.maps.internal.zzbi;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

final class zzae extends zzbi {
    private final /* synthetic */ OnStreetViewPanoramaCameraChangeListener zzbp;

    zzae(StreetViewPanorama streetViewPanorama, OnStreetViewPanoramaCameraChangeListener onStreetViewPanoramaCameraChangeListener) {
        this.zzbp = onStreetViewPanoramaCameraChangeListener;
    }

    public final void onStreetViewPanoramaCameraChange(StreetViewPanoramaCamera streetViewPanoramaCamera) {
        this.zzbp.onStreetViewPanoramaCameraChange(streetViewPanoramaCamera);
    }
}
