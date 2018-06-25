package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzfu extends zzbgl {
    public static final Creator<zzfu> CREATOR = new zzfv();
    public final int statusCode;
    public final zzdd zzluq;

    public zzfu(int i, zzdd zzdd) {
        this.statusCode = i;
        this.zzluq = zzdd;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zza(parcel, 3, this.zzluq, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
