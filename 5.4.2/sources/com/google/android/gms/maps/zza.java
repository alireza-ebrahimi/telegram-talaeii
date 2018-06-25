package com.google.android.gms.maps;

import com.google.android.gms.internal.maps.zzn;
import com.google.android.gms.maps.GoogleMap.OnIndoorStateChangeListener;
import com.google.android.gms.maps.internal.zzaa;
import com.google.android.gms.maps.model.IndoorBuilding;

final class zza extends zzaa {
    private final /* synthetic */ OnIndoorStateChangeListener zzi;

    zza(GoogleMap googleMap, OnIndoorStateChangeListener onIndoorStateChangeListener) {
        this.zzi = onIndoorStateChangeListener;
    }

    public final void onIndoorBuildingFocused() {
        this.zzi.onIndoorBuildingFocused();
    }

    public final void zza(zzn zzn) {
        this.zzi.onIndoorLevelActivated(new IndoorBuilding(zzn));
    }
}
