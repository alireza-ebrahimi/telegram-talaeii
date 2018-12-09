package com.google.android.gms.maps.internal;

public final class zza {
    public static byte zza(Boolean bool) {
        return bool != null ? bool.booleanValue() ? (byte) 1 : (byte) 0 : (byte) -1;
    }

    public static Boolean zza(byte b) {
        switch (b) {
            case (byte) 0:
                return Boolean.FALSE;
            case (byte) 1:
                return Boolean.TRUE;
            default:
                return null;
        }
    }
}
