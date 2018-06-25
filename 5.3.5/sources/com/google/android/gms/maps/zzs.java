package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.GoogleMap.OnPoiClickListener;
import com.google.android.gms.maps.internal.zzbc;
import com.google.android.gms.maps.model.PointOfInterest;

final class zzs extends zzbc {
    private /* synthetic */ OnPoiClickListener zzjal;

    zzs(GoogleMap googleMap, OnPoiClickListener onPoiClickListener) {
        this.zzjal = onPoiClickListener;
    }

    public final void zza(PointOfInterest pointOfInterest) throws RemoteException {
        this.zzjal.onPoiClick(pointOfInterest);
    }
}
