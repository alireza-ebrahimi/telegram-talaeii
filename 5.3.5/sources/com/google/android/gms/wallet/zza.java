package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
@Deprecated
public final class zza extends zzbgl {
    public static final Creator<zza> CREATOR = new zzb();
    private String name;
    private String phoneNumber;
    private String zzcyf;
    private String zzilq;
    private String zzilr;
    private String zzils;
    private String zzilx;
    private boolean zzilz;
    private String zzima;
    private String zzlit;
    private String zzliu;

    zza() {
    }

    zza(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, boolean z, String str10) {
        this.name = str;
        this.zzilq = str2;
        this.zzilr = str3;
        this.zzils = str4;
        this.zzcyf = str5;
        this.zzlit = str6;
        this.zzliu = str7;
        this.zzilx = str8;
        this.phoneNumber = str9;
        this.zzilz = z;
        this.zzima = str10;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.name, false);
        zzbgo.zza(parcel, 3, this.zzilq, false);
        zzbgo.zza(parcel, 4, this.zzilr, false);
        zzbgo.zza(parcel, 5, this.zzils, false);
        zzbgo.zza(parcel, 6, this.zzcyf, false);
        zzbgo.zza(parcel, 7, this.zzlit, false);
        zzbgo.zza(parcel, 8, this.zzliu, false);
        zzbgo.zza(parcel, 9, this.zzilx, false);
        zzbgo.zza(parcel, 10, this.phoneNumber, false);
        zzbgo.zza(parcel, 11, this.zzilz);
        zzbgo.zza(parcel, 12, this.zzima, false);
        zzbgo.zzai(parcel, zze);
    }
}
