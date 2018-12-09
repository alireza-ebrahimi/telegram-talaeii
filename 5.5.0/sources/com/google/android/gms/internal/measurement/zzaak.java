package com.google.android.gms.internal.measurement;

final class zzaak {
    private static final zzaai zzbtq = zzue();
    private static final zzaai zzbtr = new zzaaj();

    static zzaai zzuc() {
        return zzbtq;
    }

    static zzaai zzud() {
        return zzbtr;
    }

    private static zzaai zzue() {
        try {
            return (zzaai) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
