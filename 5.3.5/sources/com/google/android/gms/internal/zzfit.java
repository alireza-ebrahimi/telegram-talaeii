package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfit<K, V> {
    private final V value;
    private final K zzmbd;
    private final zzfiv<K, V> zzpqz;

    private zzfit(zzfky zzfky, K k, zzfky zzfky2, V v) {
        this.zzpqz = new zzfiv(zzfky, k, zzfky2, v);
        this.zzmbd = k;
        this.value = v;
    }

    static <K, V> int zza(zzfiv<K, V> zzfiv, K k, V v) {
        return zzfhq.zza(zzfiv.zzpra, 1, (Object) k) + zzfhq.zza(zzfiv.zzprc, 2, (Object) v);
    }

    public static <K, V> zzfit<K, V> zza(zzfky zzfky, K k, zzfky zzfky2, V v) {
        return new zzfit(zzfky, k, zzfky2, v);
    }

    private static <T> T zza(zzfhb zzfhb, zzfhm zzfhm, zzfky zzfky, T t) throws IOException {
        switch (zzfiu.zzppe[zzfky.ordinal()]) {
            case 1:
                zzfjd zzczt = ((zzfjc) t).zzczt();
                zzfhb.zza(zzczt, zzfhm);
                return zzczt.zzczy();
            case 2:
                return Integer.valueOf(zzfhb.zzcyh());
            case 3:
                throw new RuntimeException("Groups are not allowed in maps.");
            default:
                return zzfhq.zza(zzfhb, zzfky, true);
        }
    }

    static <K, V> void zza(zzfhg zzfhg, zzfiv<K, V> zzfiv, K k, V v) throws IOException {
        zzfhq.zza(zzfhg, zzfiv.zzpra, 1, k);
        zzfhq.zza(zzfhg, zzfiv.zzprc, 2, v);
    }

    public final void zza(zzfhg zzfhg, int i, K k, V v) throws IOException {
        zzfhg.zzac(i, 2);
        zzfhg.zzlt(zza(this.zzpqz, (Object) k, (Object) v));
        zza(zzfhg, this.zzpqz, (Object) k, (Object) v);
    }

    public final void zza(zzfiw<K, V> zzfiw, zzfhb zzfhb, zzfhm zzfhm) throws IOException {
        int zzli = zzfhb.zzli(zzfhb.zzcym());
        Object obj = this.zzpqz.zzprb;
        Object obj2 = this.zzpqz.zzinq;
        while (true) {
            int zzcxx = zzfhb.zzcxx();
            if (zzcxx == 0) {
                break;
            } else if (zzcxx == (this.zzpqz.zzpra.zzdcj() | 8)) {
                obj = zza(zzfhb, zzfhm, this.zzpqz.zzpra, obj);
            } else if (zzcxx == (this.zzpqz.zzprc.zzdcj() | 16)) {
                obj2 = zza(zzfhb, zzfhm, this.zzpqz.zzprc, obj2);
            } else if (!zzfhb.zzlg(zzcxx)) {
                break;
            }
        }
        zzfhb.zzlf(0);
        zzfhb.zzlj(zzli);
        zzfiw.put(obj, obj2);
    }

    public final int zzb(int i, K k, V v) {
        return zzfhg.zzlw(i) + zzfhg.zzmd(zza(this.zzpqz, (Object) k, (Object) v));
    }
}
