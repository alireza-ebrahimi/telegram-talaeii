package com.google.android.gms.internal.clearcut;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public enum zzfq {
    INT(Integer.valueOf(0)),
    LONG(Long.valueOf(0)),
    FLOAT(Float.valueOf(BitmapDescriptorFactory.HUE_RED)),
    DOUBLE(Double.valueOf(0.0d)),
    BOOLEAN(Boolean.valueOf(false)),
    STRING(TtmlNode.ANONYMOUS_REGION_ID),
    BYTE_STRING(zzbb.zzfi),
    ENUM(null),
    MESSAGE(null);
    
    private final Object zzlj;

    private zzfq(Object obj) {
        this.zzlj = obj;
    }
}
