package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzdkv extends zzbgl {
    public static final Creator<zzdkv> CREATOR = new zzdkw();
    public int zzlgq;

    public zzdkv(int i) {
        this.zzlgq = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.zzlgq);
        zzbgo.zzai(parcel, zze);
    }
}
