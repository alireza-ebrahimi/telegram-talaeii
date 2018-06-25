package com.google.android.gms.internal;

final class zzfhl {
    private static Class<?> zzpot = zzczc();

    private static Class<?> zzczc() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static zzfhm zzczd() {
        if (zzpot != null) {
            try {
                return zzty("getEmptyRegistry");
            } catch (Exception e) {
            }
        }
        return zzfhm.zzpow;
    }

    private static final zzfhm zzty(String str) throws Exception {
        return (zzfhm) zzpot.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }
}
