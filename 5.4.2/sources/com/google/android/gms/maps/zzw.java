package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnCameraMoveCanceledListener;
import com.google.android.gms.maps.internal.zzq;

final class zzw extends zzq {
    private final /* synthetic */ OnCameraMoveCanceledListener zzae;

    zzw(GoogleMap googleMap, OnCameraMoveCanceledListener onCameraMoveCanceledListener) {
        this.zzae = onCameraMoveCanceledListener;
    }

    public final void onCameraMoveCanceled() {
        this.zzae.onCameraMoveCanceled();
    }
}
