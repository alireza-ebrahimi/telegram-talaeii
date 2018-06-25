package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzdr extends zzbgl {
    public static final Creator<zzdr> CREATOR = new zzdq();
    private int statusCode;
    private boolean zzluk;

    public zzdr(int i, boolean z) {
        this.statusCode = i;
        this.zzluk = z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zza(parcel, 3, this.zzluk);
        zzbgo.zzai(parcel, zze);
    }
}
