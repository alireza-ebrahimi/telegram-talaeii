package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public enum zzdj {
    VOID(Void.class, Void.class, null),
    INT(Integer.TYPE, Integer.class, Integer.valueOf(0)),
    LONG(Long.TYPE, Long.class, Long.valueOf(0)),
    FLOAT(Float.TYPE, Float.class, Float.valueOf(BitmapDescriptorFactory.HUE_RED)),
    DOUBLE(Double.TYPE, Double.class, Double.valueOf(0.0d)),
    BOOLEAN(Boolean.TYPE, Boolean.class, Boolean.valueOf(false)),
    STRING(String.class, String.class, TtmlNode.ANONYMOUS_REGION_ID),
    BYTE_STRING(zzbu.class, zzbu.class, zzbu.zzmi),
    ENUM(Integer.TYPE, Integer.class, null),
    MESSAGE(Object.class, Object.class, null);
    
    private final Class<?> zzsh;
    private final Class<?> zzsi;
    private final Object zzsj;

    private zzdj(Class<?> cls, Class<?> cls2, Object obj) {
        this.zzsh = cls;
        this.zzsi = cls2;
        this.zzsj = obj;
    }

    public final Class<?> zzel() {
        return this.zzsi;
    }
}
