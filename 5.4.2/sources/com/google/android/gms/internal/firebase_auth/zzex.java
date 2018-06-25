package com.google.android.gms.internal.firebase_auth;

import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

final class zzex {
    private static final Class<?> zzug = zzfm();
    private static final zzfp<?, ?> zzuh = zzi(false);
    private static final zzfp<?, ?> zzui = zzi(true);
    private static final zzfp<?, ?> zzuj = new zzfr();

    static <UT, UB> UB zza(int i, int i2, UB ub, zzfp<UT, UB> zzfp) {
        Object zzfy;
        if (ub == null) {
            zzfy = zzfp.zzfy();
        }
        zzfp.zza(zzfy, i, (long) i2);
        return zzfy;
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzdf<?> zzdf, UB ub, zzfp<UT, UB> zzfp) {
        if (zzdf == null) {
            return ub;
        }
        UB ub2;
        int intValue;
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            int i3 = 0;
            ub2 = ub;
            while (i2 < size) {
                UB ub3;
                intValue = ((Integer) list.get(i2)).intValue();
                if (zzdf.zzam(intValue) != null) {
                    if (i2 != i3) {
                        list.set(i3, Integer.valueOf(intValue));
                    }
                    intValue = i3 + 1;
                    ub3 = ub2;
                } else {
                    int i4 = i3;
                    ub3 = zza(i, intValue, (Object) ub2, (zzfp) zzfp);
                    intValue = i4;
                }
                i2++;
                ub2 = ub3;
                i3 = intValue;
            }
            if (i3 != size) {
                list.subList(i3, size).clear();
            }
        } else {
            Object zza;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                intValue = ((Integer) it.next()).intValue();
                if (zzdf.zzam(intValue) == null) {
                    zza = zza(i, intValue, zza, (zzfp) zzfp);
                    it.remove();
                }
            }
            ub2 = zza;
        }
        return ub2;
    }

    public static void zza(int i, List<String> list, zzgj zzgj) {
        if (list != null && !list.isEmpty()) {
            zzgj.zza(i, (List) list);
        }
    }

    public static void zza(int i, List<?> list, zzgj zzgj, zzev zzev) {
        if (list != null && !list.isEmpty()) {
            zzgj.zza(i, (List) list, zzev);
        }
    }

    public static void zza(int i, List<Double> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzg(i, list, z);
        }
    }

    static <T, FT extends zzcu<FT>> void zza(zzcp<FT> zzcp, T t, T t2) {
        zzcs zzc = zzcp.zzc(t2);
        if (!zzc.isEmpty()) {
            zzcp.zzd(t).zza(zzc);
        }
    }

    static <T> void zza(zzec zzec, T t, T t2, long j) {
        zzfv.zza((Object) t, j, zzec.zzb(zzfv.zzp(t, j), zzfv.zzp(t2, j)));
    }

    static <T, UT, UB> void zza(zzfp<UT, UB> zzfp, T t, T t2) {
        zzfp.zze(t, zzfp.zzg(zzfp.zzr(t), zzfp.zzr(t2)));
    }

    static int zzaa(List<?> list) {
        return list.size() << 3;
    }

    static int zzab(List<?> list) {
        return list.size();
    }

    public static void zzb(int i, List<zzbu> list, zzgj zzgj) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzb(i, (List) list);
        }
    }

    public static void zzb(int i, List<?> list, zzgj zzgj, zzev zzev) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzb(i, (List) list, zzev);
        }
    }

    public static void zzb(int i, List<Float> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzf(i, list, z);
        }
    }

    static int zzc(int i, Object obj, zzev zzev) {
        return obj instanceof zzdo ? zzci.zza(i, (zzdo) obj) : zzci.zzb(i, (zzeh) obj, zzev);
    }

    static int zzc(int i, List<?> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzaa = zzci.zzaa(i) * size;
        int i2;
        if (list instanceof zzdq) {
            zzdq zzdq = (zzdq) list;
            i2 = 0;
            while (i2 < size) {
                Object raw = zzdq.getRaw(i2);
                i2++;
                zzaa = raw instanceof zzbu ? zzci.zzb((zzbu) raw) + zzaa : zzci.zzam((String) raw) + zzaa;
            }
            return zzaa;
        }
        i2 = 0;
        while (i2 < size) {
            raw = list.get(i2);
            i2++;
            zzaa = raw instanceof zzbu ? zzci.zzb((zzbu) raw) + zzaa : zzci.zzam((String) raw) + zzaa;
        }
        return zzaa;
    }

    static int zzc(int i, List<?> list, zzev zzev) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzaa = zzci.zzaa(i) * size;
        int i2 = 0;
        while (i2 < size) {
            Object obj = list.get(i2);
            i2++;
            zzaa = obj instanceof zzdo ? zzci.zza((zzdo) obj) + zzaa : zzci.zzb((zzeh) obj, zzev) + zzaa;
        }
        return zzaa;
    }

    public static void zzc(int i, List<Long> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzc(i, list, z);
        }
    }

    static int zzd(int i, List<zzbu> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzaa = size * zzci.zzaa(i);
        for (size = 0; size < list.size(); size++) {
            zzaa += zzci.zzb((zzbu) list.get(size));
        }
        return zzaa;
    }

    static int zzd(int i, List<zzeh> list, zzev zzev) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += zzci.zzc(i, (zzeh) list.get(i3), zzev);
        }
        return i2;
    }

    public static void zzd(int i, List<Long> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzd(i, list, z);
        }
    }

    static boolean zzd(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static void zze(int i, List<Long> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzn(i, list, z);
        }
    }

    public static void zzf(int i, List<Long> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zze(i, list, z);
        }
    }

    public static zzfp<?, ?> zzfj() {
        return zzuh;
    }

    public static zzfp<?, ?> zzfk() {
        return zzui;
    }

    public static zzfp<?, ?> zzfl() {
        return zzuj;
    }

    private static Class<?> zzfm() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class<?> zzfn() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable th) {
            return null;
        }
    }

    public static void zzg(int i, List<Long> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzl(i, list, z);
        }
    }

    public static void zzg(Class<?> cls) {
        if (!zzdb.class.isAssignableFrom(cls) && zzug != null && !zzug.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzh(int i, List<Integer> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zza(i, (List) list, z);
        }
    }

    private static zzfp<?, ?> zzi(boolean z) {
        try {
            Class zzfn = zzfn();
            if (zzfn == null) {
                return null;
            }
            return (zzfp) zzfn.getConstructor(new Class[]{Boolean.TYPE}).newInstance(new Object[]{Boolean.valueOf(z)});
        } catch (Throwable th) {
            return null;
        }
    }

    public static void zzi(int i, List<Integer> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzj(i, list, z);
        }
    }

    public static void zzj(int i, List<Integer> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzm(i, list, z);
        }
    }

    public static void zzk(int i, List<Integer> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzb(i, (List) list, z);
        }
    }

    public static void zzl(int i, List<Integer> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzk(i, list, z);
        }
    }

    public static void zzm(int i, List<Integer> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzh(i, list, z);
        }
    }

    public static void zzn(int i, List<Boolean> list, zzgj zzgj, boolean z) {
        if (list != null && !list.isEmpty()) {
            zzgj.zzi(i, list, z);
        }
    }

    static int zzo(int i, List<Long> list, boolean z) {
        return list.size() == 0 ? 0 : zzs(list) + (list.size() * zzci.zzaa(i));
    }

    static int zzp(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (size * zzci.zzaa(i)) + zzt(list);
    }

    static int zzq(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (size * zzci.zzaa(i)) + zzu(list);
    }

    static int zzr(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (size * zzci.zzaa(i)) + zzv(list);
    }

    static int zzs(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (size * zzci.zzaa(i)) + zzw(list);
    }

    static int zzs(List<Long> list) {
        int i = 0;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzdv) {
            zzdv zzdv = (zzdv) list;
            i2 = 0;
            while (i2 < size) {
                int zzd = zzci.zzd(zzdv.getLong(i2)) + i;
                i2++;
                i = zzd;
            }
            return i;
        }
        i2 = 0;
        for (zzd = 0; zzd < size; zzd++) {
            i2 += zzci.zzd(((Long) list.get(zzd)).longValue());
        }
        return i2;
    }

    static int zzt(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (size * zzci.zzaa(i)) + zzx(list);
    }

    static int zzt(List<Long> list) {
        int i = 0;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzdv) {
            zzdv zzdv = (zzdv) list;
            i2 = 0;
            while (i2 < size) {
                int zze = zzci.zze(zzdv.getLong(i2)) + i;
                i2++;
                i = zze;
            }
            return i;
        }
        i2 = 0;
        for (zze = 0; zze < size; zze++) {
            i2 += zzci.zze(((Long) list.get(zze)).longValue());
        }
        return i2;
    }

    static int zzu(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (size * zzci.zzaa(i)) + zzy(list);
    }

    static int zzu(List<Long> list) {
        int i = 0;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzdv) {
            zzdv zzdv = (zzdv) list;
            i2 = 0;
            while (i2 < size) {
                int zzf = zzci.zzf(zzdv.getLong(i2)) + i;
                i2++;
                i = zzf;
            }
            return i;
        }
        i2 = 0;
        for (zzf = 0; zzf < size; zzf++) {
            i2 += zzci.zzf(((Long) list.get(zzf)).longValue());
        }
        return i2;
    }

    static int zzv(int i, List<?> list, boolean z) {
        int size = list.size();
        return size == 0 ? 0 : zzci.zzj(i, 0) * size;
    }

    static int zzv(List<Integer> list) {
        int i = 0;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzdc) {
            zzdc zzdc = (zzdc) list;
            i2 = 0;
            while (i2 < size) {
                int zzag = zzci.zzag(zzdc.getInt(i2)) + i;
                i2++;
                i = zzag;
            }
            return i;
        }
        i2 = 0;
        for (zzag = 0; zzag < size; zzag++) {
            i2 += zzci.zzag(((Integer) list.get(zzag)).intValue());
        }
        return i2;
    }

    static int zzw(int i, List<?> list, boolean z) {
        int size = list.size();
        return size == 0 ? 0 : size * zzci.zzg(i, 0);
    }

    static int zzw(List<Integer> list) {
        int i = 0;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzdc) {
            zzdc zzdc = (zzdc) list;
            i2 = 0;
            while (i2 < size) {
                int zzab = zzci.zzab(zzdc.getInt(i2)) + i;
                i2++;
                i = zzab;
            }
            return i;
        }
        i2 = 0;
        for (zzab = 0; zzab < size; zzab++) {
            i2 += zzci.zzab(((Integer) list.get(zzab)).intValue());
        }
        return i2;
    }

    static int zzx(int i, List<?> list, boolean z) {
        int size = list.size();
        return size == 0 ? 0 : size * zzci.zzc(i, true);
    }

    static int zzx(List<Integer> list) {
        int i = 0;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzdc) {
            zzdc zzdc = (zzdc) list;
            i2 = 0;
            while (i2 < size) {
                int zzac = zzci.zzac(zzdc.getInt(i2)) + i;
                i2++;
                i = zzac;
            }
            return i;
        }
        i2 = 0;
        for (zzac = 0; zzac < size; zzac++) {
            i2 += zzci.zzac(((Integer) list.get(zzac)).intValue());
        }
        return i2;
    }

    static int zzy(List<Integer> list) {
        int i = 0;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzdc) {
            zzdc zzdc = (zzdc) list;
            i2 = 0;
            while (i2 < size) {
                int zzad = zzci.zzad(zzdc.getInt(i2)) + i;
                i2++;
                i = zzad;
            }
            return i;
        }
        i2 = 0;
        for (zzad = 0; zzad < size; zzad++) {
            i2 += zzci.zzad(((Integer) list.get(zzad)).intValue());
        }
        return i2;
    }

    static int zzz(List<?> list) {
        return list.size() << 2;
    }
}
