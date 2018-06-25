package com.google.android.gms.internal.measurement;

public enum zzabr {
    DOUBLE(zzabw.DOUBLE, 1),
    FLOAT(zzabw.FLOAT, 5),
    INT64(zzabw.LONG, 0),
    UINT64(zzabw.LONG, 0),
    INT32(zzabw.INT, 0),
    FIXED64(zzabw.LONG, 1),
    FIXED32(zzabw.INT, 5),
    BOOL(zzabw.BOOLEAN, 0),
    STRING(zzabw.STRING, 2),
    GROUP(zzabw.MESSAGE, 3),
    MESSAGE(zzabw.MESSAGE, 2),
    BYTES(zzabw.BYTE_STRING, 2),
    UINT32(zzabw.INT, 0),
    ENUM(zzabw.ENUM, 0),
    SFIXED32(zzabw.INT, 5),
    SFIXED64(zzabw.LONG, 1),
    SINT32(zzabw.INT, 0),
    SINT64(zzabw.LONG, 0);
    
    private final zzabw zzbwl;
    private final int zzbwm;

    private zzabr(zzabw zzabw, int i) {
        this.zzbwl = zzabw;
        this.zzbwm = i;
    }

    public final zzabw zzve() {
        return this.zzbwl;
    }
}
