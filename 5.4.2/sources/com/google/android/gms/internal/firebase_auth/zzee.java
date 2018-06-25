package com.google.android.gms.internal.firebase_auth;

final class zzee {
    private static final zzec zztg = zzey();
    private static final zzec zzth = new zzed();

    static zzec zzew() {
        return zztg;
    }

    static zzec zzex() {
        return zzth;
    }

    private static zzec zzey() {
        try {
            return (zzec) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
