package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzbhv extends zzbgl {
    public static final Creator<zzbhv> CREATOR = new zzbhy();
    private int zzehz;
    private final HashMap<String, Map<String, zzbhq<?, ?>>> zzgiy;
    private final ArrayList<zzbhw> zzgiz = null;
    private final String zzgja;

    zzbhv(int i, ArrayList<zzbhw> arrayList, String str) {
        this.zzehz = i;
        HashMap hashMap = new HashMap();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            zzbhw zzbhw = (zzbhw) arrayList.get(i2);
            hashMap.put(zzbhw.className, zzbhw.zzank());
        }
        this.zzgiy = hashMap;
        this.zzgja = (String) zzbq.checkNotNull(str);
        zzani();
    }

    private final void zzani() {
        for (String str : this.zzgiy.keySet()) {
            Map map = (Map) this.zzgiy.get(str);
            for (String str2 : map.keySet()) {
                ((zzbhq) map.get(str2)).zza(this);
            }
        }
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : this.zzgiy.keySet()) {
            stringBuilder.append(str).append(":\n");
            Map map = (Map) this.zzgiy.get(str);
            for (String str2 : map.keySet()) {
                stringBuilder.append("  ").append(str2).append(": ");
                stringBuilder.append(map.get(str2));
            }
        }
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzehz);
        List arrayList = new ArrayList();
        for (String str : this.zzgiy.keySet()) {
            arrayList.add(new zzbhw(str, (Map) this.zzgiy.get(str)));
        }
        zzbgo.zzc(parcel, 2, arrayList, false);
        zzbgo.zza(parcel, 3, this.zzgja, false);
        zzbgo.zzai(parcel, zze);
    }

    public final String zzanj() {
        return this.zzgja;
    }

    public final Map<String, zzbhq<?, ?>> zzgz(String str) {
        return (Map) this.zzgiy.get(str);
    }
}
