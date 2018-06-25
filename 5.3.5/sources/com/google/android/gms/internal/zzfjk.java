package com.google.android.gms.internal;

final class zzfjk {
    private static final zzfji zzprk = zzdbe();
    private static final zzfji zzprl = new zzfjj();

    static zzfji zzdbc() {
        return zzprk;
    }

    static zzfji zzdbd() {
        return zzprl;
    }

    private static zzfji zzdbe() {
        try {
            return (zzfji) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
