package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzzk {
    private static volatile boolean zzbrw = false;
    private static final Class<?> zzbrx = zztm();
    static final zzzk zzbry = new zzzk(true);
    private final Map<Object, Object> zzbrz;

    zzzk() {
        this.zzbrz = new HashMap();
    }

    private zzzk(boolean z) {
        this.zzbrz = Collections.emptyMap();
    }

    private static Class<?> zztm() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static zzzk zztn() {
        return zzzj.zztl();
    }
}
