package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzk;
import com.google.android.gms.maps.GoogleMap.OnGroundOverlayClickListener;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.GroundOverlay;

final class zzn extends zzy {
    private final /* synthetic */ OnGroundOverlayClickListener zzv;

    zzn(GoogleMap googleMap, OnGroundOverlayClickListener onGroundOverlayClickListener) {
        this.zzv = onGroundOverlayClickListener;
    }

    public final void zza(zzk zzk) {
        this.zzv.onGroundOverlayClick(new GroundOverlay(zzk));
    }
}
