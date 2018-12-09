package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzw;
import com.google.android.gms.maps.GoogleMap.OnPolygonClickListener;
import com.google.android.gms.maps.internal.zzbe;
import com.google.android.gms.maps.model.Polygon;

final class zzp extends zzbe {
    private final /* synthetic */ OnPolygonClickListener zzx;

    zzp(GoogleMap googleMap, OnPolygonClickListener onPolygonClickListener) {
        this.zzx = onPolygonClickListener;
    }

    public final void zza(zzw zzw) {
        this.zzx.onPolygonClick(new Polygon(zzw));
    }
}
