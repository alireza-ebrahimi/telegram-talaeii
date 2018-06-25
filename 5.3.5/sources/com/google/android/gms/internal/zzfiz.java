package com.google.android.gms.internal;

final class zzfiz {
    private static final zzfix zzpre = zzday();
    private static final zzfix zzprf = new zzfiy();

    static zzfix zzdaw() {
        return zzpre;
    }

    static zzfix zzdax() {
        return zzprf;
    }

    private static zzfix zzday() {
        try {
            return (zzfix) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
