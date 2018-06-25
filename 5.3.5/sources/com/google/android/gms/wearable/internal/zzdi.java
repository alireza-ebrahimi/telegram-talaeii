package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.List;

public final class zzdi extends zzbgl {
    public static final Creator<zzdi> CREATOR = new zzdj();
    public final int statusCode;
    public final List<zzah> zzluh;

    public zzdi(int i, List<zzah> list) {
        this.statusCode = i;
        this.zzluh = list;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zzc(parcel, 3, this.zzluh, false);
        zzbgo.zzai(parcel, zze);
    }
}
