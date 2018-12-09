package com.google.android.gms.internal.firebase_auth;

abstract class zzfp<T, B> {
    zzfp() {
    }

    abstract void zza(B b, int i, long j);

    abstract void zza(B b, int i, zzbu zzbu);

    abstract void zza(B b, int i, T t);

    abstract void zza(T t, zzgj zzgj);

    abstract boolean zza(zzeu zzeu);

    final boolean zza(B b, zzeu zzeu) {
        int tag = zzeu.getTag();
        int i = tag >>> 3;
        switch (tag & 7) {
            case 0:
                zza((Object) b, i, zzeu.zzce());
                return true;
            case 1:
                zzb(b, i, zzeu.zzcg());
                return true;
            case 2:
                zza((Object) b, i, zzeu.zzck());
                return true;
            case 3:
                Object zzfy = zzfy();
                int i2 = (i << 3) | 4;
                while (zzeu.zzda() != Integer.MAX_VALUE) {
                    if (!zza(zzfy, zzeu)) {
                        if (i2 == zzeu.getTag()) {
                            throw zzdh.zzeh();
                        }
                        zza((Object) b, i, zzl(zzfy));
                        return true;
                    }
                }
                if (i2 == zzeu.getTag()) {
                    zza((Object) b, i, zzl(zzfy));
                    return true;
                }
                throw zzdh.zzeh();
            case 4:
                return false;
            case 5:
                zzc(b, i, zzeu.zzch());
                return true;
            default:
                throw zzdh.zzei();
        }
    }

    abstract void zzb(B b, int i, long j);

    abstract void zzc(B b, int i, int i2);

    abstract void zzc(T t, zzgj zzgj);

    abstract void zze(Object obj);

    abstract void zze(Object obj, T t);

    abstract void zzf(Object obj, B b);

    abstract B zzfy();

    abstract T zzg(T t, T t2);

    abstract T zzl(B b);

    abstract int zzo(T t);

    abstract T zzr(Object obj);

    abstract B zzs(Object obj);

    abstract int zzt(T t);
}
