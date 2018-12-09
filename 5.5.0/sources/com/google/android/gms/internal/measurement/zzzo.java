package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzzo<FieldDescriptorType extends zzzq<FieldDescriptorType>> {
    private static final zzzo zzbse = new zzzo(true);
    private boolean zzbme;
    private final zzaba<FieldDescriptorType, Object> zzbsc = zzaba.zzag(16);
    private boolean zzbsd = false;

    private zzzo() {
    }

    private zzzo(boolean z) {
        if (!this.zzbme) {
            this.zzbsc.zzrp();
            this.zzbme = true;
        }
    }

    private static void zza(zzabr zzabr, Object obj) {
        boolean z = false;
        zzzt.checkNotNull(obj);
        switch (zzzp.zzbsf[zzabr.zzve().ordinal()]) {
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
                if ((obj instanceof zzyy) || (obj instanceof byte[])) {
                    z = true;
                    break;
                }
            case 8:
                if ((obj instanceof Integer) || (obj instanceof zzzu)) {
                    z = true;
                    break;
                }
            case 9:
                if ((obj instanceof zzaan) || (obj instanceof zzzw)) {
                    z = true;
                    break;
                }
        }
        if (!z) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    private final void zza(FieldDescriptorType fieldDescriptorType, Object obj) {
        Object obj2;
        if (!fieldDescriptorType.zztt()) {
            zza(fieldDescriptorType.zzts(), obj);
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
                zza(fieldDescriptorType.zzts(), obj3);
            }
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj2 instanceof zzzw) {
            this.zzbsd = true;
        }
        this.zzbsc.zza((Comparable) fieldDescriptorType, obj2);
    }

    public static <T extends zzzq<T>> zzzo<T> zztr() {
        return zzbse;
    }

    public final /* synthetic */ Object clone() {
        zzzo zzzo = new zzzo();
        for (int i = 0; i < this.zzbsc.zzus(); i++) {
            Entry zzah = this.zzbsc.zzah(i);
            zzzo.zza((zzzq) zzah.getKey(), zzah.getValue());
        }
        for (Entry entry : this.zzbsc.zzut()) {
            zzzo.zza((zzzq) entry.getKey(), entry.getValue());
        }
        zzzo.zzbsd = this.zzbsd;
        return zzzo;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzzo)) {
            return false;
        }
        return this.zzbsc.equals(((zzzo) obj).zzbsc);
    }

    public final int hashCode() {
        return this.zzbsc.hashCode();
    }

    public final Iterator<Entry<FieldDescriptorType, Object>> iterator() {
        return this.zzbsd ? new zzzz(this.zzbsc.entrySet().iterator()) : this.zzbsc.entrySet().iterator();
    }
}
