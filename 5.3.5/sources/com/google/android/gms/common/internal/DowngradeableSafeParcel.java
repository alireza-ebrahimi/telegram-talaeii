package com.google.android.gms.common.internal;

import com.google.android.gms.internal.zzbgl;

public abstract class DowngradeableSafeParcel extends zzbgl implements ReflectedParcelable {
    private static final Object zzgfx = new Object();
    private static ClassLoader zzgfy = null;
    private static Integer zzgfz = null;
    private boolean zzgga = false;

    @Hide
    private static ClassLoader zzamp() {
        synchronized (zzgfx) {
        }
        return null;
    }

    @Hide
    protected static Integer zzamq() {
        synchronized (zzgfx) {
        }
        return null;
    }

    @Hide
    protected static boolean zzgq(String str) {
        zzamp();
        return true;
    }
}
