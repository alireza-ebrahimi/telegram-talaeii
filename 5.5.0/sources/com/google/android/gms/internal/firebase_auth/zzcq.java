package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzdb.zzc;
import java.util.Map.Entry;

final class zzcq extends zzcp<Object> {
    zzcq() {
    }

    final int zza(Entry<?, ?> entry) {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    final Object zza(zzco zzco, zzeh zzeh, int i) {
        return zzco.zza(zzeh, i);
    }

    final <UT, UB> UB zza(zzeu zzeu, Object obj, zzco zzco, zzcs<Object> zzcs, UB ub, zzfp<UT, UB> zzfp) {
        throw new NoSuchMethodError();
    }

    final void zza(zzbu zzbu, Object obj, zzco zzco, zzcs<Object> zzcs) {
        throw new NoSuchMethodError();
    }

    final void zza(zzeu zzeu, Object obj, zzco zzco, zzcs<Object> zzcs) {
        throw new NoSuchMethodError();
    }

    final void zza(zzgj zzgj, Entry<?, ?> entry) {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    final void zza(Object obj, zzcs<Object> zzcs) {
        ((zzc) obj).zzrd = zzcs;
    }

    final zzcs<Object> zzc(Object obj) {
        return ((zzc) obj).zzrd;
    }

    final zzcs<Object> zzd(Object obj) {
        zzcs<Object> zzc = zzc(obj);
        if (!zzc.isImmutable()) {
            return zzc;
        }
        zzcs zzcs = (zzcs) zzc.clone();
        zza(obj, zzcs);
        return zzcs;
    }

    final void zze(Object obj) {
        zzc(obj).zzbs();
    }

    final boolean zze(zzeh zzeh) {
        return zzeh instanceof zzc;
    }
}
