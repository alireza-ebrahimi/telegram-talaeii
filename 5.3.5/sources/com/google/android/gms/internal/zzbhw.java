package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class zzbhw extends zzbgl {
    public static final Creator<zzbhw> CREATOR = new zzbhz();
    final String className;
    private int versionCode;
    private ArrayList<zzbhx> zzgjb;

    zzbhw(int i, String str, ArrayList<zzbhx> arrayList) {
        this.versionCode = i;
        this.className = str;
        this.zzgjb = arrayList;
    }

    zzbhw(String str, Map<String, zzbhq<?, ?>> map) {
        ArrayList arrayList;
        this.versionCode = 1;
        this.className = str;
        if (map == null) {
            arrayList = null;
        } else {
            ArrayList arrayList2 = new ArrayList();
            for (String str2 : map.keySet()) {
                arrayList2.add(new zzbhx(str2, (zzbhq) map.get(str2)));
            }
            arrayList = arrayList2;
        }
        this.zzgjb = arrayList;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.versionCode);
        zzbgo.zza(parcel, 2, this.className, false);
        zzbgo.zzc(parcel, 3, this.zzgjb, false);
        zzbgo.zzai(parcel, zze);
    }

    final HashMap<String, zzbhq<?, ?>> zzank() {
        HashMap<String, zzbhq<?, ?>> hashMap = new HashMap();
        int size = this.zzgjb.size();
        for (int i = 0; i < size; i++) {
            zzbhx zzbhx = (zzbhx) this.zzgjb.get(i);
            hashMap.put(zzbhx.key, zzbhx.zzgjc);
        }
        return hashMap;
    }
}
