package com.google.android.gms.internal.firebase_auth;

import java.util.Collection;
import java.util.List;

final class zzdu extends zzdr {
    private zzdu() {
        super();
    }

    private static <E> zzdg<E> zzd(Object obj, long j) {
        return (zzdg) zzfv.zzp(obj, j);
    }

    final <L> List<L> zza(Object obj, long j) {
        zzdg zzd = zzd(obj, j);
        if (zzd.zzbr()) {
            return zzd;
        }
        int size = zzd.size();
        Object zzj = zzd.zzj(size == 0 ? 10 : size << 1);
        zzfv.zza(obj, j, zzj);
        return zzj;
    }

    final <E> void zza(Object obj, Object obj2, long j) {
        Object zzd = zzd(obj, j);
        Collection zzd2 = zzd(obj2, j);
        int size = zzd.size();
        int size2 = zzd2.size();
        if (size > 0 && size2 > 0) {
            if (!zzd.zzbr()) {
                zzd = zzd.zzj(size2 + size);
            }
            zzd.addAll(zzd2);
        }
        if (size <= 0) {
            Collection collection = zzd2;
        }
        zzfv.zza(obj, j, zzd);
    }

    final void zzb(Object obj, long j) {
        zzd(obj, j).zzbs();
    }
}
