package com.google.android.gms.internal.firebase_auth;

final class zzeq {
    private static final zzeo zzub = zzff();
    private static final zzeo zzuc = new zzep();

    static zzeo zzfd() {
        return zzub;
    }

    static zzeo zzfe() {
        return zzuc;
    }

    private static zzeo zzff() {
        try {
            return (zzeo) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
