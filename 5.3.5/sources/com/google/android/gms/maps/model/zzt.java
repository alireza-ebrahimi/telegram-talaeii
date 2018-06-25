package com.google.android.gms.maps.model;

import com.google.android.gms.maps.model.internal.zzaa;

final class zzt extends zzaa {
    private /* synthetic */ TileProvider zzjeu;

    zzt(TileOverlayOptions tileOverlayOptions, TileProvider tileProvider) {
        this.zzjeu = tileProvider;
    }

    public final Tile getTile(int i, int i2, int i3) {
        return this.zzjeu.getTile(i, i2, i3);
    }
}
