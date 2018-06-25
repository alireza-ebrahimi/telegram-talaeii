package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.internal.zzau;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.internal.zzp;

final class zzc extends zzau {
    private /* synthetic */ OnMarkerDragListener zzizv;

    zzc(GoogleMap googleMap, OnMarkerDragListener onMarkerDragListener) {
        this.zzizv = onMarkerDragListener;
    }

    public final void zzb(zzp zzp) {
        this.zzizv.onMarkerDragStart(new Marker(zzp));
    }

    public final void zzc(zzp zzp) {
        this.zzizv.onMarkerDragEnd(new Marker(zzp));
    }

    public final void zzd(zzp zzp) {
        this.zzizv.onMarkerDrag(new Marker(zzp));
    }
}
