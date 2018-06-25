package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzdt extends zzbgl {
    public static final Creator<zzdt> CREATOR = new zzds();
    private int statusCode;
    private boolean zzlul;
    private boolean zzlum;

    public zzdt(int i, boolean z, boolean z2) {
        this.statusCode = i;
        this.zzlul = z;
        this.zzlum = z2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zza(parcel, 3, this.zzlul);
        zzbgo.zza(parcel, 4, this.zzlum);
        zzbgo.zzai(parcel, zze);
    }
}
