package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowCloseListener;
import com.google.android.gms.maps.internal.zzae;
import com.google.android.gms.maps.model.Marker;

final class zzf extends zzae {
    private final /* synthetic */ OnInfoWindowCloseListener zzn;

    zzf(GoogleMap googleMap, OnInfoWindowCloseListener onInfoWindowCloseListener) {
        this.zzn = onInfoWindowCloseListener;
    }

    public final void zzg(zzt zzt) {
        this.zzn.onInfoWindowClose(new Marker(zzt));
    }
}
