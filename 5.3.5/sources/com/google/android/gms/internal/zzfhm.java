package com.google.android.gms.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzfhm {
    private static volatile boolean zzpou = false;
    private static final Class<?> zzpov = zzcze();
    static final zzfhm zzpow = new zzfhm(true);
    private final Map<Object, Object> zzpox;

    zzfhm() {
        this.zzpox = new HashMap();
    }

    private zzfhm(boolean z) {
        this.zzpox = Collections.emptyMap();
    }

    private static Class<?> zzcze() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static zzfhm zzczf() {
        return zzfhl.zzczd();
    }
}
