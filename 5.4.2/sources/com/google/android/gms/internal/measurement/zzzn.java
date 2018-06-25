package com.google.android.gms.internal.measurement;

final class zzzn {
    private static final zzzl<?> zzbsa = new zzzm();
    private static final zzzl<?> zzbsb = zzto();

    private static zzzl<?> zzto() {
        try {
            return (zzzl) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static zzzl<?> zztp() {
        return zzbsa;
    }

    static zzzl<?> zztq() {
        if (zzbsb != null) {
            return zzbsb;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
