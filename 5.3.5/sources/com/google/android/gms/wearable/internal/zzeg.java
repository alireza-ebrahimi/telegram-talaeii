package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzeg extends zzbgl {
    public static final Creator<zzeg> CREATOR = new zzeh();
    public final int statusCode;
    public final zzfo zzlur;

    public zzeg(int i, zzfo zzfo) {
        this.statusCode = i;
        this.zzlur = zzfo;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zza(parcel, 3, this.zzlur, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
