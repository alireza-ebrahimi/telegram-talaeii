package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.internal.zzo;

final class zzx extends zzo {
    private final /* synthetic */ OnCameraIdleListener zzaf;

    zzx(GoogleMap googleMap, OnCameraIdleListener onCameraIdleListener) {
        this.zzaf = onCameraIdleListener;
    }

    public final void onCameraIdle() {
        this.zzaf.onCameraIdle();
    }
}
