package com.google.android.gms.internal.clearcut;

import java.util.Collection;

final class zzdb extends zzcy {
    private zzdb() {
        super();
    }

    private static <E> zzcn<E> zzc(Object obj, long j) {
        return (zzcn) zzfd.zzo(obj, j);
    }

    final void zza(Object obj, long j) {
        zzc(obj, j).zzv();
    }

    final <E> void zza(Object obj, Object obj2, long j) {
        Object zzc = zzc(obj, j);
        Collection zzc2 = zzc(obj2, j);
        int size = zzc.size();
        int size2 = zzc2.size();
        if (size > 0 && size2 > 0) {
            if (!zzc.zzu()) {
                zzc = zzc.zzi(size2 + size);
            }
            zzc.addAll(zzc2);
        }
        if (size <= 0) {
            Collection collection = zzc2;
        }
        zzfd.zza(obj, j, zzc);
    }
}
