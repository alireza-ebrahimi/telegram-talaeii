package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzdll extends zzbgl {
    public static final Creator<zzdll> CREATOR = new zzdlm();
    public final String zzlib;
    public final zzdlu[] zzlig;
    public final zzdlf zzlih;
    private zzdlf zzlii;
    private zzdlf zzlij;
    public final String zzlik;
    private float zzlil;
    private int zzlim;
    public final boolean zzlin;
    public final int zzlio;
    public final int zzlip;

    @Hide
    public zzdll(zzdlu[] zzdluArr, zzdlf zzdlf, zzdlf zzdlf2, zzdlf zzdlf3, String str, float f, String str2, int i, boolean z, int i2, int i3) {
        this.zzlig = zzdluArr;
        this.zzlih = zzdlf;
        this.zzlii = zzdlf2;
        this.zzlij = zzdlf3;
        this.zzlik = str;
        this.zzlil = f;
        this.zzlib = str2;
        this.zzlim = i;
        this.zzlin = z;
        this.zzlio = i2;
        this.zzlip = i3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzlig, i, false);
        zzbgo.zza(parcel, 3, this.zzlih, i, false);
        zzbgo.zza(parcel, 4, this.zzlii, i, false);
        zzbgo.zza(parcel, 5, this.zzlij, i, false);
        zzbgo.zza(parcel, 6, this.zzlik, false);
        zzbgo.zza(parcel, 7, this.zzlil);
        zzbgo.zza(parcel, 8, this.zzlib, false);
        zzbgo.zzc(parcel, 9, this.zzlim);
        zzbgo.zza(parcel, 10, this.zzlin);
        zzbgo.zzc(parcel, 11, this.zzlio);
        zzbgo.zzc(parcel, 12, this.zzlip);
        zzbgo.zzai(parcel, zze);
    }
}
