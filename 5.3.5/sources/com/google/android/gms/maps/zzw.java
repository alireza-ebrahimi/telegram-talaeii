package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnCameraMoveCanceledListener;
import com.google.android.gms.maps.internal.zzq;

final class zzw extends zzq {
    private /* synthetic */ OnCameraMoveCanceledListener zzjap;

    zzw(GoogleMap googleMap, OnCameraMoveCanceledListener onCameraMoveCanceledListener) {
        this.zzjap = onCameraMoveCanceledListener;
    }

    public final void onCameraMoveCanceled() {
        this.zzjap.onCameraMoveCanceled();
    }
}
