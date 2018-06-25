package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.List;

public final class zzea extends zzbgl {
    public static final Creator<zzea> CREATOR = new zzeb();
    public final int statusCode;
    public final List<zzfo> zzlup;

    public zzea(int i, List<zzfo> list) {
        this.statusCode = i;
        this.zzlup = list;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zzc(parcel, 3, this.zzlup, false);
        zzbgo.zzai(parcel, zze);
    }
}
