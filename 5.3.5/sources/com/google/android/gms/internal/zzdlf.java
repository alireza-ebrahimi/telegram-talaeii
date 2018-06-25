package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzdlf extends zzbgl {
    public static final Creator<zzdlf> CREATOR = new zzdlg();
    public final int height;
    public final int left;
    public final int top;
    public final int width;
    public final float zzlif;

    @Hide
    public zzdlf(int i, int i2, int i3, int i4, float f) {
        this.left = i;
        this.top = i2;
        this.width = i3;
        this.height = i4;
        this.zzlif = f;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.left);
        zzbgo.zzc(parcel, 3, this.top);
        zzbgo.zzc(parcel, 4, this.width);
        zzbgo.zzc(parcel, 5, this.height);
        zzbgo.zza(parcel, 6, this.zzlif);
        zzbgo.zzai(parcel, zze);
    }
}
