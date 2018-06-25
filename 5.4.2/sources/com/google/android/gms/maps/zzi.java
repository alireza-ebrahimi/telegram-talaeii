package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.internal.zzaw;

final class zzi extends zzaw {
    private final /* synthetic */ OnMyLocationButtonClickListener zzq;

    zzi(GoogleMap googleMap, OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
        this.zzq = onMyLocationButtonClickListener;
    }

    public final boolean onMyLocationButtonClick() {
        return this.zzq.onMyLocationButtonClick();
    }
}
