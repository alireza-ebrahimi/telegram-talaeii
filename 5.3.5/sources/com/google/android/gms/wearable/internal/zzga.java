package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzga extends zzbgl {
    public static final Creator<zzga> CREATOR = new zzgb();
    public final int statusCode;
    public final int zzino;

    public zzga(int i, int i2) {
        this.statusCode = i;
        this.zzino = i2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zzc(parcel, 3, this.zzino);
        zzbgo.zzai(parcel, zze);
    }
}
