package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener;

final class zzu extends com.google.android.gms.maps.internal.zzu {
    private final /* synthetic */ OnCameraMoveStartedListener zzac;

    zzu(GoogleMap googleMap, OnCameraMoveStartedListener onCameraMoveStartedListener) {
        this.zzac = onCameraMoveStartedListener;
    }

    public final void onCameraMoveStarted(int i) {
        this.zzac.onCameraMoveStarted(i);
    }
}
