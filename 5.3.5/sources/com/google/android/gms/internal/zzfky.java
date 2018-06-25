package com.google.android.gms.internal;

public enum zzfky {
    DOUBLE(zzfld.DOUBLE, 1),
    FLOAT(zzfld.FLOAT, 5),
    INT64(zzfld.LONG, 0),
    UINT64(zzfld.LONG, 0),
    INT32(zzfld.INT, 0),
    FIXED64(zzfld.LONG, 1),
    FIXED32(zzfld.INT, 5),
    BOOL(zzfld.BOOLEAN, 0),
    STRING(zzfld.STRING, 2),
    GROUP(zzfld.MESSAGE, 3),
    MESSAGE(zzfld.MESSAGE, 2),
    BYTES(zzfld.BYTE_STRING, 2),
    UINT32(zzfld.INT, 0),
    ENUM(zzfld.ENUM, 0),
    SFIXED32(zzfld.INT, 5),
    SFIXED64(zzfld.LONG, 1),
    SINT32(zzfld.INT, 0),
    SINT64(zzfld.LONG, 0);
    
    private final zzfld zzpuq;
    private final int zzpur;

    private zzfky(zzfld zzfld, int i) {
        this.zzpuq = zzfld;
        this.zzpur = i;
    }

    public final zzfld zzdci() {
        return this.zzpuq;
    }

    public final int zzdcj() {
        return this.zzpur;
    }
}
