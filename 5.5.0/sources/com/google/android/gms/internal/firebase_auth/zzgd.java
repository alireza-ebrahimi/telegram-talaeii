package com.google.android.gms.internal.firebase_auth;

public enum zzgd {
    DOUBLE(zzgi.DOUBLE, 1),
    FLOAT(zzgi.FLOAT, 5),
    INT64(zzgi.LONG, 0),
    UINT64(zzgi.LONG, 0),
    INT32(zzgi.INT, 0),
    FIXED64(zzgi.LONG, 1),
    FIXED32(zzgi.INT, 5),
    BOOL(zzgi.BOOLEAN, 0),
    STRING(zzgi.STRING, 2),
    GROUP(zzgi.MESSAGE, 3),
    MESSAGE(zzgi.MESSAGE, 2),
    BYTES(zzgi.BYTE_STRING, 2),
    UINT32(zzgi.INT, 0),
    ENUM(zzgi.ENUM, 0),
    SFIXED32(zzgi.INT, 5),
    SFIXED64(zzgi.LONG, 1),
    SINT32(zzgi.INT, 0),
    SINT64(zzgi.LONG, 0);
    
    private final zzgi zzwx;
    private final int zzwy;

    private zzgd(zzgi zzgi, int i) {
        this.zzwx = zzgi;
        this.zzwy = i;
    }

    public final zzgi zzgj() {
        return this.zzwx;
    }

    public final int zzgk() {
        return this.zzwy;
    }
}
