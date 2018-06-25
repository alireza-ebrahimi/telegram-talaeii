package com.google.android.gms.maps.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
public final class zza {
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

    public static byte zzc(Boolean bool) {
        return bool != null ? bool.booleanValue() ? (byte) 1 : (byte) 0 : (byte) -1;
    }
}
