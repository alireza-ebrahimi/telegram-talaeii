package com.google.android.gms.internal.measurement;

final class zzaaz {
    private static final Class<?> zzbud = zzuq();
    private static final zzabl<?, ?> zzbue = zzu(false);
    private static final zzabl<?, ?> zzbuf = zzu(true);
    private static final zzabl<?, ?> zzbug = new zzabn();

    public static void zzh(Class<?> cls) {
        if (!zzzs.class.isAssignableFrom(cls) && zzbud != null && !zzbud.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    private static zzabl<?, ?> zzu(boolean z) {
        try {
            Class zzur = zzur();
            if (zzur == null) {
                return null;
            }
            return (zzabl) zzur.getConstructor(new Class[]{Boolean.TYPE}).newInstance(new Object[]{Boolean.valueOf(z)});
        } catch (Throwable th) {
            return null;
        }
    }

    public static zzabl<?, ?> zzun() {
        return zzbue;
    }

    public static zzabl<?, ?> zzuo() {
        return zzbuf;
    }

    public static zzabl<?, ?> zzup() {
        return zzbug;
    }

    private static Class<?> zzuq() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class<?> zzur() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable th) {
            return null;
        }
    }
}
