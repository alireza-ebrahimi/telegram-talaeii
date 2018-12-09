package com.google.android.gms.common.images.internal;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public final class ColorFilters {
    public static final ColorFilter COLOR_FILTER_BW = new ColorMatrixColorFilter(zzpv);
    private static final ColorMatrix zzpv;

    static {
        ColorMatrix colorMatrix = new ColorMatrix();
        zzpv = colorMatrix;
        colorMatrix.setSaturation(BitmapDescriptorFactory.HUE_RED);
    }

    private ColorFilters() {
    }
}
