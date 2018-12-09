package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

final class zzdt extends zzdr {
    private static final Class<?> zzsv = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzdt() {
        super();
    }

    private static <L> List<L> zza(Object obj, long j, int i) {
        List<L> zzc = zzc(obj, j);
        if (zzc.isEmpty()) {
            Object zzdp = zzc instanceof zzdq ? new zzdp(i) : new ArrayList(i);
            zzfv.zza(obj, j, zzdp);
            return zzdp;
        } else if (zzsv.isAssignableFrom(zzc.getClass())) {
            r1 = new ArrayList(zzc.size() + i);
            r1.addAll(zzc);
            zzfv.zza(obj, j, (Object) r1);
            return r1;
        } else if (!(zzc instanceof zzfs)) {
            return zzc;
        } else {
            r1 = new zzdp(zzc.size() + i);
            r1.addAll((zzfs) zzc);
            zzfv.zza(obj, j, (Object) r1);
            return r1;
        }
    }

    private static <E> List<E> zzc(Object obj, long j) {
        return (List) zzfv.zzp(obj, j);
    }

    final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    final <E> void zza(Object obj, Object obj2, long j) {
        Collection zzc = zzc(obj2, j);
        Object zza = zza(obj, j, zzc.size());
        int size = zza.size();
        int size2 = zzc.size();
        if (size > 0 && size2 > 0) {
            zza.addAll(zzc);
        }
        if (size <= 0) {
            Collection collection = zzc;
        }
        zzfv.zza(obj, j, zza);
    }

    final void zzb(Object obj, long j) {
        Object zzep;
        List list = (List) zzfv.zzp(obj, j);
        if (list instanceof zzdq) {
            zzep = ((zzdq) list).zzep();
        } else if (!zzsv.isAssignableFrom(list.getClass())) {
            zzep = Collections.unmodifiableList(list);
        } else {
            return;
        }
        zzfv.zza(obj, j, zzep);
    }
}
