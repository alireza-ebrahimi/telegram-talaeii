package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

final class zzed implements zzec {
    zzed() {
    }

    public final int zzb(int i, Object obj, Object obj2) {
        zzeb zzeb = (zzeb) obj;
        if (!zzeb.isEmpty()) {
            Iterator it = zzeb.entrySet().iterator();
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
        obj = (zzeb) obj;
        zzeb zzeb = (zzeb) obj2;
        if (!zzeb.isEmpty()) {
            if (!obj.isMutable()) {
                obj = obj.zzeu();
            }
            obj.zza(zzeb);
        }
        return obj;
    }

    public final Map<?, ?> zzi(Object obj) {
        return (zzeb) obj;
    }

    public final Map<?, ?> zzj(Object obj) {
        return (zzeb) obj;
    }

    public final boolean zzk(Object obj) {
        return !((zzeb) obj).isMutable();
    }

    public final Object zzl(Object obj) {
        ((zzeb) obj).zzbs();
        return obj;
    }

    public final Object zzm(Object obj) {
        return zzeb.zzet().zzeu();
    }

    public final zzea<?, ?> zzn(Object obj) {
        throw new NoSuchMethodError();
    }
}
