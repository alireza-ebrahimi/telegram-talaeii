package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzdb.zzb;
import com.google.android.gms.internal.firebase_auth.zzdb.zze;

public final class zzfm extends zzdb<zzfm, zza> implements zzej {
    private static final zzfm zzuz = new zzfm();
    private static volatile zzer<zzfm> zzva;
    private long zzux;
    private int zzuy;

    public static final class zza extends com.google.android.gms.internal.firebase_auth.zzdb.zza<zzfm, zza> implements zzej {
        private zza() {
            super(zzfm.zzuz);
        }
    }

    static {
        zzdb.zza(zzfm.class, zzuz);
    }

    private zzfm() {
    }

    public static zzer<zzfm> zzfw() {
        return (zzer) zzuz.zza(zze.zzrk, null, null);
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        switch (zzfn.zzvb[i - 1]) {
            case 1:
                return new zzfm();
            case 2:
                return new zza();
            case 3:
                Object[] objArr = new Object[]{"zzux", "zzuy"};
                return new zzet(zzuz, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0002\u0004", objArr);
            case 4:
                return zzuz;
            case 5:
                zzer zzer = zzva;
                if (zzer != null) {
                    return zzer;
                }
                Object obj3;
                synchronized (zzfm.class) {
                    obj3 = zzva;
                    if (obj3 == null) {
                        obj3 = new zzb(zzuz);
                        zzva = obj3;
                    }
                }
                return obj3;
            case 6:
                return Byte.valueOf((byte) 1);
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
