package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener;

final class zzu extends com.google.android.gms.maps.internal.zzu {
    private /* synthetic */ OnCameraMoveStartedListener zzjan;

    zzu(GoogleMap googleMap, OnCameraMoveStartedListener onCameraMoveStartedListener) {
        this.zzjan = onCameraMoveStartedListener;
    }

    public final void onCameraMoveStarted(int i) {
        this.zzjan.onCameraMoveStarted(i);
    }
}
