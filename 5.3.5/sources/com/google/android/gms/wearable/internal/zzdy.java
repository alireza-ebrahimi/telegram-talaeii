package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.wearable.ConnectionConfiguration;

public final class zzdy extends zzbgl {
    public static final Creator<zzdy> CREATOR = new zzdz();
    private int statusCode;
    private ConnectionConfiguration[] zzluo;

    public zzdy(int i, ConnectionConfiguration[] connectionConfigurationArr) {
        this.statusCode = i;
        this.zzluo = connectionConfigurationArr;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zza(parcel, 3, this.zzluo, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
