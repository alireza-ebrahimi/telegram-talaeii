package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.internal.zzs;

final class zzv extends zzs {
    private final /* synthetic */ OnCameraMoveListener zzad;

    zzv(GoogleMap googleMap, OnCameraMoveListener onCameraMoveListener) {
        this.zzad = onCameraMoveListener;
    }

    public final void onCameraMove() {
        this.zzad.onCameraMove();
    }
}
