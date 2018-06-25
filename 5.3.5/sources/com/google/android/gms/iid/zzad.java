package com.google.android.gms.iid;

import com.google.android.gms.common.internal.Hide;

@Hide
abstract class zzad {
    private static zzad zziob;

    zzad() {
    }

    static synchronized zzad zzawy() {
        zzad zzad;
        synchronized (zzad.class) {
            if (zziob == null) {
                zziob = new zzx();
            }
            zzad = zziob;
        }
        return zzad;
    }

    abstract zzae<Boolean> zzf(String str, boolean z);
}
