package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzz;
import com.google.android.gms.maps.GoogleMap.OnPolylineClickListener;
import com.google.android.gms.maps.internal.zzbg;
import com.google.android.gms.maps.model.Polyline;

final class zzq extends zzbg {
    private final /* synthetic */ OnPolylineClickListener zzy;

    zzq(GoogleMap googleMap, OnPolylineClickListener onPolylineClickListener) {
        this.zzy = onPolylineClickListener;
    }

    public final void zza(zzz zzz) {
        this.zzy.onPolylineClick(new Polyline(zzz));
    }
}
