package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnPolygonClickListener;
import com.google.android.gms.maps.internal.zzbe;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.internal.zzs;

final class zzp extends zzbe {
    private /* synthetic */ OnPolygonClickListener zzjai;

    zzp(GoogleMap googleMap, OnPolygonClickListener onPolygonClickListener) {
        this.zzjai = onPolygonClickListener;
    }

    public final void zza(zzs zzs) {
        this.zzjai.onPolygonClick(new Polygon(zzs));
    }
}
