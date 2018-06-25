package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzcg.zzb;
import com.google.android.gms.internal.clearcut.zzcg.zzc;
import com.google.android.gms.internal.clearcut.zzcg.zzd;

public final class zzgc extends zzd<zzgc, zza> implements zzdq {
    private static volatile zzdz<zzgc> zzbg;
    private static final zzgc zzsg = new zzgc();
    private byte zzsf = (byte) 2;

    public static final class zza extends zzc<zzgc, zza> implements zzdq {
        private zza() {
            super(zzgc.zzsg);
        }
    }

    static {
        zzcg.zza(zzgc.class, zzsg);
    }

    private zzgc() {
    }

    public static zzgc zzer() {
        return zzsg;
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        switch (zzgd.zzba[i - 1]) {
            case 1:
                return new zzgc();
            case 2:
                return new zza();
            case 3:
                return zzcg.zza(zzsg, "\u0003\u0000", null);
            case 4:
                return zzsg;
            case 5:
                zzdz zzdz = zzbg;
                if (zzdz != null) {
                    return zzdz;
                }
                Object obj3;
                synchronized (zzgc.class) {
                    obj3 = zzbg;
                    if (obj3 == null) {
                        obj3 = new zzb(zzsg);
                        zzbg = obj3;
                    }
                }
                return obj3;
            case 6:
                return Byte.valueOf(this.zzsf);
            case 7:
                this.zzsf = (byte) (obj == null ? 0 : 1);
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
