package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.internal.zzam;

final class zzk extends zzam {
    private final /* synthetic */ OnMapLoadedCallback zzs;

    zzk(GoogleMap googleMap, OnMapLoadedCallback onMapLoadedCallback) {
        this.zzs = onMapLoadedCallback;
    }

    public final void onMapLoaded() {
        this.zzs.onMapLoaded();
    }
}
