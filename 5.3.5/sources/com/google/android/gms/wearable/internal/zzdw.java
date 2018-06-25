package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.wearable.ConnectionConfiguration;

@Deprecated
public final class zzdw extends zzbgl {
    public static final Creator<zzdw> CREATOR = new zzdx();
    private int statusCode;
    private ConnectionConfiguration zzlun;

    public zzdw(int i, ConnectionConfiguration connectionConfiguration) {
        this.statusCode = i;
        this.zzlun = connectionConfiguration;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zza(parcel, 3, this.zzlun, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
