package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class zzbhl extends zzbgl implements zzbhr<String, Integer> {
    public static final Creator<zzbhl> CREATOR = new zzbhn();
    private int zzehz;
    private final HashMap<String, Integer> zzgij;
    private final SparseArray<String> zzgik;
    private final ArrayList<zzbhm> zzgil;

    public zzbhl() {
        this.zzehz = 1;
        this.zzgij = new HashMap();
        this.zzgik = new SparseArray();
        this.zzgil = null;
    }

    zzbhl(int i, ArrayList<zzbhm> arrayList) {
        this.zzehz = i;
        this.zzgij = new HashMap();
        this.zzgik = new SparseArray();
        this.zzgil = null;
        zzd(arrayList);
    }

    private final void zzd(ArrayList<zzbhm> arrayList) {
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zzbhm zzbhm = (zzbhm) obj;
            zzi(zzbhm.zzgim, zzbhm.zzgin);
        }
    }

    public final /* synthetic */ Object convertBack(Object obj) {
        String str = (String) this.zzgik.get(((Integer) obj).intValue());
        return (str == null && this.zzgij.containsKey("gms_unknown")) ? "gms_unknown" : str;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzehz);
        List arrayList = new ArrayList();
        for (String str : this.zzgij.keySet()) {
            arrayList.add(new zzbhm(str, ((Integer) this.zzgij.get(str)).intValue()));
        }
        zzbgo.zzc(parcel, 2, arrayList, false);
        zzbgo.zzai(parcel, zze);
    }

    public final zzbhl zzi(String str, int i) {
        this.zzgij.put(str, Integer.valueOf(i));
        this.zzgik.put(i, str);
        return this;
    }
}
