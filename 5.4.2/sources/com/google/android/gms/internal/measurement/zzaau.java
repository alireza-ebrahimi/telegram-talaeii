package com.google.android.gms.internal.measurement;

final class zzaau {
    private static final zzaas zzbty = zzul();
    private static final zzaas zzbtz = new zzaat();

    static zzaas zzuj() {
        return zzbty;
    }

    static zzaas zzuk() {
        return zzbtz;
    }

    private static zzaas zzul() {
        try {
            return (zzaas) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
