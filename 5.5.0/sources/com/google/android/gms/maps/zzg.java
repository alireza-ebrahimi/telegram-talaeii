package com.google.android.gms.maps;

import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.internal.zzi;
import com.google.android.gms.maps.model.Marker;

final class zzg extends zzi {
    private final /* synthetic */ InfoWindowAdapter zzo;

    zzg(GoogleMap googleMap, InfoWindowAdapter infoWindowAdapter) {
        this.zzo = infoWindowAdapter;
    }

    public final IObjectWrapper zzh(zzt zzt) {
        return ObjectWrapper.wrap(this.zzo.getInfoWindow(new Marker(zzt)));
    }

    public final IObjectWrapper zzi(zzt zzt) {
        return ObjectWrapper.wrap(this.zzo.getInfoContents(new Marker(zzt)));
    }
}
