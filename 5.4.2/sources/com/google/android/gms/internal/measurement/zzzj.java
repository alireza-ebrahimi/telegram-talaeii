package com.google.android.gms.internal.measurement;

final class zzzj {
    private static final Class<?> zzbrv = zztk();

    private static final zzzk zzfh(String str) {
        return (zzzk) zzbrv.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }

    private static Class<?> zztk() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static zzzk zztl() {
        if (zzbrv != null) {
            try {
                return zzfh("getEmptyRegistry");
            } catch (Exception e) {
            }
        }
        return zzzk.zzbry;
    }
}
