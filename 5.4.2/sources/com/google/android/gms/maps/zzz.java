package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.internal.zzao;
import com.google.android.gms.maps.model.LatLng;

final class zzz extends zzao {
    private final /* synthetic */ OnMapLongClickListener zzah;

    zzz(GoogleMap googleMap, OnMapLongClickListener onMapLongClickListener) {
        this.zzah = onMapLongClickListener;
    }

    public final void onMapLongClick(LatLng latLng) {
        this.zzah.onMapLongClick(latLng);
    }
}
