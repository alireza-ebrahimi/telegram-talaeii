package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.internal.zzs;

final class zzv extends zzs {
    private /* synthetic */ OnCameraMoveListener zzjao;

    zzv(GoogleMap googleMap, OnCameraMoveListener onCameraMoveListener) {
        this.zzjao = onCameraMoveListener;
    }

    public final void onCameraMove() {
        this.zzjao.onCameraMove();
    }
}
