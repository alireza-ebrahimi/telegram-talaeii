package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzdb.zze;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class zzck implements zzgj {
    private final zzci zzmo;

    private zzck(zzci zzci) {
        this.zzmo = (zzci) zzdd.zza((Object) zzci, "output");
    }

    public static zzck zza(zzci zzci) {
        return zzci.zzng != null ? zzci.zzng : new zzck(zzci);
    }

    public final void zza(int i, double d) {
        this.zzmo.zza(i, d);
    }

    public final void zza(int i, float f) {
        this.zzmo.zza(i, f);
    }

    public final void zza(int i, long j) {
        this.zzmo.zza(i, j);
    }

    public final void zza(int i, zzbu zzbu) {
        this.zzmo.zza(i, zzbu);
    }

    public final <K, V> void zza(int i, zzea<K, V> zzea, Map<K, V> map) {
        for (Entry entry : map.entrySet()) {
            this.zzmo.zzb(i, 2);
            this.zzmo.zzx(zzdz.zza(zzea, entry.getKey(), entry.getValue()));
            zzdz.zza(this.zzmo, zzea, entry.getKey(), entry.getValue());
        }
    }

    public final void zza(int i, Object obj) {
        if (obj instanceof zzbu) {
            this.zzmo.zzb(i, (zzbu) obj);
        } else {
            this.zzmo.zzb(i, (zzeh) obj);
        }
    }

    public final void zza(int i, Object obj, zzev zzev) {
        this.zzmo.zza(i, (zzeh) obj, zzev);
    }

    public final void zza(int i, String str) {
        this.zzmo.zza(i, str);
    }

    public final void zza(int i, List<String> list) {
        int i2 = 0;
        if (list instanceof zzdq) {
            zzdq zzdq = (zzdq) list;
            for (int i3 = 0; i3 < list.size(); i3++) {
                Object raw = zzdq.getRaw(i3);
                if (raw instanceof String) {
                    this.zzmo.zza(i, (String) raw);
                } else {
                    this.zzmo.zza(i, (zzbu) raw);
                }
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zza(i, (String) list.get(i2));
            i2++;
        }
    }

    public final void zza(int i, List<?> list, zzev zzev) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), zzev);
        }
    }

    public final void zza(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzab(((Integer) list.get(i4)).intValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zzw(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zzc(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzaj(int i) {
        this.zzmo.zzb(i, 3);
    }

    public final void zzak(int i) {
        this.zzmo.zzb(i, 4);
    }

    public final void zzb(int i, long j) {
        this.zzmo.zzb(i, j);
    }

    public final void zzb(int i, Object obj, zzev zzev) {
        zzci zzci = this.zzmo;
        zzeh zzeh = (zzeh) obj;
        zzci.zzb(i, 3);
        zzev.zza(zzeh, zzci.zzng);
        zzci.zzb(i, 4);
    }

    public final void zzb(int i, List<zzbu> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zzmo.zza(i, (zzbu) list.get(i2));
        }
    }

    public final void zzb(int i, List<?> list, zzev zzev) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), zzev);
        }
    }

    public final void zzb(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzae(((Integer) list.get(i4)).intValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zzz(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zzf(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzb(int i, boolean z) {
        this.zzmo.zzb(i, z);
    }

    public final void zzc(int i, int i2) {
        this.zzmo.zzc(i, i2);
    }

    public final void zzc(int i, long j) {
        this.zzmo.zzc(i, j);
    }

    public final void zzc(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzd(((Long) list.get(i4)).longValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zza(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zza(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzd(int i, int i2) {
        this.zzmo.zzd(i, i2);
    }

    public final void zzd(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zze(((Long) list.get(i4)).longValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zza(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zza(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final int zzdf() {
        return zze.zzrp;
    }

    public final void zze(int i, int i2) {
        this.zzmo.zze(i, i2);
    }

    public final void zze(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzg(((Long) list.get(i4)).longValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zzc(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zzc(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzf(int i, int i2) {
        this.zzmo.zzf(i, i2);
    }

    public final void zzf(int i, List<Float> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzb(((Float) list.get(i4)).floatValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zza(((Float) list.get(i2)).floatValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zza(i, ((Float) list.get(i2)).floatValue());
            i2++;
        }
    }

    public final void zzg(int i, List<Double> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzb(((Double) list.get(i4)).doubleValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zza(((Double) list.get(i2)).doubleValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zza(i, ((Double) list.get(i2)).doubleValue());
            i2++;
        }
    }

    public final void zzh(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzag(((Integer) list.get(i4)).intValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zzw(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zzc(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzi(int i, long j) {
        this.zzmo.zza(i, j);
    }

    public final void zzi(int i, List<Boolean> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzg(((Boolean) list.get(i4)).booleanValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zzf(((Boolean) list.get(i2)).booleanValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zzb(i, ((Boolean) list.get(i2)).booleanValue());
            i2++;
        }
    }

    public final void zzj(int i, long j) {
        this.zzmo.zzc(i, j);
    }

    public final void zzj(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzac(((Integer) list.get(i4)).intValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zzx(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zzd(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzk(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzaf(((Integer) list.get(i4)).intValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zzz(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zzf(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzl(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzh(((Long) list.get(i4)).longValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zzc(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zzc(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzm(int i, int i2) {
        this.zzmo.zzf(i, i2);
    }

    public final void zzm(int i, List<Integer> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzad(((Integer) list.get(i4)).intValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zzy(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zze(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzn(int i, int i2) {
        this.zzmo.zzc(i, i2);
    }

    public final void zzn(int i, List<Long> list, boolean z) {
        int i2 = 0;
        if (z) {
            this.zzmo.zzb(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzci.zzf(((Long) list.get(i4)).longValue());
            }
            this.zzmo.zzx(i3);
            while (i2 < list.size()) {
                this.zzmo.zzb(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzmo.zzb(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }
}
