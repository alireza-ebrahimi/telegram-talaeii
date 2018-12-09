package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.internal.zzas;
import com.google.android.gms.maps.model.Marker;

final class zzb extends zzas {
    private final /* synthetic */ OnMarkerClickListener zzj;

    zzb(GoogleMap googleMap, OnMarkerClickListener onMarkerClickListener) {
        this.zzj = onMarkerClickListener;
    }

    public final boolean zza(zzt zzt) {
        return this.zzj.onMarkerClick(new Marker(zzt));
    }
}
