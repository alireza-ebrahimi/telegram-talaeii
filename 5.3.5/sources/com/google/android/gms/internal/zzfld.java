package com.google.android.gms.internal;

public enum zzfld {
    INT(Integer.valueOf(0)),
    LONG(Long.valueOf(0)),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(Double.valueOf(0.0d)),
    BOOLEAN(Boolean.valueOf(false)),
    STRING(""),
    BYTE_STRING(zzfgs.zzpnw),
    ENUM(null),
    MESSAGE(null);
    
    private final Object zzpvc;

    private zzfld(Object obj) {
        this.zzpvc = obj;
    }
}
