package com.google.android.gms.internal;

final class zzfgo {
    private static final Class<?> zzpnt = zztu("libcore.io.Memory");
    private static final boolean zzpnu = (zztu("org.robolectric.Robolectric") != null);

    static boolean zzcxm() {
        return (zzpnt == null || zzpnu) ? false : true;
    }

    static Class<?> zzcxn() {
        return zzpnt;
    }

    private static <T> Class<T> zztu(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable th) {
            return null;
        }
    }
}
