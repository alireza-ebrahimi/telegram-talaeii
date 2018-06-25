package com.google.android.gms.internal;

final class zzfjx {
    private static final Class<?> zzpsg = zzdbn();
    private static final zzfkn<?, ?> zzpsh = zzdp(false);
    private static final zzfkn<?, ?> zzpsi = zzdp(true);
    private static final zzfkn<?, ?> zzpsj = new zzfkp();

    public static zzfkn<?, ?> zzdbk() {
        return zzpsh;
    }

    public static zzfkn<?, ?> zzdbl() {
        return zzpsi;
    }

    public static zzfkn<?, ?> zzdbm() {
        return zzpsj;
    }

    private static Class<?> zzdbn() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class<?> zzdbo() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable th) {
            return null;
        }
    }

    private static zzfkn<?, ?> zzdp(boolean z) {
        try {
            Class zzdbo = zzdbo();
            if (zzdbo == null) {
                return null;
            }
            return (zzfkn) zzdbo.getConstructor(new Class[]{Boolean.TYPE}).newInstance(new Object[]{Boolean.valueOf(z)});
        } catch (Throwable th) {
            return null;
        }
    }

    public static void zzm(Class<?> cls) {
        if (!zzfhu.class.isAssignableFrom(cls) && zzpsg != null && !zzpsg.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }
}
