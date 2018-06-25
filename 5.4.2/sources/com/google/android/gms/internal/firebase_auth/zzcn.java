package com.google.android.gms.internal.firebase_auth;

final class zzcn {
    private static final Class<?> zzns = zzdg();

    private static final zzco zzao(String str) {
        return (zzco) zzns.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }

    private static Class<?> zzdg() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static zzco zzdh() {
        if (zzns != null) {
            try {
                return zzao("getEmptyRegistry");
            } catch (Exception e) {
            }
        }
        return zzco.zznw;
    }

    static zzco zzdi() {
        zzco zzco = null;
        if (zzns != null) {
            try {
                zzco = zzao("loadGeneratedRegistry");
            } catch (Exception e) {
            }
        }
        if (zzco == null) {
            zzco = zzco.zzdi();
        }
        return zzco == null ? zzdh() : zzco;
    }
}
