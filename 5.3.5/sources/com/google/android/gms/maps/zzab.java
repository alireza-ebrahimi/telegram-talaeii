package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.zzaq;

final class zzab extends zzaq {
    private /* synthetic */ OnMapReadyCallback zzjbm;

    zzab(zza zza, OnMapReadyCallback onMapReadyCallback) {
        this.zzjbm = onMapReadyCallback;
    }

    public final void zza(IGoogleMapDelegate iGoogleMapDelegate) throws RemoteException {
        this.zzjbm.onMapReady(new GoogleMap(iGoogleMapDelegate));
    }
}
