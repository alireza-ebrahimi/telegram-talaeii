package com.google.android.gms.internal.firebase_auth;

final class zzfr extends zzfp<zzfq, zzfq> {
    zzfr() {
    }

    private static void zza(Object obj, zzfq zzfq) {
        ((zzdb) obj).zzqx = zzfq;
    }

    final /* synthetic */ void zza(Object obj, int i, long j) {
        ((zzfq) obj).zzb(i << 3, Long.valueOf(j));
    }

    final /* synthetic */ void zza(Object obj, int i, zzbu zzbu) {
        ((zzfq) obj).zzb((i << 3) | 2, zzbu);
    }

    final /* synthetic */ void zza(Object obj, int i, Object obj2) {
        ((zzfq) obj).zzb((i << 3) | 3, (zzfq) obj2);
    }

    final /* synthetic */ void zza(Object obj, zzgj zzgj) {
        ((zzfq) obj).zzb(zzgj);
    }

    final boolean zza(zzeu zzeu) {
        return false;
    }

    final /* synthetic */ void zzb(Object obj, int i, long j) {
        ((zzfq) obj).zzb((i << 3) | 1, Long.valueOf(j));
    }

    final /* synthetic */ void zzc(Object obj, int i, int i2) {
        ((zzfq) obj).zzb((i << 3) | 5, Integer.valueOf(i2));
    }

    final /* synthetic */ void zzc(Object obj, zzgj zzgj) {
        ((zzfq) obj).zza(zzgj);
    }

    final void zze(Object obj) {
        ((zzdb) obj).zzqx.zzbs();
    }

    final /* synthetic */ void zze(Object obj, Object obj2) {
        zza(obj, (zzfq) obj2);
    }

    final /* synthetic */ void zzf(Object obj, Object obj2) {
        zza(obj, (zzfq) obj2);
    }

    final /* synthetic */ Object zzfy() {
        return zzfq.zzga();
    }

    final /* synthetic */ Object zzg(Object obj, Object obj2) {
        zzfq zzfq = (zzfq) obj;
        zzfq zzfq2 = (zzfq) obj2;
        return zzfq2.equals(zzfq.zzfz()) ? zzfq : zzfq.zza(zzfq, zzfq2);
    }

    final /* synthetic */ Object zzl(Object obj) {
        zzfq zzfq = (zzfq) obj;
        zzfq.zzbs();
        return zzfq;
    }

    final /* synthetic */ int zzo(Object obj) {
        return ((zzfq) obj).zzdq();
    }

    final /* synthetic */ Object zzr(Object obj) {
        return ((zzdb) obj).zzqx;
    }

    final /* synthetic */ Object zzs(Object obj) {
        zzfq zzfq = ((zzdb) obj).zzqx;
        if (zzfq != zzfq.zzfz()) {
            return zzfq;
        }
        zzfq = zzfq.zzga();
        zza(obj, zzfq);
        return zzfq;
    }

    final /* synthetic */ int zzt(Object obj) {
        return ((zzfq) obj).zzgb();
    }
}
