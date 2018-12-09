package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.internal.maps.zzaf;

final class zzs implements TileProvider {
    private final zzaf zzek = this.zzel.zzeh;
    private final /* synthetic */ TileOverlayOptions zzel;

    zzs(TileOverlayOptions tileOverlayOptions) {
        this.zzel = tileOverlayOptions;
    }

    public final Tile getTile(int i, int i2, int i3) {
        try {
            return this.zzek.getTile(i, i2, i3);
        } catch (RemoteException e) {
            return null;
        }
    }
}
