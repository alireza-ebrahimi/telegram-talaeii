package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzccp {
    private static zzccp zzhqf;
    private final zzcck zzhqg = new zzcck();
    private final zzccl zzhqh = new zzccl();

    static {
        zzccp zzccp = new zzccp();
        synchronized (zzccp.class) {
            zzhqf = zzccp;
        }
    }

    private zzccp() {
    }

    private static zzccp zzasm() {
        zzccp zzccp;
        synchronized (zzccp.class) {
            zzccp = zzhqf;
        }
        return zzccp;
    }

    public static zzcck zzasn() {
        return zzasm().zzhqg;
    }

    public static zzccl zzaso() {
        return zzasm().zzhqh;
    }
}
