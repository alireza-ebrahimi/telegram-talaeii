package com.google.android.gms.maps;

import android.location.Location;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.internal.zzay;

final class zzh extends zzay {
    private final /* synthetic */ OnMyLocationChangeListener zzp;

    zzh(GoogleMap googleMap, OnMyLocationChangeListener onMyLocationChangeListener) {
        this.zzp = onMyLocationChangeListener;
    }

    public final void zza(IObjectWrapper iObjectWrapper) {
        this.zzp.onMyLocationChange((Location) ObjectWrapper.unwrap(iObjectWrapper));
    }
}
