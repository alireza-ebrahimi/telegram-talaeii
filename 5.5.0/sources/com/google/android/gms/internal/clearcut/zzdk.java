package com.google.android.gms.internal.clearcut;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

final class zzdk implements zzdj {
    zzdk() {
    }

    public final int zzb(int i, Object obj, Object obj2) {
        zzdi zzdi = (zzdi) obj;
        if (!zzdi.isEmpty()) {
            Iterator it = zzdi.entrySet().iterator();
            if (it.hasNext()) {
                Entry entry = (Entry) it.next();
                entry.getKey();
                entry.getValue();
                throw new NoSuchMethodError();
            }
        }
        return 0;
    }

    public final Object zzb(Object obj, Object obj2) {
        obj = (zzdi) obj;
        zzdi zzdi = (zzdi) obj2;
        if (!zzdi.isEmpty()) {
            if (!obj.isMutable()) {
                obj = obj.zzca();
            }
            obj.zza(zzdi);
        }
        return obj;
    }

    public final Map<?, ?> zzg(Object obj) {
        return (zzdi) obj;
    }

    public final Map<?, ?> zzh(Object obj) {
        return (zzdi) obj;
    }

    public final boolean zzi(Object obj) {
        return !((zzdi) obj).isMutable();
    }

    public final Object zzj(Object obj) {
        ((zzdi) obj).zzv();
        return obj;
    }

    public final Object zzk(Object obj) {
        return zzdi.zzbz().zzca();
    }

    public final zzdh<?, ?> zzl(Object obj) {
        throw new NoSuchMethodError();
    }
}
