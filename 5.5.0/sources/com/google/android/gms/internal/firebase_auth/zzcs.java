package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzcs<FieldDescriptorType extends zzcu<FieldDescriptorType>> {
    private static final zzcs zzod = new zzcs(true);
    private final zzey<FieldDescriptorType, Object> zzoa = zzey.zzat(16);
    private boolean zzob;
    private boolean zzoc = false;

    private zzcs() {
    }

    private zzcs(boolean z) {
        zzbs();
    }

    static int zza(zzgd zzgd, int i, Object obj) {
        int i2;
        int zzaa = zzci.zzaa(i);
        if (zzgd == zzgd.GROUP) {
            zzdd.zzf((zzeh) obj);
            i2 = zzaa << 1;
        } else {
            i2 = zzaa;
        }
        return i2 + zzb(zzgd, obj);
    }

    private final Object zza(FieldDescriptorType fieldDescriptorType) {
        Object obj = this.zzoa.get(fieldDescriptorType);
        return obj instanceof zzdk ? zzdk.zzem() : obj;
    }

    static void zza(zzci zzci, zzgd zzgd, int i, Object obj) {
        if (zzgd == zzgd.GROUP) {
            zzdd.zzf((zzeh) obj);
            zzeh zzeh = (zzeh) obj;
            zzci.zzb(i, 3);
            zzeh.zzb(zzci);
            zzci.zzb(i, 4);
            return;
        }
        zzci.zzb(i, zzgd.zzgk());
        switch (zzct.zzne[zzgd.ordinal()]) {
            case 1:
                zzci.zza(((Double) obj).doubleValue());
                return;
            case 2:
                zzci.zza(((Float) obj).floatValue());
                return;
            case 3:
                zzci.zza(((Long) obj).longValue());
                return;
            case 4:
                zzci.zza(((Long) obj).longValue());
                return;
            case 5:
                zzci.zzw(((Integer) obj).intValue());
                return;
            case 6:
                zzci.zzc(((Long) obj).longValue());
                return;
            case 7:
                zzci.zzz(((Integer) obj).intValue());
                return;
            case 8:
                zzci.zzf(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzeh) obj).zzb(zzci);
                return;
            case 10:
                zzci.zzb((zzeh) obj);
                return;
            case 11:
                if (obj instanceof zzbu) {
                    zzci.zza((zzbu) obj);
                    return;
                } else {
                    zzci.zzal((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzbu) {
                    zzci.zza((zzbu) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzci.zze(bArr, 0, bArr.length);
                return;
            case 13:
                zzci.zzx(((Integer) obj).intValue());
                return;
            case 14:
                zzci.zzz(((Integer) obj).intValue());
                return;
            case 15:
                zzci.zzc(((Long) obj).longValue());
                return;
            case 16:
                zzci.zzy(((Integer) obj).intValue());
                return;
            case 17:
                zzci.zzb(((Long) obj).longValue());
                return;
            case 18:
                if (obj instanceof zzde) {
                    zzci.zzw(((zzde) obj).zzds());
                    return;
                } else {
                    zzci.zzw(((Integer) obj).intValue());
                    return;
                }
            default:
                return;
        }
    }

    private final void zza(FieldDescriptorType fieldDescriptorType, Object obj) {
        Object obj2;
        if (!fieldDescriptorType.zzdv()) {
            zza(fieldDescriptorType.zzdt(), obj);
            obj2 = obj;
        } else if (obj instanceof List) {
            obj2 = new ArrayList();
            obj2.addAll((List) obj);
            ArrayList arrayList = (ArrayList) obj2;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj3 = arrayList.get(i);
                i++;
                zza(fieldDescriptorType.zzdt(), obj3);
            }
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj2 instanceof zzdk) {
            this.zzoc = true;
        }
        this.zzoa.zza((Comparable) fieldDescriptorType, obj2);
    }

    private static void zza(zzgd zzgd, Object obj) {
        boolean z = false;
        zzdd.checkNotNull(obj);
        switch (zzct.zzoe[zzgd.zzgj().ordinal()]) {
            case 1:
                z = obj instanceof Integer;
                break;
            case 2:
                z = obj instanceof Long;
                break;
            case 3:
                z = obj instanceof Float;
                break;
            case 4:
                z = obj instanceof Double;
                break;
            case 5:
                z = obj instanceof Boolean;
                break;
            case 6:
                z = obj instanceof String;
                break;
            case 7:
                if ((obj instanceof zzbu) || (obj instanceof byte[])) {
                    z = true;
                    break;
                }
            case 8:
                if ((obj instanceof Integer) || (obj instanceof zzde)) {
                    z = true;
                    break;
                }
            case 9:
                if ((obj instanceof zzeh) || (obj instanceof zzdk)) {
                    z = true;
                    break;
                }
        }
        if (!z) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    private static int zzb(zzcu<?> zzcu, Object obj) {
        int i = 0;
        zzgd zzdt = zzcu.zzdt();
        int zzds = zzcu.zzds();
        if (!zzcu.zzdv()) {
            return zza(zzdt, zzds, obj);
        }
        if (zzcu.zzdw()) {
            for (Object zzb : (List) obj) {
                i += zzb(zzdt, zzb);
            }
            return zzci.zzai(i) + (zzci.zzaa(zzds) + i);
        }
        for (Object zzb2 : (List) obj) {
            i += zza(zzdt, zzds, zzb2);
        }
        return i;
    }

    private static int zzb(zzgd zzgd, Object obj) {
        switch (zzct.zzne[zzgd.ordinal()]) {
            case 1:
                return zzci.zzb(((Double) obj).doubleValue());
            case 2:
                return zzci.zzb(((Float) obj).floatValue());
            case 3:
                return zzci.zzd(((Long) obj).longValue());
            case 4:
                return zzci.zze(((Long) obj).longValue());
            case 5:
                return zzci.zzab(((Integer) obj).intValue());
            case 6:
                return zzci.zzg(((Long) obj).longValue());
            case 7:
                return zzci.zzae(((Integer) obj).intValue());
            case 8:
                return zzci.zzg(((Boolean) obj).booleanValue());
            case 9:
                return zzci.zzd((zzeh) obj);
            case 10:
                return obj instanceof zzdk ? zzci.zza((zzdk) obj) : zzci.zzc((zzeh) obj);
            case 11:
                return obj instanceof zzbu ? zzci.zzb((zzbu) obj) : zzci.zzam((String) obj);
            case 12:
                return obj instanceof zzbu ? zzci.zzb((zzbu) obj) : zzci.zzc((byte[]) obj);
            case 13:
                return zzci.zzac(((Integer) obj).intValue());
            case 14:
                return zzci.zzaf(((Integer) obj).intValue());
            case 15:
                return zzci.zzh(((Long) obj).longValue());
            case 16:
                return zzci.zzad(((Integer) obj).intValue());
            case 17:
                return zzci.zzf(((Long) obj).longValue());
            case 18:
                return obj instanceof zzde ? zzci.zzag(((zzde) obj).zzds()) : zzci.zzag(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    private static boolean zzb(Entry<FieldDescriptorType, Object> entry) {
        zzcu zzcu = (zzcu) entry.getKey();
        if (zzcu.zzdu() == zzgi.MESSAGE) {
            if (zzcu.zzdv()) {
                for (zzeh isInitialized : (List) entry.getValue()) {
                    if (!isInitialized.isInitialized()) {
                        return false;
                    }
                }
            }
            Object value = entry.getValue();
            if (value instanceof zzeh) {
                if (!((zzeh) value).isInitialized()) {
                    return false;
                }
            } else if (value instanceof zzdk) {
                return true;
            } else {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
        }
        return true;
    }

    private final void zzc(Entry<FieldDescriptorType, Object> entry) {
        Comparable comparable = (zzcu) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzdk) {
            value = zzdk.zzem();
        }
        Object zza;
        if (comparable.zzdv()) {
            zza = zza((zzcu) comparable);
            if (zza == null) {
                zza = new ArrayList();
            }
            for (Object zzf : (List) value) {
                ((List) zza).add(zzf(zzf));
            }
            this.zzoa.zza(comparable, zza);
        } else if (comparable.zzdu() == zzgi.MESSAGE) {
            zza = zza((zzcu) comparable);
            if (zza == null) {
                this.zzoa.zza(comparable, zzf(value));
            } else {
                this.zzoa.zza(comparable, zza instanceof zzen ? comparable.zza((zzen) zza, (zzen) value) : comparable.zza(((zzeh) zza).zzdz(), (zzeh) value).zzed());
            }
        } else {
            this.zzoa.zza(comparable, zzf(value));
        }
    }

    private static int zzd(Entry<FieldDescriptorType, Object> entry) {
        zzcu zzcu = (zzcu) entry.getKey();
        Object value = entry.getValue();
        return (zzcu.zzdu() != zzgi.MESSAGE || zzcu.zzdv() || zzcu.zzdw()) ? zzb(zzcu, value) : value instanceof zzdk ? zzci.zzb(((zzcu) entry.getKey()).zzds(), (zzdk) value) : zzci.zzd(((zzcu) entry.getKey()).zzds(), (zzeh) value);
    }

    public static <T extends zzcu<T>> zzcs<T> zzdp() {
        return zzod;
    }

    private static Object zzf(Object obj) {
        if (obj instanceof zzen) {
            return ((zzen) obj).zzfc();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        Object obj2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, obj2, 0, bArr.length);
        return obj2;
    }

    public final /* synthetic */ Object clone() {
        zzcs zzcs = new zzcs();
        for (int i = 0; i < this.zzoa.zzfo(); i++) {
            Entry zzau = this.zzoa.zzau(i);
            zzcs.zza((zzcu) zzau.getKey(), zzau.getValue());
        }
        for (Entry entry : this.zzoa.zzfp()) {
            zzcs.zza((zzcu) entry.getKey(), entry.getValue());
        }
        zzcs.zzoc = this.zzoc;
        return zzcs;
    }

    final Iterator<Entry<FieldDescriptorType, Object>> descendingIterator() {
        return this.zzoc ? new zzdn(this.zzoa.zzfq().iterator()) : this.zzoa.zzfq().iterator();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcs)) {
            return false;
        }
        return this.zzoa.equals(((zzcs) obj).zzoa);
    }

    public final int hashCode() {
        return this.zzoa.hashCode();
    }

    final boolean isEmpty() {
        return this.zzoa.isEmpty();
    }

    public final boolean isImmutable() {
        return this.zzob;
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzoa.zzfo(); i++) {
            if (!zzb(this.zzoa.zzau(i))) {
                return false;
            }
        }
        for (Entry zzb : this.zzoa.zzfp()) {
            if (!zzb(zzb)) {
                return false;
            }
        }
        return true;
    }

    public final Iterator<Entry<FieldDescriptorType, Object>> iterator() {
        return this.zzoc ? new zzdn(this.zzoa.entrySet().iterator()) : this.zzoa.entrySet().iterator();
    }

    public final void zza(zzcs<FieldDescriptorType> zzcs) {
        for (int i = 0; i < zzcs.zzoa.zzfo(); i++) {
            zzc(zzcs.zzoa.zzau(i));
        }
        for (Entry zzc : zzcs.zzoa.zzfp()) {
            zzc(zzc);
        }
    }

    public final void zzbs() {
        if (!this.zzob) {
            this.zzoa.zzbs();
            this.zzob = true;
        }
    }

    public final int zzdq() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzoa.zzfo(); i2++) {
            Entry zzau = this.zzoa.zzau(i2);
            i += zzb((zzcu) zzau.getKey(), zzau.getValue());
        }
        for (Entry entry : this.zzoa.zzfp()) {
            i += zzb((zzcu) entry.getKey(), entry.getValue());
        }
        return i;
    }

    public final int zzdr() {
        int i = 0;
        int i2 = 0;
        while (i < this.zzoa.zzfo()) {
            i2 += zzd(this.zzoa.zzau(i));
            i++;
        }
        for (Entry zzd : this.zzoa.zzfp()) {
            i2 += zzd(zzd);
        }
        return i2;
    }
}
