package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.internal.zzo;

final class zzx extends zzo {
    private /* synthetic */ OnCameraIdleListener zzjaq;

    zzx(GoogleMap googleMap, OnCameraIdleListener onCameraIdleListener) {
        this.zzjaq = onCameraIdleListener;
    }

    public final void onCameraIdle() {
        this.zzjaq.onCameraIdle();
    }
}
