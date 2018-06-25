package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzbhm extends zzbgl {
    public static final Creator<zzbhm> CREATOR = new zzbho();
    private int versionCode;
    final String zzgim;
    final int zzgin;

    zzbhm(int i, String str, int i2) {
        this.versionCode = i;
        this.zzgim = str;
        this.zzgin = i2;
    }

    zzbhm(String str, int i) {
        this.versionCode = 1;
        this.zzgim = str;
        this.zzgin = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.versionCode);
        zzbgo.zza(parcel, 2, this.zzgim, false);
        zzbgo.zzc(parcel, 3, this.zzgin);
        zzbgo.zzai(parcel, zze);
    }
}
