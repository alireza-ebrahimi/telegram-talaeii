package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzdv extends zzbgl {
    public static final Creator<zzdv> CREATOR = new zzdu();
    private boolean enabled;
    private int statusCode;

    public zzdv(int i, boolean z) {
        this.statusCode = i;
        this.enabled = z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zza(parcel, 3, this.enabled);
        zzbgo.zzai(parcel, zze);
    }
}
