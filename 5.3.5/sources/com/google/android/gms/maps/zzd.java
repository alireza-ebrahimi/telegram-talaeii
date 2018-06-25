package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.internal.zzac;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.internal.zzp;

final class zzd extends zzac {
    private /* synthetic */ OnInfoWindowClickListener zzizw;

    zzd(GoogleMap googleMap, OnInfoWindowClickListener onInfoWindowClickListener) {
        this.zzizw = onInfoWindowClickListener;
    }

    public final void zze(zzp zzp) {
        this.zzizw.onInfoWindowClick(new Marker(zzp));
    }
}
