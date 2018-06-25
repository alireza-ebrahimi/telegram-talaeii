package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnGroundOverlayClickListener;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.internal.zzg;

final class zzn extends zzy {
    private /* synthetic */ OnGroundOverlayClickListener zzjag;

    zzn(GoogleMap googleMap, OnGroundOverlayClickListener onGroundOverlayClickListener) {
        this.zzjag = onGroundOverlayClickListener;
    }

    public final void zza(zzg zzg) {
        this.zzjag.onGroundOverlayClick(new GroundOverlay(zzg));
    }
}
