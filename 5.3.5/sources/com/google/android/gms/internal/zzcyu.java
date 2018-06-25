package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbr;

public final class zzcyu extends zzbgl {
    public static final Creator<zzcyu> CREATOR = new zzcyv();
    private int zzehz;
    private zzbr zzkly;

    zzcyu(int i, zzbr zzbr) {
        this.zzehz = i;
        this.zzkly = zzbr;
    }

    public zzcyu(zzbr zzbr) {
        this(1, zzbr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzehz);
        zzbgo.zza(parcel, 2, this.zzkly, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
