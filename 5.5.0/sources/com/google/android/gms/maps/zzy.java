package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.internal.zzak;
import com.google.android.gms.maps.model.LatLng;

final class zzy extends zzak {
    private final /* synthetic */ OnMapClickListener zzag;

    zzy(GoogleMap googleMap, OnMapClickListener onMapClickListener) {
        this.zzag = onMapClickListener;
    }

    public final void onMapClick(LatLng latLng) {
        this.zzag.onMapClick(latLng);
    }
}
