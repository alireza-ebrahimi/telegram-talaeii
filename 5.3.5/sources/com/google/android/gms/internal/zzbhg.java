package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzbhg {
    private static zzbhi zzgih;

    @Hide
    public static synchronized zzbhi zzanc() {
        zzbhi zzbhi;
        synchronized (zzbhg.class) {
            if (zzgih == null) {
                zzgih = new zzbhh();
            }
            zzbhi = zzgih;
        }
        return zzbhi;
    }
}
