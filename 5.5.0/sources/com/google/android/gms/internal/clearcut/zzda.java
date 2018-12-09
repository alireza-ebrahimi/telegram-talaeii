package com.google.android.gms.internal.clearcut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

final class zzda extends zzcy {
    private static final Class<?> zzlv = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzda() {
        super();
    }

    private static <E> List<E> zzb(Object obj, long j) {
        return (List) zzfd.zzo(obj, j);
    }

    final void zza(Object obj, long j) {
        Object zzbu;
        List list = (List) zzfd.zzo(obj, j);
        if (list instanceof zzcx) {
            zzbu = ((zzcx) list).zzbu();
        } else if (!zzlv.isAssignableFrom(list.getClass())) {
            zzbu = Collections.unmodifiableList(list);
        } else {
            return;
        }
        zzfd.zza(obj, j, zzbu);
    }

    final <E> void zza(Object obj, Object obj2, long j) {
        Collection zzb = zzb(obj2, j);
        int size = zzb.size();
        Object zzb2 = zzb(obj, j);
        if (zzb2.isEmpty()) {
            zzb2 = zzb2 instanceof zzcx ? new zzcw(size) : new ArrayList(size);
            zzfd.zza(obj, j, zzb2);
        } else if (zzlv.isAssignableFrom(zzb2.getClass())) {
            r1 = new ArrayList(size + zzb2.size());
            r1.addAll(zzb2);
            zzfd.zza(obj, j, r1);
            zzb2 = r1;
        } else if (zzb2 instanceof zzfa) {
            r1 = new zzcw(size + zzb2.size());
            r1.addAll((zzfa) zzb2);
            zzfd.zza(obj, j, r1);
            zzb2 = r1;
        }
        int size2 = zzb2.size();
        size = zzb.size();
        if (size2 > 0 && size > 0) {
            zzb2.addAll(zzb);
        }
        if (size2 <= 0) {
            Collection collection = zzb;
        }
        zzfd.zza(obj, j, zzb2);
    }
}
