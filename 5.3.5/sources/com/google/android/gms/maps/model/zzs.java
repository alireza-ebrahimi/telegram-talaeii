package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.maps.model.internal.zzz;

final class zzs implements TileProvider {
    private final zzz zzjes = this.zzjet.zzjep;
    private /* synthetic */ TileOverlayOptions zzjet;

    zzs(TileOverlayOptions tileOverlayOptions) {
        this.zzjet = tileOverlayOptions;
    }

    public final Tile getTile(int i, int i2, int i3) {
        try {
            return this.zzjes.getTile(i, i2, i3);
        } catch (RemoteException e) {
            return null;
        }
    }
}
