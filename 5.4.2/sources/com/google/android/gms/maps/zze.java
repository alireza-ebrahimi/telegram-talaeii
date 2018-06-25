package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowLongClickListener;
import com.google.android.gms.maps.internal.zzag;
import com.google.android.gms.maps.model.Marker;

final class zze extends zzag {
    private final /* synthetic */ OnInfoWindowLongClickListener zzm;

    zze(GoogleMap googleMap, OnInfoWindowLongClickListener onInfoWindowLongClickListener) {
        this.zzm = onInfoWindowLongClickListener;
    }

    public final void zzf(zzt zzt) {
        this.zzm.onInfoWindowLongClick(new Marker(zzt));
    }
}
