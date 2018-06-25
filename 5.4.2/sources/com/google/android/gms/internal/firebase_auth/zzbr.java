package com.google.android.gms.internal.firebase_auth;

final class zzbr {
    private static final Class<?> zzme = zzaj("libcore.io.Memory");
    private static final boolean zzmf = (zzaj("org.robolectric.Robolectric") != null);

    private static <T> Class<T> zzaj(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable th) {
            return null;
        }
    }

    static boolean zzbu() {
        return (zzme == null || zzmf) ? false : true;
    }

    static Class<?> zzbv() {
        return zzme;
    }
}
