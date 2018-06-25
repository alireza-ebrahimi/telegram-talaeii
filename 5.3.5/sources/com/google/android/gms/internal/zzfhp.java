package com.google.android.gms.internal;

final class zzfhp {
    private static final zzfhn<?> zzpoy = new zzfho();
    private static final zzfhn<?> zzpoz = zzczg();

    private static zzfhn<?> zzczg() {
        try {
            return (zzfhn) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static zzfhn<?> zzczh() {
        return zzpoy;
    }

    static zzfhn<?> zzczi() {
        if (zzpoz != null) {
            return zzpoz;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
