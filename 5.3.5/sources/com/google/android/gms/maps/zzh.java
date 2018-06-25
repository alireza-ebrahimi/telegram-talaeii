package com.google.android.gms.maps;

import android.location.Location;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.internal.zzay;

final class zzh extends zzay {
    private /* synthetic */ OnMyLocationChangeListener zzjaa;

    zzh(GoogleMap googleMap, OnMyLocationChangeListener onMyLocationChangeListener) {
        this.zzjaa = onMyLocationChangeListener;
    }

    public final void zzz(IObjectWrapper iObjectWrapper) {
        this.zzjaa.onMyLocationChange((Location) zzn.zzy(iObjectWrapper));
    }
}
