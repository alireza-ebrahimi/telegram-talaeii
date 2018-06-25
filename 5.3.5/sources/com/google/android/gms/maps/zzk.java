package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.internal.zzam;

final class zzk extends zzam {
    private /* synthetic */ OnMapLoadedCallback zzjad;

    zzk(GoogleMap googleMap, OnMapLoadedCallback onMapLoadedCallback) {
        this.zzjad = onMapLoadedCallback;
    }

    public final void onMapLoaded() throws RemoteException {
        this.zzjad.onMapLoaded();
    }
}
