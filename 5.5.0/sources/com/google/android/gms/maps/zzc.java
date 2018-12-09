package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.internal.zzau;
import com.google.android.gms.maps.model.Marker;

final class zzc extends zzau {
    private final /* synthetic */ OnMarkerDragListener zzk;

    zzc(GoogleMap googleMap, OnMarkerDragListener onMarkerDragListener) {
        this.zzk = onMarkerDragListener;
    }

    public final void zzb(zzt zzt) {
        this.zzk.onMarkerDragStart(new Marker(zzt));
    }

    public final void zzc(zzt zzt) {
        this.zzk.onMarkerDragEnd(new Marker(zzt));
    }

    public final void zzd(zzt zzt) {
        this.zzk.onMarkerDrag(new Marker(zzt));
    }
}
