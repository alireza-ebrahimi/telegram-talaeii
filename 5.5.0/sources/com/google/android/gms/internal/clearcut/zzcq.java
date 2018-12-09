package com.google.android.gms.internal.clearcut;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public enum zzcq {
    VOID(Void.class, Void.class, null),
    INT(Integer.TYPE, Integer.class, Integer.valueOf(0)),
    LONG(Long.TYPE, Long.class, Long.valueOf(0)),
    FLOAT(Float.TYPE, Float.class, Float.valueOf(BitmapDescriptorFactory.HUE_RED)),
    DOUBLE(Double.TYPE, Double.class, Double.valueOf(0.0d)),
    BOOLEAN(Boolean.TYPE, Boolean.class, Boolean.valueOf(false)),
    STRING(String.class, String.class, TtmlNode.ANONYMOUS_REGION_ID),
    BYTE_STRING(zzbb.class, zzbb.class, zzbb.zzfi),
    ENUM(Integer.TYPE, Integer.class, null),
    MESSAGE(Object.class, Object.class, null);
    
    private final Class<?> zzlh;
    private final Class<?> zzli;
    private final Object zzlj;

    private zzcq(Class<?> cls, Class<?> cls2, Object obj) {
        this.zzlh = cls;
        this.zzli = cls2;
        this.zzlj = obj;
    }

    public final Class<?> zzbq() {
        return this.zzli;
    }
}
