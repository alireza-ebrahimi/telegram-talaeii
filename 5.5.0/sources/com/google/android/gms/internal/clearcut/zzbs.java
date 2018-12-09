package com.google.android.gms.internal.clearcut;

final class zzbs {
    private static final Class<?> zzgl = zzak();

    private static Class<?> zzak() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static zzbt zzal() {
        if (zzgl != null) {
            try {
                return (zzbt) zzgl.getDeclaredMethod("getEmptyRegistry", new Class[0]).invoke(null, new Object[0]);
            } catch (Exception e) {
            }
        }
        return zzbt.zzgo;
    }
}
